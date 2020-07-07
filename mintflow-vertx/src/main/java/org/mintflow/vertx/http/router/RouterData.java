package org.mintflow.vertx.http.router;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import org.mintflow.vertx.http.adapter.request.RequestParamAdapter;
import org.mintflow.vertx.http.adapter.response.ResponseParamAdapter;

public class RouterData {

    public RouterData(){
        super();
    }

    public RouterData(String url,String nameSpace,String process,HttpMethod[] httpMethods,RequestParamAdapter requestParamAdapter,ResponseParamAdapter responseParamAdapter){
        this.url = url;
        this.nameSpace = nameSpace;
        this.process = process;
        this.httpMethod = httpMethods;
        this.requestParamAdapter = requestParamAdapter;
        this.responseParamAdapter = responseParamAdapter;
    }

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

    public static boolean checkRequest(RouterData routerData, HttpServerRequest event) {
        if(routerData==null){
            return false;
        }

        boolean isInMethod = false;
        HttpMethod[] httpMethods = routerData.getHttpMethod();
        HttpMethod nowHttpMethod = event.method();
        for(HttpMethod itemMethod: httpMethods){
            if(itemMethod.equals(nowHttpMethod)){
                isInMethod=true;
                break;
            }
        }
        return isInMethod;
    }
}
