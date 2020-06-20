package org.melkweg.handle.sync;

import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.List;

public abstract class SyncCycleFnHandler extends SyncToolsFnHandle {
    private List<FnHandler> cycleFnHandlerList = new ArrayList<>();
    protected SyncCycleFnHandler(String name) {
        this(name, HandleType.CYCLE_HANDLE_SYNC);
    }

    private SyncCycleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }
    public ParamWrapper handle(ParamWrapper paramWrapper, Scheduler scheduler){
        return scheduler.run(paramWrapper, cycleFnHandlerList);
    }

    public void setCycleFnHandlerList(List<FnHandler> cycleFnHandlerList) {
        this.cycleFnHandlerList = cycleFnHandlerList;
    }

    @Override
    public SyncCycleFnHandler clone() throws CloneNotSupportedException {
        SyncCycleFnHandler cycleHandler = (SyncCycleFnHandler)super.clone();
        cycleHandler.cycleFnHandlerList = new ArrayList<>();
        return cycleHandler;
    }
}
