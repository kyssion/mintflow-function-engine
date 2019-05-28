package com.kyssion.galaxy;

import com.kyssion.galaxy.handle.Handle;

import java.util.Map;

public class Galaxy {
    private Map<String, Handle> handleMap;
    private Map<String, Process> processMap;

    public Galaxy(Map<String, Handle> handleMap,
                  Map<String, Process> processMap) {
        this.handleMap = handleMap;
        this.processMap = processMap;
    }


}
