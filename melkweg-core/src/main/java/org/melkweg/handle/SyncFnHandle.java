package org.melkweg.handle;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.exception.HandleUseSyncException;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.Scheduler;

public abstract class SyncFnHandle extends FnHandler {

    private Scheduler scheduler;

    protected SyncFnHandle(String name) {
        super(name);
    }

    protected SyncFnHandle(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult) {
        throw new HandleUseSyncException(HandleUseSyncException.NO_USE_SYNC + ",  handle name :" + this.getClass().getName());
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
