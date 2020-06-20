package org.melkweg.handle.async;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.async.scheduler.AsyncScheduler;
import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.HandleType;
import org.melkweg.handle.sync.SyncFnHandle;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.Scheduler;

public abstract class AsyncToolsFnHandle extends AsyncFnHandle {

    protected AsyncToolsFnHandle(String name) {
        super(name);
    }

    protected AsyncToolsFnHandle(String name, HandleType handleType) {
        super(name, handleType);
    }

    public abstract void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler);
}
