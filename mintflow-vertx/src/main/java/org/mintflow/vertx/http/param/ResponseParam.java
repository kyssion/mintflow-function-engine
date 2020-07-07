package org.mintflow.vertx.http.param;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import org.mintflow.vertx.http.util.HttpUtil;

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


    public void addDataToHttpServerResponse(HttpServerResponse httpServerResponse){
        if(this.getCookies()!=null){
            HttpUtil.ResponseUtil.addCookies(this.getCookies(),httpServerResponse);
        }
        if(this.getHeader()!=null){
            HttpUtil.ResponseUtil.addHeader(this.getHeader(),httpServerResponse);
        }
        if(this.getStatusCode()!=null){
            HttpUtil.ResponseUtil.addStatusCode(this.getStatusCode(),httpServerResponse);
        }
        if(this.getStatusMessage()!=null){
            HttpUtil.ResponseUtil.addStatusMessage(this.getStatusMessage(),httpServerResponse);
        }
        HttpUtil.ResponseUtil.addData(this.getData(),httpServerResponse);
    }
}
