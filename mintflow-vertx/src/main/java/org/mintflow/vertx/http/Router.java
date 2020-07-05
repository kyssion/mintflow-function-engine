package org.mintflow.vertx.http;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import org.mintflow.vertx.http.adapter.request.DefaultRequestParamAdapter;
import org.mintflow.vertx.http.adapter.request.RequestParamAdapter;
import org.mintflow.vertx.http.adapter.response.ResponseParamAdapter;
import org.mintflow.vertx.http.adapter.response.DefaultResponseParamAdapter;
import java.util.HashMap;
import java.util.Map;

public class Router implements Handler<HttpServerRequest> {

    private static class RouterData{

        private String url;
        private String nameSpace;
        private String process;
        private HttpMethod httpMethod;
        private RequestParamAdapter requestParamAdapter;
        private ResponseParamAdapter responseParamAdapter;

        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(HttpMethod httpMethod) {
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
    private Vertx vertx;

    public Router addRouter(String url , String nameSpace,String process,
                            RequestParamAdapter requestParamAdapter,ResponseParamAdapter responseParamAdapter){
        RouterData routerData = new RouterData();
        routerData.setNameSpace(nameSpace);
        routerData.setProcess(process);
        routerData.setRequestParamAdapter(requestParamAdapter);
        routerData.setResponseParamAdapter(responseParamAdapter);
        routerDataMap.put(url,routerData);
        return this;
    }

    public Router addRouter(String url , HttpMethod httpMethod, String nameSpace, String process){
        RouterData routerData = new RouterData();
        routerData.setNameSpace(nameSpace);
        routerData.setProcess(process);
        routerData.setRequestParamAdapter(this.defaultRequestParamAdapter);
        routerData.setResponseParamAdapter(this.defaultResponseParamAdapter);
        routerDataMap.put(url,routerData);
        return this;
    }

    private Router(){
        super();
    }

    @Override
    public void handle(HttpServerRequest event) {

    }

    private void setVertx(Vertx vertx) {
        this.vertx = vertx;
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

    public static Router router(Vertx vertx){
        Router router = new Router();
        router.setVertx(vertx);
        router.setRouterDataMap(new HashMap<>());
        router.setDefaultRequestParamAdapter(new DefaultRequestParamAdapter());
        router.setDefaultResponseParamAdapter(new DefaultResponseParamAdapter());
        return router;
    }

}
