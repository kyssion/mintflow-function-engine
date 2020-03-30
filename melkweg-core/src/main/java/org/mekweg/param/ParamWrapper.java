package org.mekweg.param;

import java.util.HashMap;
import java.util.Map;
/**
 * Used to describe data parameters in the process
 *
 * In the future, it will be a bridge for linking function parameters and process context data
 */
public class ParamWrapper<R,T extends TreeParams<R>> {
    private T paramsTree;
    private Map<Class<?>,Object> params = new HashMap<>();
    private Map<String,Object> contextParams = new HashMap<>();

    public ParamWrapper (T paramsTree){
        this.paramsTree = paramsTree;
    }

    @SuppressWarnings("unchecked")
    public ParamWrapper(){
        this.paramsTree = (T) new TreeParams<>();
    }

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

    public R getReturn(){
        return paramsTree.getReturn();
    }
}
