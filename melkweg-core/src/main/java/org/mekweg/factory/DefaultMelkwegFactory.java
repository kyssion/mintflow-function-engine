package org.mekweg.factory;

import org.mekweg.Melkweg;
import org.mekweg.builder.HandleMapBuilder;
import org.mekweg.builder.ProcessMapBuilder;
import org.mekweg.builder.StartHandleMapBuilder;
import org.mekweg.handle.Handle;
import org.mekweg.handle.StartHandler;
import org.mekweg.process.Process;

import java.util.Map;

public class DefaultMelkwegFactory implements MelkwegFactory {

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
            Melkweg melkwegOld = finalMelweg.get();
            //所有处理方法的集合
            Map<String, Handle> handleMap = HandleMapBuilder.handleMapbuild(this.handlePath);
            Map<String, Class<? extends Process>> processMap = ProcessMapBuilder.build(this.processPath);
            //创建handle调用链
            Map<String, StartHandler> startHanderMap = StartHandleMapBuilder.build(handleMap, this.mapperPath);
            Melkweg melkwegNew = new Melkweg(startHanderMap, processMap);
            finalMelweg.compareAndSet(melkwegOld, melkwegNew);
            return finalMelweg.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getHandlePath() {
        return handlePath;
    }

    public void setHandlePath(String[] handlePath) {
        this.handlePath = handlePath;
    }

    public String[] getProcessPath() {
        return processPath;
    }

    public void setProcessPath(String[] processPath) {
        this.processPath = processPath;
    }

    public String[] getMapperPath() {
        return mapperPath;
    }

    public void setMapperPath(String[] mapperPath) {
        this.mapperPath = mapperPath;
    }
}
