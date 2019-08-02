package org.mekweg.spring.builder;

import org.mekweg.builder.AbstractMelkwegFactoryBuilder;
import org.mekweg.factory.MelkwegFactory;

import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

public class MelkwegSpringFacotryBuilder implements AbstractMelkwegFactoryBuilder {

    @Override
    public MelkwegFactory build(Reader reader) {
        return null;
    }

    @Override
    public MelkwegFactory build(Properties properties) {
        return null;
    }

    @Override
    public MelkwegFactory build(InputStream inputStream) {
        return null;
    }

    @Override
    public MelkwegFactory build(String configPath) {
        return null;
    }
}

