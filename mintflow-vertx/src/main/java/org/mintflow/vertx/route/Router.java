package org.mintflow.vertx.route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import org.mintflow.MintFlow;
import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.controller.ControllerRouterData;
import org.mintflow.vertx.param.ResponseParam;
import org.mintflow.vertx.param.adapter.DefaultResponseParamAdapter;
import org.mintflow.vertx.param.adapter.RequestParamAdapter;
import org.mintflow.vertx.param.RequestParam;
import org.mintflow.vertx.param.adapter.ResponseParamAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Router implements Handler<HttpServerRequest> {
    private final MintFlow mintFlow;
    private final ResponseParamAdapter<ResponseParam> responseParamResponseParamAdapter = new DefaultResponseParamAdapter();
    public Router(MintFlow mintFlow){
        this.mintFlow = mintFlow;
    }

    private Map<String,RouterData>  routerDataMap = new HashMap<>();

    @Override
    public void handle(HttpServerRequest request) {
        RequestParam requestParam = getRequestParam(request);
        String url = request.path();
        RouterData routerData = routerDataMap.get(url);
        if(routerData==null){
            throw new RuntimeException("not find url map");
        }
        request.bodyHandler(buf->{
            requestParam.setBody(buf.toString());
            ParamWrapper paramWrapper = routerData.getRequestParamAdapter().createParams(requestParam);
            mintFlow.runAsync(routerData.getNameSpace(),routerData.getProcess(),paramWrapper,(param)->{
                ResponseParam responseParam = routerData.getResponseParamAdapter().createResponseParams(param);
                request.response().end(responseParam.getData());
            });
        });
    }

    private RequestParam getRequestParam(HttpServerRequest request) {
        RequestParam requestParam = new RequestParam();
        requestParam.setHeaders(request.headers());
        requestParam.setCookieMap(request.cookieMap());
        requestParam.setParams(request.params());
        requestParam.setFormAttributes(request.formAttributes());
        return requestParam;
    }

    public void addRoute(String url, String namespace, String process,
                         RequestParamAdapter<RequestParam> requestParamAdapter, ResponseParamAdapter<ResponseParam> responseParamAdapter){
        RouterData routerData = new RouterData(namespace,process,requestParamAdapter,responseParamAdapter);
        this.routerDataMap.put(url,routerData);
    }

    public void addRoute(Map<String, ControllerRouterData> map){
        for(Map.Entry<String,ControllerRouterData> entry : map.entrySet()){
            this.addRoute(entry.getKey(), entry.getValue().getNameSpace(), entry.getValue().getProcess(),
                    requestParam -> {
                        ControllerRouterData routerData =
                                entry.getValue();
                        Method method = routerData.getMethod();
                        try {
                           return (ParamWrapper) method.invoke(routerData.getItem(),requestParam);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();e.printStackTrace();
                            return null;
                        }
                    }, responseParamResponseParamAdapter);
        }
    }

    private static class RouterData{

        public RouterData(String nameSpace,String process,RequestParamAdapter<RequestParam> requestParamAdapter,
                          ResponseParamAdapter<ResponseParam> responseParamAdapter){
            this.nameSpace = nameSpace;
            this.process = process;
            this.requestParamAdapter = requestParamAdapter;
            this.responseParamAdapter = responseParamAdapter;
        }

        private String nameSpace;
        private String process;
        private RequestParamAdapter<RequestParam> requestParamAdapter;
        private ResponseParamAdapter<ResponseParam> responseParamAdapter;

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

        public RequestParamAdapter<RequestParam> getRequestParamAdapter() {
            return requestParamAdapter;
        }

        public void setRequestParamAdapter(RequestParamAdapter<RequestParam> requestParamAdapter) {
            this.requestParamAdapter = requestParamAdapter;
        }

        public ResponseParamAdapter<ResponseParam> getResponseParamAdapter() {
            return responseParamAdapter;
        }

        public void setResponseParamAdapter(ResponseParamAdapter<ResponseParam> responseParamAdapter) {
            this.responseParamAdapter = responseParamAdapter;
        }
    }

}
