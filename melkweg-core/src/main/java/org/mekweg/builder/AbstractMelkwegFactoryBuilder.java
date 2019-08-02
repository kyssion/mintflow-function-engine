package org.mekweg.builder;

import org.mekweg.factory.MelkwegFactory;

import java.io.*;
import java.util.Properties;

public interface AbstractMelkwegFactoryBuilder {
    MelkwegFactory build(Reader reader);

    MelkwegFactory build(Properties properties);

    MelkwegFactory build(InputStream inputStream);

    MelkwegFactory build(String configPath);
}
