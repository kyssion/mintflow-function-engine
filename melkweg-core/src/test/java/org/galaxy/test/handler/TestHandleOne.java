package org.galaxy.test.handler;

import org.galaxy.annotation.Handler;
import org.galaxy.handle.Handle;
import org.galaxy.param.ParamWrapper;

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
        Integer integer = p.get(Integer.class);
        p.put(integer + 1);
        return p;
    }
}
