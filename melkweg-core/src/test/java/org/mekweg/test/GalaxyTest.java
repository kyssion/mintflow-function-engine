package org.mekweg.test;

import org.mekweg.Melkweg;
import org.mekweg.builder.MelkwegFactoryBuilder;
import org.mekweg.factory.MelkwegFactory;
import org.mekweg.test.process.TestProcess;

import java.util.Objects;

public class GalaxyTest {
    public static void main(String[] args) {
        System.out.println(Objects.requireNonNull(GalaxyTest.class.getClassLoader().getResource("galaxy-test.properties")).getFile());
        MelkwegFactoryBuilder melkwegFactoryBuilder = new MelkwegFactoryBuilder();
        MelkwegFactory factory = melkwegFactoryBuilder.build(GalaxyTest.class.getClassLoader().getResource("galaxy-test.properties").getFile());
        Melkweg melkweg = factory.create();
        //-------------------
        TestProcess process = melkweg.getProcess(TestProcess.class);
        Integer name = process.sayName(0);
        System.out.println(name);
    }
}
