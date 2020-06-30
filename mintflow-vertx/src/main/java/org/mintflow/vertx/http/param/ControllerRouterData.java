package org.mintflow.vertx.http.param;

import org.mintflow.vertx.http.adapter.ResponseParamAdapter;

import java.lang.reflect.Method;

public class ControllerRouterData<T> {
    private String nameSpace;
    private String process;
    private Method method;
    private Object item;
    private ResponseParamAdapter<T> responseParamAdapter;

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
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

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
