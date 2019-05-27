package com.kyssion.galaxy;


import com.kyssion.galaxy.annotation.HandleId;
import com.kyssion.galaxy.handle.Handle;

@HandleId("testId")
public class TestHandle implements Handle<String, String> {

    @Override
    public void before() {

    }

    @Override
    public void after() {

    }

    @Override
    public void error() {

    }

    @Override
    public String handle(String s) {
        return null;
    }
}
