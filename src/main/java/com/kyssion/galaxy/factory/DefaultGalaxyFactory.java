package com.kyssion.galaxy.factory;

import com.kyssion.galaxy.Galaxy;

public class DefaultGalaxyFactory implements GalaxyFactory {
    private String handlePath;
    private String processPath;
    private String mapperPath;

    public DefaultGalaxyFactory(String handPath, String mapperPath, String processPath){
        this.handlePath = handPath;
        this.mapperPath = mapperPath;
        this.processPath = processPath;
    }

    @Override
    public Galaxy create() {
        return new Galaxy();
    }
}
