package org.mekweg.builder;

import org.mekweg.factory.DefaultMelkwegFactory;
import org.mekweg.factory.MelkwegFactory;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

/**
 * read config file build factory
 */
public class MelkwegFactoryBuilder {
    private static final AtomicReference<MelkwegFactory> factory =
            new AtomicReference<>();

    public static MelkwegFactory build(Reader reader) {
        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return build(properties);
    }

    public static MelkwegFactory build(Properties properties) {
        MelkwegFactory galaxyFactory = factory.get();
        if (galaxyFactory == null) {
            galaxyFactory = new DefaultMelkwegFactory(
                    getPropString(properties, "galaxy.handle-path"),
                    getPropString(properties, "galaxy.map-path"),
                    getPropString(properties, "galaxy.process-path"));
            factory.compareAndSet(null, galaxyFactory);
            return factory.get();
        } else {
            return galaxyFactory;
        }
    }

    public static MelkwegFactory build(InputStream inputStream) {
        return build(new InputStreamReader(inputStream));
    }

    private static String getPropString(Properties properties, String key) {
        return (String) properties.get(key);
    }

    public static MelkwegFactory build(String configPath){
        File file = new File(configPath);
        try {
            return build(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
