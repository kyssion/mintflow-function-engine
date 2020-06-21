package org.melkweg.handle.sync;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.scheduler.async.AsyncScheduler;
import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;

public abstract class SyncFnHandler extends FnHandler {
    protected SyncFnHandler(String name) {
        super(name);
    }

    protected SyncFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public final void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        throw new HandleUseException(HandleUseException.NO_USE_SYNC + ",  handle name :" + this.getClass().getName());
    }

    public SyncFnHandler clone() throws CloneNotSupportedException {
        return (SyncFnHandler) super.clone();
    }
}
