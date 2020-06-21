package org.mintflow.handle.sync;

import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.exception.HandleUseException;
import org.mintflow.handle.FnHandler;
import org.mintflow.handle.HandleType;

public abstract class SyncFnHandler extends FnHandler {
    protected SyncFnHandler(String name) {
        super(name);
    }

    protected SyncFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public final void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        throw new HandleUseException(HandleUseException.NO_USE_SYNC + ",  handle name :" + this.getClass().getName());
    }

    public SyncFnHandler clone() throws CloneNotSupportedException {
        return (SyncFnHandler) super.clone();
    }
}
