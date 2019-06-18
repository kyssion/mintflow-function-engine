package com.kyssion.galaxy;

import com.kyssion.galaxy.handle.header.StartHander;
import com.kyssion.galaxy.process.Process;

import java.lang.reflect.Proxy;
import java.util.Map;

public class Galaxy {

    private Map<String, StartHander> headHanderMap;
    private Map<Class<? extends Process>, Proxy> processProxy;

    public Galaxy(Map<String, StartHander> headHanderMap, Map<String, Class<? extends Process>> processMap) {
        this.headHanderMap = headHanderMap;
        //创建代理
    }

    @SuppressWarnings("unchecked")
    public <T extends Process> T getProcess(Class<T> processClass) {
        if (processProxy != null) {
            return (T) processProxy.get(processClass);
        }
        return null;
    }

    public StartHander getStartHandle(String name){
        if(headHanderMap!=null){
            return this.headHanderMap.get(name);
        }
        return null;
    }
}
