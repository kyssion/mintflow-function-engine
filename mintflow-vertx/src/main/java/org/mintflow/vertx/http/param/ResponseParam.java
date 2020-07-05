package org.mintflow.vertx.http.param;

import io.vertx.core.MultiMap;

/**
 *         event.response().addCookie();
 *         event.response().setChunked();
 *         event.response().setStatusCode();
 *         event.response().setStatusMessage();
 *         event.response().putHeader();
 *         event.response().write()
 */
public class ResponseParam {
    private Integer statusCode;
    private String statusMessage;
    private Object data;
    private MultiMap cookies;
    private boolean chunked;
    private MultiMap header;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MultiMap getCookies() {
        return cookies;
    }

    public void setCookies(MultiMap cookies) {
        this.cookies = cookies;
    }

    public boolean isChunked() {
        return chunked;
    }

    public void setChunked(boolean chunked) {
        this.chunked = chunked;
    }

    public MultiMap getHeader() {
        return header;
    }

    public void setHeader(MultiMap header) {
        this.header = header;
    }
}
