package com.kyssion.galaxy.test.handler;

import com.kyssion.galaxy.annotation.Handler;
import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.param.ParamWrapper;

@Handler(value = "one")
public class TestHandleOne implements Handle {

    @Override
    public void before() {

    }

    @Override
    public void after() {

    }

    @Override
    public void error(Exception e) {
        System.out.println("no need handle");
    }

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        String name = p.get(String.class);
        name = name+"this.is.handler.one";
        p.put(name);
        p.put(123123);
        return p;
    }
}
