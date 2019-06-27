package com.kyssion.galaxy;

import com.kyssion.galaxy.handle.header.StartHandler;
import com.kyssion.galaxy.process.Process;
import com.kyssion.galaxy.proxy.ProcessMapperProxyFactory;

import java.util.HashMap;
import java.util.Map;

public class Galaxy {

    private Map<String, StartHandler> headHanderMap;
    private Map<Class<? extends Process>, ? extends Process> processProxy;

    public Galaxy(Map<String, StartHandler> headHanderMap, Map<String, Class<? extends Process>> processMap) {
        this.headHanderMap = headHanderMap;
        //创建代理
        initProxy(headHanderMap, processMap);
    }

    private void initProxy(Map<String, StartHandler> startHandlerMap,
                           Map<String, Class<? extends Process>> processMap) {
        this.processProxy = new HashMap<>();
        ProcessMapperProxyFactory mapperProxyFactory = new ProcessMapperProxyFactory();
        for (Map.Entry<String, Class<? extends Process>> entry : processMap.entrySet()) {
            processProxy.put(entry.getValue(), mapperProxyFactory.newInstance(
                    entry.getKey(),entry.getValue(),startHandlerMap
            ));
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Process> T getProcess(Class<T> processClass) {
        if (processProxy != null) {
            return (T) processProxy.get(processClass);
        }
        return null;
    }

    public StartHandler getStartHandle(String name) {
        if (headHanderMap != null) {
            return this.headHanderMap.get(name);
        }
        return null;
    }
}
