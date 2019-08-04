package org.mekweg.builder;

import org.mekweg.factory.MelkwegFactory;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public interface AbstractMelkwegFactoryBuilder {

    AtomicReference<MelkwegFactory> finalMelkwegFactory = new AtomicReference<>();

    MelkwegFactory build0(Properties properties);

    default MelkwegFactory getMelKewgFactory() {
        return finalMelkwegFactory.get();
    }

    default MelkwegFactory build(Reader reader) {
        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return build(properties);
    }

    default MelkwegFactory build(Properties properties) {
        MelkwegFactory melkwegFactoryOld = finalMelkwegFactory.get();
        MelkwegFactory melkwegFactoryNew = build0(properties);
        finalMelkwegFactory.compareAndSet(melkwegFactoryOld, melkwegFactoryNew);
        return finalMelkwegFactory.get();
    }

    default MelkwegFactory build(InputStream inputStream) {
        return build(new InputStreamReader(inputStream));
    }


    default MelkwegFactory build(String configPath) {
        File file = new File(configPath);
        try {
            return build(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
