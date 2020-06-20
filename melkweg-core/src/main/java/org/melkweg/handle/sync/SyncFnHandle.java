package org.melkweg.handle.sync;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;

public abstract class SyncFnHandle extends FnHandler {
    protected SyncFnHandle(String name) {
        super(name);
    }

    protected SyncFnHandle(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public final void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult) {
        throw new HandleUseException(HandleUseException.NO_USE_SYNC + ",  handle name :" + this.getClass().getName());
    }
}
