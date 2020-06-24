package org.mintflow.vertx.route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import org.mintflow.MintFlow;
import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.param.ResponseParams;
import org.mintflow.vertx.param.adapter.RequestParamAdapter;
import org.mintflow.vertx.param.RequestParam;
import org.mintflow.vertx.param.adapter.ResponseParamAdapter;

import java.util.HashMap;
import java.util.Map;

public class Router implements Handler<HttpServerRequest> {
    private final MintFlow mintFlow;
    public Router(MintFlow mintFlow){
        this.mintFlow = mintFlow;
    }

    private Map<String,RouterData>  routerDataMap = new HashMap<>();

    @Override
    public void handle(HttpServerRequest request) {
        RequestParam requestParam = getRequestParam(request);
        String url = request.path();
        RouterData routerData = routerDataMap.get(url);
        request.bodyHandler(buf->{
            requestParam.setBody(buf.toString());
            ParamWrapper paramWrapper = routerData.getRequestParamAdapter().createParams(requestParam);
            mintFlow.runAsync(routerData.getNameSpace(),routerData.getProcess(),paramWrapper,(param)->{
                ResponseParams responseParams = routerData.getResponseParamAdapter().createResponseParams(param);
                request.response().end(responseParams.getData());
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
                         RequestParamAdapter<RequestParam> requestParamAdapter, ResponseParamAdapter<ResponseParams> responseParamAdapter){
        RouterData routerData = new RouterData(namespace,process,requestParamAdapter,responseParamAdapter);
        this.routerDataMap.put(url,routerData);
    }


    private static class RouterData{

        public RouterData(String nameSpace,String process,RequestParamAdapter<RequestParam> requestParamAdapter,
                          ResponseParamAdapter<ResponseParams> responseParamAdapter){
            this.nameSpace = nameSpace;
            this.process = process;
            this.requestParamAdapter = requestParamAdapter;
            this.responseParamAdapter = responseParamAdapter;
        }

        private String nameSpace;
        private String process;
        private RequestParamAdapter<RequestParam> requestParamAdapter;
        private ResponseParamAdapter<ResponseParams> responseParamAdapter;

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

        public ResponseParamAdapter<ResponseParams> getResponseParamAdapter() {
            return responseParamAdapter;
        }

        public void setResponseParamAdapter(ResponseParamAdapter<ResponseParams> responseParamAdapter) {
            this.responseParamAdapter = responseParamAdapter;
        }
    }

}
