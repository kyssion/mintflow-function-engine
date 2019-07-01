package com.kyssion.galaxy.test.handler;

import com.kyssion.galaxy.annotation.Handler;
import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.param.ParamWrapper;
@Handler(value = "two")
public class TestHandlerTwo implements Handle {
    @Override
    public ParamWrapper handle(ParamWrapper p) {
        Integer integer = p.get(Integer.class);
        p.put(integer+1);
        return p;
    }
}
