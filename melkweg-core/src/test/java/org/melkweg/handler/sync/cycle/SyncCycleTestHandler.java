package org.melkweg.handler.sync.cycle;

import org.melkweg.annotation.MelkwegHandler;
import org.melkweg.handle.sync.SyncCycleFnHandler;
import org.melkweg.param.CycleParam;
import org.melkweg.param.ParamWrapper;

@MelkwegHandler(name = "sync_cycle_test")
public class SyncCycleTestHandler extends SyncCycleFnHandler {

    public SyncCycleTestHandler(String name) {
        super(name);
    }

    @Override
    public CycleParam cycleHandler(ParamWrapper paramWrapper) {
        int times = (int) (Math.random()*10);
        paramWrapper.setContextParam("random_number",times);
        return new CycleParam(times,paramWrapper);
    }
}
