package org.mintflow.param;

import java.util.HashMap;
import java.util.Map;
/**
 * Used to describe data parameters in the process
 *
 * In the future, it will be a bridge for linking function parameters and process context data
 */
public class ParamWrapper {
    private boolean isSuccess = true;
    private Exception exception = null;
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

    public void setContextParam(String key,Object value){
        this.contextParams.put(key,value);
    }

    public void setParam(Object object){
        this.params.put(object.getClass(),object);
    }

    public void setParam(Class<?> type , Object item){
        this.params.put(type,item);
    }

    @SuppressWarnings("unchecked")
    public <T> T getParam(Class<?> type){
        return (T) params.get(type);
    }

    @SuppressWarnings("unchecked")
    public <T> T getContextParam(String name){
        return (T) contextParams.get(name);
    }

    @SuppressWarnings("unchecked")
    public <T> T getResult(Class<T> type){
        return (T) params.get(type);
    }

    public void fail(){
        this.isSuccess = false;
    }

    public boolean isSuccess(){
        return this.isSuccess;
    }

    public void setException(Exception e){
        this.exception = e;
    }

    public Exception getException(){
        return exception;
    }
}
