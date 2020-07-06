package org.mintflow.vertx.http.controller.bean;

public class DefaultResponseBean {
    private String name;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
