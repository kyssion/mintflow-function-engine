package com.kyssion.galaxy.test.handler;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.mark.Type;
import com.kyssion.galaxy.param.ParamWrapper;

public class TestHandleOne implements Handle {
    @Override
    public Type type() {
        return Type.HANDLE;
    }

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
    public ParamWrapper handle(ParamWrapper p) {
        String name = p.get(String.class);
        name = name+"this.is.handler.one";
        p.put(name);
        return p;
    }
}
