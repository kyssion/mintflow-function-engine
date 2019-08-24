package org.mekweg.test.handler;

import org.mekweg.annotation.Handler;
import org.mekweg.handle.Handle;
import org.mekweg.param.ParamWrapper;

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
        return p;
    }
}
