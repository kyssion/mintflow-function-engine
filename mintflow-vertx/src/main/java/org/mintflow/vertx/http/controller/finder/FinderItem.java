package org.mintflow.vertx.http.controller.finder;

import io.vertx.core.http.HttpMethod;
import org.mintflow.vertx.http.adapter.request.RequestParamAdapter;
import org.mintflow.vertx.http.adapter.response.ResponseParamAdapter;

public class FinderItem {
    
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
