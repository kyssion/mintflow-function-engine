package com.kyssion.galaxy.factory;

import com.kyssion.galaxy.Galaxy;
import com.kyssion.galaxy.builder.HandleMapBuilder;
import com.kyssion.galaxy.builder.ProcessMapBuilder;
import com.kyssion.galaxy.builder.StartHandleMapBuilder;
import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.header.StartHander;
import com.kyssion.galaxy.process.Process;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class DefaultGalaxyFactory implements GalaxyFactory {

    private static final AtomicReference<Galaxy> galaxyCache =
            new AtomicReference<>();

    private String[] handlePath;
    private String[] processPath;
    private String[] mapperPath;

    public DefaultGalaxyFactory(String handPath, String mapperPath, String processPath) {
        this.handlePath = (handPath == null) ? null : handPath.split(",");
        this.mapperPath = (mapperPath == null) ? null : mapperPath.split(",");
        this.processPath = (processPath == null) ? null : processPath.split(",");
    }

    @Override
    public Galaxy create() {
        try {
            Galaxy galaxy = galaxyCache.get();
            if (galaxy == null) {
                //所有处理方法的集合
                Map<String, Handle> handleMap = HandleMapBuilder.handleMapbuild(this.handlePath);
                Map<String, Class<? extends Process>> processMap = ProcessMapBuilder.build(this.processPath);
                //创建handle调用链
                Map<String, StartHander> startHanderMap = StartHandleMapBuilder.build(handleMap,this.mapperPath);
                galaxy = new Galaxy(startHanderMap, processMap);
                galaxyCache.compareAndSet(null, galaxy);
                return galaxyCache.get();
            } else {
                return galaxy;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
