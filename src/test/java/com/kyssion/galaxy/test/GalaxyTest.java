package com.kyssion.galaxy.test;

import com.kyssion.galaxy.Galaxy;
import com.kyssion.galaxy.builder.GalaxyFactoryBuilder;
import com.kyssion.galaxy.factory.GalaxyFactory;
import com.kyssion.galaxy.test.process.TestProcess;

import java.util.Objects;

public class GalaxyTest {
    public static void main(String[] args) {
        System.out.println(Objects.requireNonNull(GalaxyTest.class.getClassLoader().getResource("galaxy-test.properties")).getFile());
        GalaxyFactory factory = GalaxyFactoryBuilder.build(GalaxyTest.class.getClassLoader().getResource("galaxy-test.properties").getFile());
        Galaxy galaxy = factory.create();
        //-------------------
        TestProcess process = galaxy.getProcess(TestProcess.class);
        int name = process.sayName(0);
        System.out.println(name);
    }
}
