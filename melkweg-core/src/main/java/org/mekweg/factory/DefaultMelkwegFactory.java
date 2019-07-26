package org.mekweg.factory;

import org.mekweg.Melkweg;
import org.mekweg.builder.HandleMapBuilder;
import org.mekweg.builder.ProcessMapBuilder;
import org.mekweg.builder.StartHandleMapBuilder;
import org.mekweg.handle.Handle;
import org.mekweg.handle.StartHandler;
import org.mekweg.process.Process;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class DefaultMelkwegFactory implements MelkwegFactory {

    private static final AtomicReference<Melkweg> galaxyCache =
            new AtomicReference<>();

    private String[] handlePath;
    private String[] processPath;
    private String[] mapperPath;


    public DefaultMelkwegFactory(String handPath, String mapperPath, String processPath) {
        this.handlePath = (handPath == null) ? null : handPath.split(",");
        this.mapperPath = (mapperPath == null) ? null : mapperPath.split(",");
        this.processPath = (processPath == null) ? null : processPath.split(",");
    }

    @Override
    public Melkweg create() {
        try {
            Melkweg melkweg = galaxyCache.get();
            if (melkweg == null) {
                //所有处理方法的集合
                Map<String, Handle> handleMap = HandleMapBuilder.handleMapbuild(this.handlePath);
                Map<String, Class<? extends Process>> processMap = ProcessMapBuilder.build(this.processPath);
                //创建handle调用链
                Map<String, StartHandler> startHanderMap = StartHandleMapBuilder.build(handleMap,this.mapperPath);
                melkweg = new Melkweg(startHanderMap, processMap);
                galaxyCache.compareAndSet(null, melkweg);
                return galaxyCache.get();
            } else {
                return melkweg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
