package org.mintflow.handler.sync.simple;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.sync.SyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;

import static org.mintflow.test.syncBaseTest.SyncCycleTest.ADD_DATA_CYCLE;

@MintFlowHandler(name = "sync_cycle_sample_handler")
public class CycleSampleHandler extends SyncSampleFnHandler {

    public static final String SYNC_CYCLE_STR="sync_cycle_str";

    public CycleSampleHandler(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        String item = params.getContextParam(SYNC_CYCLE_STR);
        params.setContextParam(SYNC_CYCLE_STR,item+ADD_DATA_CYCLE);
        return params;
    }

}
