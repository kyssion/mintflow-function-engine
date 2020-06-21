package org.mintflow.handle.async;

import org.mintflow.exception.HandleUseException;
import org.mintflow.handle.FnHandler;
import org.mintflow.handle.HandleType;
import org.mintflow.param.ParamWrapper;

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
