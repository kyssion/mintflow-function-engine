package org.melkweg.handler.sync.simple;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "base_test_handle3")
public class BaseTestHandler3 extends SyncSampleFnHandler {
    public BaseTestHandler3(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        Integer item = params.getParam(Integer.class);
        params.setParam(item+3);
        return params;
    }
}
