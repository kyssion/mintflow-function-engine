package org.melkweg.handle.sync;

import org.melkweg.handle.HandleType;
import org.melkweg.param.CycleParam;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.sync.SyncScheduler;

import java.util.ArrayList;
import java.util.List;

public abstract class SyncCycleFnHandler extends SyncToolsFnHandler {

    protected SyncCycleFnHandler(String name) {
        this(name, HandleType.CYCLE_HANDLE_SYNC);
    }

    private SyncCycleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    public final ParamWrapper handle(ParamWrapper paramWrapper, SyncScheduler syncScheduler){
        CycleParam cycleParam= cycleHandler(paramWrapper);
        int times = cycleParam.getCycleTimes();
        ParamWrapper startParam = cycleParam.getStartParamWrapper();
        while(times>=0){
            startParam = syncScheduler.run(startParam,getSyncChildren());
            times--;
        }
        return startParam;
    }

    public abstract CycleParam cycleHandler(ParamWrapper paramWrapper);
}
