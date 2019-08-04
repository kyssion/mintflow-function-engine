package org.mekweg.builder;

import org.mekweg.factory.DefaultMelkwegFactory;
import org.mekweg.factory.MelkwegFactory;

import java.util.Properties;

/**
 * read config file build factory
 */
public class MelkwegFactoryBuilder implements AbstractMelkwegFactoryBuilder {
    @Override
    public MelkwegFactory build0(Properties properties) {
        return new DefaultMelkwegFactory(
                getPropString(properties, "galaxy.handle-path"),
                getPropString(properties, "galaxy.map-path"),
                getPropString(properties, "galaxy.process-path"));
    }

    private String getPropString(Properties properties, String key) {
        return (String) properties.get(key);
    }
}
