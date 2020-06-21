package org.melkweg.handler.sync.simple;

import org.melkweg.annotation.MelkwegHandler;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;

import static org.melkweg.test.syncBaseTest.SyncCycleTest.ADD_DATA;

@MelkwegHandler(name = "sync_cycle_sample_handler")
public class CycleSampleHandler extends SyncSampleFnHandler {


    public CycleSampleHandler(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        String item = params.getParam(String.class);
        params.setParam(item+ADD_DATA);
        return params;
    }

}
