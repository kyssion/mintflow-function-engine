package com.kyssion.galaxy;

import com.kyssion.galaxy.builder.GalaxyFactoryBuilder;

import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        Galaxy galaxy = GalaxyFactoryBuilder.build(new Properties()).create();
        System.out.println("123123");
    }
}
