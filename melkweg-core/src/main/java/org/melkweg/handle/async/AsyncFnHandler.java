package org.melkweg.handle.async;

import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;

public abstract class AsyncFnHandler extends FnHandler {
    protected AsyncFnHandler(String name) {
        super(name);
    }

    protected AsyncFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public final ParamWrapper handle(ParamWrapper params) {
        throw new HandleUseException(HandleUseException.NO_USE_ASYNC + ",  handle name :" + this.getClass().getName());
    }

    public AsyncFnHandler clone() throws CloneNotSupportedException {
        return (AsyncFnHandler) super.clone();
    }
}
