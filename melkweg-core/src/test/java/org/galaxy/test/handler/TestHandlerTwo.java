package org.galaxy.test.handler;

import org.galaxy.annotation.Handler;
import org.galaxy.handle.Handle;
import org.galaxy.param.ParamWrapper;

@Handler(value = "two")
public class TestHandlerTwo implements Handle {
    @Override
    public ParamWrapper handle(ParamWrapper p) {
        Integer integer = p.get(Integer.class);
        p.put(integer+1);
        return p;
    }
}
