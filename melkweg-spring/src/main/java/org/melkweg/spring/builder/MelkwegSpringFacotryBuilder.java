package org.melkweg.spring.builder;

import org.mekweg.builder.AbstractMelkwegFactoryBuilder;
import org.mekweg.factory.MelkwegFactory;
import org.melkweg.spring.factory.MelkwegSpringFactory;

import java.util.Properties;

public class MelkwegSpringFacotryBuilder implements AbstractMelkwegFactoryBuilder {

    @Override
    public MelkwegFactory build0(Properties properties) {
        MelkwegFactory melkwegFactoryOld = finalMelkwegFactory.get();
        MelkwegFactory melkwegFactoryNew = new MelkwegSpringFactory(properties.getProperty("hande_path"),
                properties.getProperty("mapper_path"),properties.getProperty("process_path"));
        finalMelkwegFactory.compareAndSet(melkwegFactoryOld,melkwegFactoryNew);
        return finalMelkwegFactory.get();
    }

}

