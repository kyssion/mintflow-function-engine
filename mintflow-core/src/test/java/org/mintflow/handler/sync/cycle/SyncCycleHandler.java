package org.mintflow.handler.sync.cycle;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.sync.SyncCycleFnHandler;
import org.mintflow.param.CycleParam;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "sync_cycle_test")
public class SyncCycleHandler extends SyncCycleFnHandler {

    private static final String random_number_cycle="random_number_cycle";

    public SyncCycleHandler(String name) {
        super(name);
    }

    @Override
    public CycleParam cycleHandler(ParamWrapper paramWrapper) {
        int times = (int) (Math.random()*10);
        paramWrapper.setContextParam(random_number_cycle,times);
        return new CycleParam(times,paramWrapper);
    }
}
