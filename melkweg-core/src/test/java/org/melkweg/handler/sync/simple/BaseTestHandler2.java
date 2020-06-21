package org.melkweg.handler.sync.simple;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "base_test_handle2")
public class BaseTestHandler2 extends SyncSampleFnHandler {
    public BaseTestHandler2(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        Integer item = params.getParam(Integer.class);
        params.setParam(item+2);
        return params;
    }
}
