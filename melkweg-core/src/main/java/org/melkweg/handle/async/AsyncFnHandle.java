package org.melkweg.handle.async;

import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;

public abstract class AsyncFnHandle extends FnHandler {
    protected AsyncFnHandle(String name) {
        super(name);
    }

    protected AsyncFnHandle(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public final ParamWrapper handle(ParamWrapper params) {
        throw new HandleUseException(HandleUseException.NO_USE_ASYNC + ",  handle name :" + this.getClass().getName());
    }
}
