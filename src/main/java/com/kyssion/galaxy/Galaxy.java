package com.kyssion.galaxy;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.process.Process;

import java.util.HashMap;
import java.util.Map;

public class Galaxy {

    private Map<String, Handle> handleMap;
    private Map<String, Class<? extends Process>> processMap;
    private Map<Class<? extends Process>, Object> proxy;

    public Galaxy(Map<String, Handle> handleMap,
                  Map<String, Class<? extends Process>> processMap) {
        this.handleMap = handleMap;
        this.processMap = processMap;
        proxy = new HashMap<>();
        initProxy();
    }

    private void initProxy() {

    }
}
