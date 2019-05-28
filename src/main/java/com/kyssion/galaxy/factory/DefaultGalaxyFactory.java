package com.kyssion.galaxy.factory;

import com.kyssion.galaxy.Galaxy;
import com.kyssion.galaxy.builder.HandleMapBuilder;
import com.kyssion.galaxy.builder.ProcessMapBuilder;
import com.kyssion.galaxy.handle.Handle;

import java.util.Map;

public class DefaultGalaxyFactory implements GalaxyFactory {
    private String handlePath;
    private String processPath;
    private String mapperPath;

    public DefaultGalaxyFactory(String handPath, String mapperPath, String processPath) {
        this.handlePath = handPath;
        this.mapperPath = mapperPath;
        this.processPath = processPath;
    }

    @Override
    public Galaxy create() {
        Map<String, Handle> handleMap = HandleMapBuilder.build(this.handlePath);
        Map<String, Process> processMap = ProcessMapBuilder.build(this.processPath);
        return new Galaxy(handleMap,processMap);
    }
}
