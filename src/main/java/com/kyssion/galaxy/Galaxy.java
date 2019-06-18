package com.kyssion.galaxy;

import com.kyssion.galaxy.handle.header.HeadHander;
import com.kyssion.galaxy.process.Process;

import java.lang.reflect.Proxy;
import java.util.Map;

public class Galaxy {

    private Map<String, HeadHander> headHanderMap;
    private Map<Class<? extends Process>, Proxy> processProxy;

    public Galaxy(Map<String, HeadHander> headHanderMap, Map<String, Class<? extends Process>> processMap) {
        this.headHanderMap = headHanderMap;

    }
}
