package org.mintflow.handler.sync;

import org.mintflow.handler.HandlerType;
import org.mintflow.param.CycleParam;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.sync.SyncScheduler;

public abstract class SyncCycleFnHandler extends SyncToolsFnHandler {

    public SyncCycleFnHandler(String name) {
        this(name, HandlerType.CYCLE_HANDLE_SYNC);
    }

    private SyncCycleFnHandler(String name, HandlerType handleType) {
        super(name, handleType);
    }

    public final ParamWrapper handle(ParamWrapper paramWrapper, SyncScheduler syncScheduler){
        CycleParam cycleParam= cycleHandler(paramWrapper);
        int times = cycleParam.getCycleTimes();
        ParamWrapper startParam = cycleParam.getStartParamWrapper();
        while(times>0){
            startParam = syncScheduler.run(startParam,getSyncChildren());
            times--;
        }
        return startParam;
    }

    public abstract CycleParam cycleHandler(ParamWrapper paramWrapper);
}
