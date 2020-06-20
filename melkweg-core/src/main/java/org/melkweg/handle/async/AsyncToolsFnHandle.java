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

    @Override
    public final void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult) {
        throw new HandleUseException(HandleUseException.CAN_NOT_USE_SIMPLE_HANDLE_METHOD_FOR_TOOLS_HANDLE);
    }

    public abstract void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler);
}
