package org.mintflow.vertx.param;

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
    private String data;
    private MultiMap cookies;
    private boolean chunked;
    private int statusMessage;
    private MultiMap header;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public int getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(int statusMessage) {
        this.statusMessage = statusMessage;
    }

    public MultiMap getHeader() {
        return header;
    }

    public void setHeader(MultiMap header) {
        this.header = header;
    }
}
