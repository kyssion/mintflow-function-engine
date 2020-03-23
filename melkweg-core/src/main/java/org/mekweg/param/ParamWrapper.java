package org.mekweg.param;

import java.util.HashMap;
import java.util.Map;

public class ParamWrapper<T extends TreeParams> {
    private T paramsTree;
    private Map<Class<?>,Object> params = new HashMap<>();
    private Map<String,Object> contextParams = new HashMap<>();
    public Map<Class<?>, Object> getParams() {
        return params;
    }
    public void setParams(Map<Class<?>, Object> params) {
        this.params = params;
    }
    public Map<String, Object> getContextParams() {
        return contextParams;
    }
    public void setContextParams(Map<String, Object> contextParams) {
        this.contextParams = contextParams;
    }
    public T getparamsTree(){
        return paramsTree;
    }
    public void setParamsTree(T t){
        this.paramsTree = t;
    }

    public <R> R getReturn(){
        return paramsTree.getReturn();
    }
}
