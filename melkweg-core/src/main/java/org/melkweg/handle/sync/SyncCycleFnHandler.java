package org.melkweg.handle.sync;

import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class SyncCycleFnHandler extends SyncFnHandle {
    private List<FnHandler> cycleFnHandlerList = new ArrayList<>();
    protected SyncCycleFnHandler(String name) {
        this(name, HandleType.CYCLE_HANDLE);
    }

    private SyncCycleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    protected ParamWrapper cycleHandle(ParamWrapper paramWrapper){
        return this.getScheduler().run(paramWrapper, cycleFnHandlerList);
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
