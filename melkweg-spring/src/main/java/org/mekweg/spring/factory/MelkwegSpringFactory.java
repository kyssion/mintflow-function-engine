package org.mekweg.spring.factory;

import org.mekweg.Melkweg;
import org.mekweg.factory.MelkwegFactory;

public class MelkwegSpringFactory implements MelkwegFactory {
    private String[] handlePath;
    private String[] processPath;
    private String[] mapperPath;
    public MelkwegSpringFactory(String handPath, String mapperPath, String processPath) {
        this.handlePath = (handPath == null) ? null : handPath.split(",");
        this.mapperPath = (mapperPath == null) ? null : mapperPath.split(",");
        this.processPath = (processPath == null) ? null : processPath.split(",");
    }

    @Override
    public Melkweg create() {
        return null;
    }
}
