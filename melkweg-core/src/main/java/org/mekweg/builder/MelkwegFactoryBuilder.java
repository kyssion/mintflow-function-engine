package org.mekweg.builder;

import org.mekweg.Melkweg;
import org.mekweg.factory.DefaultMelkwegFactory;
import org.mekweg.factory.MelkwegFactory;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

/**
 * read config file build factory
 */
public class MelkwegFactoryBuilder implements AbstractMelkwegFactoryBuilder {
    private static final AtomicReference<MelkwegFactory> factory =
            new AtomicReference<>();

    @Override
    public MelkwegFactory build(Reader reader) {
        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return build(properties);
    }

    @Override
    public MelkwegFactory build(Properties properties) {
        MelkwegFactory melkwegFactory = factory.get();
        if (melkwegFactory == null) {
            melkwegFactory = new DefaultMelkwegFactory(
                    getPropString(properties, "galaxy.handle-path"),
                    getPropString(properties, "galaxy.map-path"),
                    getPropString(properties, "galaxy.process-path"));
            factory.compareAndSet(null, melkwegFactory);
            return factory.get();
        } else {
            return melkwegFactory;
        }
    }

    @Override
    public MelkwegFactory build(InputStream inputStream) {
        return build(new InputStreamReader(inputStream));
    }

    private String getPropString(Properties properties, String key) {
        return (String) properties.get(key);
    }

    @Override
    public MelkwegFactory build(String configPath) {
        File file = new File(configPath);
        try {
            return build(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
