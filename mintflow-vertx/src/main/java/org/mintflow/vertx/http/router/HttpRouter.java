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
import org.mintflow.vertx.http.exceptrion.MintFlowControllerError;
import org.mintflow.vertx.http.param.RequestParam;
import org.mintflow.vertx.http.param.ResponseParam;
import org.mintflow.vertx.http.util.HttpUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRouter implements Handler<HttpServerRequest> {

    private static class RouterData{
        private String url;
        private String nameSpace;
        private String process;
        private HttpMethod[] httpMethod;
        private RequestParamAdapter requestParamAdapter;
        private ResponseParamAdapter responseParamAdapter;
        public HttpMethod[] getHttpMethod() {
            return httpMethod;
        }
        public void setHttpMethod(HttpMethod[] httpMethod) {
            this.httpMethod = httpMethod;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getNameSpace() {
            return nameSpace;
        }

        public void setNameSpace(String nameSpace) {
            this.nameSpace = nameSpace;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public RequestParamAdapter getRequestParamAdapter() {
            return requestParamAdapter;
        }

        public void setRequestParamAdapter(RequestParamAdapter requestParamAdapter) {
            this.requestParamAdapter = requestParamAdapter;
        }

        public ResponseParamAdapter getResponseParamAdapter() {
            return responseParamAdapter;
        }

        public void setResponseParamAdapter(ResponseParamAdapter responseParamAdapter) {
            this.responseParamAdapter = responseParamAdapter;
        }
    }

    private Map<String,RouterData> routerDataMap;
    private RequestParamAdapter defaultRequestParamAdapter;
    private ResponseParamAdapter defaultResponseParamAdapter;
    private  MintFlow mintFlow;

    public HttpRouter addRouter(String url , HttpMethod[] httpMethod,
                                String nameSpace , String process,
                                RequestParamAdapter requestParamAdapter ,
                                ResponseParamAdapter responseParamAdapter){
        RouterData routerData = new RouterData();
        routerData.setNameSpace(nameSpace);
        routerData.setProcess(process);
        routerData.setHttpMethod(httpMethod);
        routerData.setRequestParamAdapter(requestParamAdapter);
        routerData.setResponseParamAdapter(responseParamAdapter);
        routerDataMap.put(url,routerData);
        return this;
    }

    public HttpRouter addRouter(String url , HttpMethod[] httpMethod, String nameSpace, String process){
        RouterData routerData = new RouterData();
        routerData.setNameSpace(nameSpace);
        routerData.setProcess(process);
        routerData.setHttpMethod(httpMethod);
        routerData.setRequestParamAdapter(this.defaultRequestParamAdapter);
        routerData.setResponseParamAdapter(this.defaultResponseParamAdapter);
        if(routerDataMap.containsKey(url)){
            throw new MintFlowControllerError("当前url地址已被使用："+url);
        }
        routerDataMap.put(url,routerData);
        return this;
    }

    public HttpRouter addRouter(List<FinderItem> finderItems){
        for(FinderItem finderItem: finderItems){
            addRouter(finderItem.getUrl(),finderItem.getHttpMethod(),
                    finderItem.getNameSpace(),finderItem.getProcess(),
                    finderItem.getRequestParamAdapter(),finderItem.getResponseParamAdapter());
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
        event.handler(buffer::appendBuffer);
        String url = event.path();
        event.endHandler(vo->{
            RequestParam requestParam = new RequestParam();
            requestParam.setBody(buffer.toString());
            requestParam.setCookieMap(event.cookieMap());
            requestParam.setFormAttributes(event.formAttributes());
            requestParam.setHeaders(event.headers());
            requestParam.setParams(event.params());
            RouterData routerData = routerDataMap.get(url);
            ParamWrapper paramWrapper = routerData.getRequestParamAdapter().createParams(requestParam);
            mintFlow.runAsync(routerData.getNameSpace(),routerData.getProcess(),paramWrapper,(params)->{
                ResponseParam responseParam = routerData.getResponseParamAdapter().createResponseParams(paramWrapper);
                HttpServerResponse httpServerResponse = event.response();
                if(responseParam.getCookies()!=null){
                    HttpUtil.ResponseUtil.addCookies(responseParam.getCookies(),httpServerResponse);
                }
                if(responseParam.getHeader()!=null){
                    HttpUtil.ResponseUtil.addHeader(responseParam.getHeader(),httpServerResponse);
                }
                if(responseParam.getStatusCode()!=null){
                    HttpUtil.ResponseUtil.addStatusCode(responseParam.getStatusCode(),httpServerResponse);
                }
                if(responseParam.getStatusMessage()!=null){
                    HttpUtil.ResponseUtil.addStatusMessage(responseParam.getStatusMessage(),httpServerResponse);
                }
                HttpUtil.ResponseUtil.addData(responseParam.getData(),httpServerResponse);
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

    public static HttpRouter router(MintFlow mintFlow){
        HttpRouter httpRouter = new HttpRouter();
        httpRouter.setMintFlow(mintFlow);
        httpRouter.setRouterDataMap(new HashMap<>());
        httpRouter.setDefaultRequestParamAdapter(new ControllerMapperParamAdapter());
        httpRouter.setDefaultResponseParamAdapter(new DefaultResponseParamAdapter());
        return httpRouter;
    }

}
