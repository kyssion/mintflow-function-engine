package com.kyssion.galaxy.builder;

import com.kyssion.galaxy.factory.DefaultGalaxyFactory;
import com.kyssion.galaxy.factory.GalaxyFactory;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class GalaxyFactoryBuilder {
    public AtomicReference<GalaxyFactory> factory =
            new AtomicReference<>();

    public GalaxyFactory build(Reader reader) {
        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return build(properties);
    }

    public GalaxyFactory build(Properties properties) {
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

    public GalaxyFactory build(InputStream inputStream) {
        return build(new InputStreamReader(inputStream));
    }

    private String getPropString(Properties properties, String key) {
        return (String) properties.get("key");
    }
}
