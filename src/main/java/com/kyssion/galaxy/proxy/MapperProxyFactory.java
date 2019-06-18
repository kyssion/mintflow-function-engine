package com.kyssion.galaxy.proxy;

import java.lang.reflect.Proxy;
import java.util.Map;

public class MapperProxyFactory {

    private Map<Class<? extends Process>,Proxy> processMap;



    @SuppressWarnings("unchecked")
    public <T> T newInstance(Class<T> mapperInterface){
        MapperProxy<T> mapperProxy = new MapperProxy<>(mapperInterface);
        return (T) Proxy.newProxyInstance(
                mapperInterface.getClassLoader(), new Class[]{mapperInterface.getClass()},mapperProxy
        );
    }
}
