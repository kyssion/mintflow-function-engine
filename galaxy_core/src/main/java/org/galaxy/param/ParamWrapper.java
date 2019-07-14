package org.galaxy.param;

import java.util.HashMap;
import java.util.Map;

public class ParamWrapper {



    private Map<Class, Object> param;
    private Map<Class,Object> common;

    private Map<Class,Object> status;

    public ParamWrapper() {
        this.param = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> item) {
        return (T) param.get(item);
    }

    public void put(Object item) {
        if (item != null) {
            this.param.put(item.getClass(), item);
        }
    }
}
