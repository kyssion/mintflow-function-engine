package com.kyssion.galaxy.test;

import com.kyssion.galaxy.Galaxy;
import com.kyssion.galaxy.builder.GalaxyFactoryBuilder;
import com.kyssion.galaxy.factory.GalaxyFactory;
import com.kyssion.galaxy.test.process.TestProcess;

public class GalaxyTest {
    public static void main(String[] args) {
        GalaxyFactory factory = GalaxyFactoryBuilder.build("galaxy_config.properties");
        Galaxy galaxy = factory.create();
        TestProcess process = galaxy.getProcess(TestProcess.class);
        String name = process.sayName("test");
    }
}
