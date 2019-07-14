package org.galaxy.test;

import org.galaxy.Galaxy;
import org.galaxy.builder.GalaxyFactoryBuilder;
import org.galaxy.factory.GalaxyFactory;
import org.galaxy.test.process.TestProcess;

import java.util.Objects;

public class GalaxyTest {
    public static void main(String[] args) {
        System.out.println(Objects.requireNonNull(GalaxyTest.class.getClassLoader().getResource("galaxy-test.properties")).getFile());
        GalaxyFactory factory = GalaxyFactoryBuilder.build(GalaxyTest.class.getClassLoader().getResource("galaxy-test.properties").getFile());
        Galaxy galaxy = factory.create();
        //-------------------
        TestProcess process = galaxy.getProcess(TestProcess.class);
        Integer name = process.sayName(0);
        System.out.println(name);
    }
}
