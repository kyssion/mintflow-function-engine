package org.mintflow.handler.sync.simple;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handle.sync.SyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;

import static org.mintflow.test.syncBaseTest.SyncCycleTest.ADD_DATA;

@MintFlowHandler(name = "sync_cycle_sample_handler")
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
