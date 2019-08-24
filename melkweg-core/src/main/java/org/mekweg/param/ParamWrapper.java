package org.mekweg.param;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParamWrapper {

    private Map<String, Object> origalParams;

    private Map<String,Object> contextParams;

    private Map<Class<?>,Object> returnParams;

    private Set<String> deleteKeys;

    public ParamWrapper() {
        this.origalParams = new HashMap<>();
        this.contextParams = new HashMap<>();
        this.returnParams = new HashMap<>();
        this.deleteKeys = new HashSet<>();
    }

    public void putOrigalParams(String key,Object item){
        this.putOrigalParams(key,item);
    }

    public <T> T getOrigalParams(String key ,Class<T> params) {
        return (T) this.origalParams.get(key);
    }

    public void putContextParams(String key,Object item){
        this.putOrigalParams(key,item);
    }

    public void putContextParamsEndRemove(String key,Object item){
        this.deleteKeys.add(key);
        putContextParams(key,item);
    }

    public <T> T getContextParams(String key,Class<T> params){
        return (T) this.contextParams.get(key);
    }

    public void addRemoveKey(String key){
        this.deleteKeys.add(key);
    }

    public Set<String> getDeleteKeys(){
        return this.deleteKeys;
    }

    public <T> T getReturnParams(Class<T> key){
        return (T) returnParams.get(key);
    }

    public void addReturnParams(Object item){
        this.returnParams.put(item.getClass(),item);
    }

    public void removeReturnParams(Class<?> key){
        this.returnParams.remove(key);
    }

}
