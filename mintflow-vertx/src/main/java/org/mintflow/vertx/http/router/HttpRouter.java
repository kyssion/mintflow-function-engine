package org.mintflow.vertx.http.router;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.mintflow.MintFlow;
import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.http.adapter.request.ControllerMapperParamAdapter;
import org.mintflow.vertx.http.adapter.request.DefaultRequestParamAdapter;
import org.mintflow.vertx.http.adapter.request.RequestParamAdapter;
import org.mintflow.vertx.http.adapter.response.ResponseParamAdapter;
import org.mintflow.vertx.http.adapter.response.DefaultResponseParamAdapter;
import org.mintflow.vertx.http.controller.finder.FinderItem;
import org.mintflow.vertx.http.controller.finder.MintFlowControllerFinder;
import org.mintflow.vertx.http.exceptrion.DefaultRouterExceptionHandler;
import org.mintflow.vertx.http.exceptrion.MintFlowControllerError;
import org.mintflow.vertx.http.exceptrion.RouterExceptionHandler;
import org.mintflow.vertx.http.param.RequestParam;
import org.mintflow.vertx.http.param.ResponseParam;
import org.mintflow.vertx.http.util.HttpUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRouter implements Handler<HttpServerRequest> {

    private Map<String,RouterData> routerDataMap;
    private RequestParamAdapter defaultRequestParamAdapter;
    private ResponseParamAdapter defaultResponseParamAdapter;
    private  MintFlow mintFlow;
    private RouterExceptionHandler routerExceptionHandler = new DefaultRouterExceptionHandler();
    public static HttpRouter router(MintFlow mintFlow){
        HttpRouter httpRouter = new HttpRouter();
        httpRouter.setMintFlow(mintFlow);
        httpRouter.setRouterDataMap(new HashMap<>());
        httpRouter.setDefaultRequestParamAdapter(new ControllerMapperParamAdapter());
        httpRouter.setDefaultResponseParamAdapter(new DefaultResponseParamAdapter());
        return httpRouter;
    }

    public HttpRouter addRouter(String url , HttpMethod[] httpMethod, String nameSpace , String process, RequestParamAdapter requestParamAdapter , ResponseParamAdapter responseParamAdapter){
        RouterData routerData = new RouterData(url,nameSpace,process,httpMethod,requestParamAdapter,responseParamAdapter);
        if(routerDataMap.containsKey(url)){
            throw new MintFlowControllerError("当前url地址已被使用："+url);
        }
        routerDataMap.put(url,routerData);
        return this;
    }

    public HttpRouter addRouter(String url , HttpMethod[] httpMethod, String nameSpace, String process){
        RouterData routerData = new RouterData(url,nameSpace,process,httpMethod,this.defaultRequestParamAdapter,this.defaultResponseParamAdapter);
        if(routerDataMap.containsKey(url)){
            throw new MintFlowControllerError("当前url地址已被使用："+url);
        }
        routerDataMap.put(url,routerData);
        return this;
    }

    public HttpRouter addRouter(List<FinderItem> finderItems){
        for(FinderItem finderItem: finderItems){
            addRouter(finderItem.getUrl(),finderItem.getHttpMethod(),finderItem.getNameSpace(),finderItem.getProcess(),finderItem.getRequestParamAdapter(),finderItem.getResponseParamAdapter());
        }
        return this;
    }

    public HttpRouter addRouter(String...pkNames){
        return addRouter(MintFlowControllerFinder.find(pkNames));
    }

    private HttpRouter(){
        super();
    }

    public MintFlow getMintFlow() {
        return mintFlow;
    }

    public void setMintFlow(MintFlow mintFlow) {
        this.mintFlow = mintFlow;
    }

    @Override
    public void handle(HttpServerRequest event) {
        Buffer buffer = Buffer.buffer();
        String url = event.path();

        RouterData routerData = routerDataMap.get(url);
        if(!RouterData.checkRequest(routerData,event)){
            event.response().setStatusCode(404).setStatusMessage("not find").end();
            return;
        }
        RequestParam requestParam = new RequestParam(event.headers(),event.params(),event.formAttributes(),event.cookieMap(),null);

        event.handler(buffer::appendBuffer);
        event.endHandler(vo->{
            requestParam.setBody(buffer.toString());
            ParamWrapper paramWrapper = routerData.getRequestParamAdapter().createParams(requestParam);
            mintFlow.runAsync(routerData.getNameSpace(),routerData.getProcess(),paramWrapper,(params)->{
                ResponseParam responseParam = null;
                if(!params.isSuccess()){
                    responseParam = this.routerExceptionHandler.handle(paramWrapper,params.getException());
                }else{
                    responseParam = routerData.getResponseParamAdapter().createResponseParams(paramWrapper);
                }
                HttpServerResponse httpServerResponse = event.response();
                if(responseParam!=null){
                    responseParam.addDataToHttpServerResponse(httpServerResponse);
                }
                httpServerResponse.end();
            });
        });
    }

    private void setRouterDataMap(Map<String, RouterData> routerDataMap) {
        this.routerDataMap = routerDataMap;
    }

    public void setDefaultRequestParamAdapter(RequestParamAdapter requestParamAdapter){
        this.defaultRequestParamAdapter = requestParamAdapter;
    }

    public void setDefaultResponseParamAdapter(ResponseParamAdapter responseParamAdapter){
        this.defaultResponseParamAdapter=responseParamAdapter;
    }

    public void setRouterExceptionHandler(RouterExceptionHandler routerExceptionHandler){
        this.routerExceptionHandler  = routerExceptionHandler;
    }
}
