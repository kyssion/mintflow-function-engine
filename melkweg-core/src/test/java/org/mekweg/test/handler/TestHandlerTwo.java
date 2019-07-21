package org.mekweg.test.handler;

import org.mekweg.annotation.Handler;
import org.mekweg.handle.Handle;
import org.mekweg.param.ParamWrapper;

@Handler(value = "two")
public class TestHandlerTwo implements Handle {
    @Override
    public ParamWrapper handle(ParamWrapper p) {
        Integer integer = p.get(Integer.class);
        p.put(integer+1);
        return p;
    }
}
