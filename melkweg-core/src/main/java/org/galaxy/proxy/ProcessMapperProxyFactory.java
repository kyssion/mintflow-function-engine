package org.galaxy.proxy;

import org.galaxy.handle.StartHandler;
import org.galaxy.process.Process;

import java.lang.reflect.Proxy;
import java.util.Map;

public class ProcessMapperProxyFactory {

    @SuppressWarnings("unchecked")
    public <T extends Process> T newInstance(String nameSpace, Class<? extends Process> mapperInterface, Map<String, StartHandler> startHandleMap) {
        MapperProxy<T> mapperProxy = new MapperProxy<>(mapperInterface, startHandleMap);
        return (T) Proxy.newProxyInstance(
                mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy
        );
    }
}
