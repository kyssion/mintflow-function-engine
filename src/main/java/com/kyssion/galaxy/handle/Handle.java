package com.kyssion.galaxy.handle;

public interface Handle<T,P> {

    default void before(){ }

    default void after(){ }

    default void error(){}

    T handle(P p);
}
