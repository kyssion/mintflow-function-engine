package org.mintflow.handle.sync;

import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.exception.HandlerUseException;
import org.mintflow.handle.FnHandler;
import org.mintflow.handle.HandlerType;

public abstract class SyncFnHandler extends FnHandler {
    protected SyncFnHandler(String name) {
        super(name);
    }

    protected SyncFnHandler(String name, HandlerType handleType) {
        super(name, handleType);
    }

    @Override
    public final void asyncHandler(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        throw new HandlerUseException(HandlerUseException.NO_USE_SYNC + ",  handle name :" + this.getClass().getName());
    }

    public SyncFnHandler clone() throws CloneNotSupportedException {
        return (SyncFnHandler) super.clone();
    }
}
