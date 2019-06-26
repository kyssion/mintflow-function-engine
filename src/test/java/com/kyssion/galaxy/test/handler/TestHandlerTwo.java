package com.kyssion.galaxy.test.handler;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.param.ParamWrapper;

public class TestHandlerTwo implements Handle {
    @Override
    public ParamWrapper handle(ParamWrapper p) {
        String name = p.get(String.class);
        name = name+"this.is.handler.two";
        p.put(name);
        return p;
    }
}
