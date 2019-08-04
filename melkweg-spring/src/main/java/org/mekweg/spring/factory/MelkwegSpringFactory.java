package org.mekweg.spring.factory;

import org.mekweg.Melkweg;
import org.mekweg.factory.DefaultMelkwegFactory;
import org.mekweg.factory.MelkwegFactory;

public class MelkwegSpringFactory implements MelkwegFactory {
    private String handlePath;
    private String processPath;
    private String mapperPath;

    public MelkwegSpringFactory(String handPath, String mapperPath, String processPath) {
        this.handlePath = handPath;
        this.mapperPath = mapperPath;
        this.processPath = processPath;
    }

    @Override
    public Melkweg create() {
        DefaultMelkwegFactory defaultMelkwegFactory =
                new DefaultMelkwegFactory(handlePath, mapperPath, processPath);
        return defaultMelkwegFactory.create();
    }
}
