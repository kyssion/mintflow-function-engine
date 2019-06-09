package com.kyssion.galaxy.builder;

import com.kyssion.galaxy.factory.DefaultGalaxyFactory;
import com.kyssion.galaxy.factory.GalaxyFactory;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

/**
 * read config file build factory
 */
public class GalaxyFactoryBuilder {
    private static final AtomicReference<GalaxyFactory> factory =
            new AtomicReference<>();

    public static GalaxyFactory build(Reader reader) {
        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return build(properties);
    }

    public static GalaxyFactory build(Properties properties) {
        GalaxyFactory galaxyFactory = factory.get();
        if (galaxyFactory == null) {
            galaxyFactory = new DefaultGalaxyFactory(
                    getPropString(properties, "galaxy.handle-path"),
                    getPropString(properties, "galaxy.handle-path"),
                    getPropString(properties, "galaxy.handle-path"));
            factory.compareAndSet(null, galaxyFactory);
            return factory.get();
        } else {
            return galaxyFactory;
        }
    }

    public static GalaxyFactory build(InputStream inputStream) {
        return build(new InputStreamReader(inputStream));
    }

    private static String getPropString(Properties properties, String key) {
        return (String) properties.get("key");
    }
}
