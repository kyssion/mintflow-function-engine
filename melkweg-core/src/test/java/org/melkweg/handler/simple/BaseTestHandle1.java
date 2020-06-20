package org.melkweg.handler.simple;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "base_test_handle1")
public class BaseTestHandle1 extends SyncSampleFnHandler {
    public BaseTestHandle1(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        Integer item = params.getParam(Integer.class);
        params.setParam(item+1);
        return params;
    }
}
