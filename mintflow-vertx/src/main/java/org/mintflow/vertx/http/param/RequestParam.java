package org.mintflow.vertx.http.param;

import io.vertx.core.MultiMap;
import io.vertx.core.http.Cookie;

import java.util.Map;

public class RequestParam {

    private String body;
    private MultiMap headers;
    private MultiMap params;
    private MultiMap formAttributes;
    private Map<String, Cookie> cookieMap;

    public MultiMap getParams() {
        return params;
    }

    public void setParams(MultiMap params) {
        this.params = params;
    }

    public MultiMap getHeaders() {
        return headers;
    }

    public void setHeaders(MultiMap headers) {
        this.headers = headers;
    }

    public MultiMap getFormAttributes() {
        return formAttributes;
    }

    public void setFormAttributes(MultiMap formAttributes) {
        this.formAttributes = formAttributes;
    }

    public Map<String, Cookie> getCookieMap() {
        return cookieMap;
    }

    public void setCookieMap(Map<String, Cookie> cookieMap) {
        this.cookieMap = cookieMap;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
