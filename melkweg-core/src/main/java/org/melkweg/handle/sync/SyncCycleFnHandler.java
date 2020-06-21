package org.melkweg.handle.sync;

import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.sync.SyncScheduler;

public abstract class SyncCycleFnHandler extends SyncToolsFnHandler {
    protected SyncCycleFnHandler(String name) {
        this(name, HandleType.CYCLE_HANDLE_SYNC);
    }

    private SyncCycleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }
    public ParamWrapper handle(ParamWrapper paramWrapper, SyncScheduler syncScheduler){
        return syncScheduler.run(paramWrapper,this.getSyncChildren() );
    }

}
