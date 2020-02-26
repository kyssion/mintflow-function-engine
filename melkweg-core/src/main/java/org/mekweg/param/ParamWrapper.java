package org.mekweg.param;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to describe data parameters in the process
 *
 * In the future, it will be a bridge for linking function parameters and process context data
 */
public class ParamWrapper {
    private Map<Class<?>,Object> typeParams = new HashMap<>();

    private Map<String,Object> nameParams = new HashMap<>();

    public void addParams(Class<?> key,Object item){
        typeParams.put(key,item);
    }

    public void addParams(String key,Object item){
        nameParams.put(key,item);
    }

    public Map<Class<?>, Object> getTypeParams() {
        return typeParams;
    }

    public Map<String, Object> getNameParams() {
        return nameParams;
    }
}
