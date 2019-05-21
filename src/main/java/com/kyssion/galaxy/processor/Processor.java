package com.kyssion.galaxy.processor;

public interface Processor {

    default void before(){ }

    default void after(){ }

    default void error(){}

    <T, P> T handle(P p);
}
