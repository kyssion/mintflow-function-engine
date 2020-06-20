package org.melkweg.handler.sync.simple;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.syncBaseTest.ReorderTest;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "reorder_sample_handle")
public class ReorderSampleHandle extends SyncSampleFnHandler {

    public ReorderSampleHandle(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        String itme = params.getParam(String.class);
        params.setParam(itme+ ReorderTest.ADD_DATA);
        return params;
    }
}
