package org.mintflow.handle.async;

import org.mintflow.exception.HandlerUseException;
import org.mintflow.handle.FnHandler;
import org.mintflow.handle.HandlerType;
import org.mintflow.param.ParamWrapper;

public abstract class AsyncFnHandler extends FnHandler {
    protected AsyncFnHandler(String name) {
        super(name);
    }

    protected AsyncFnHandler(String name, HandlerType handleType) {
        super(name, handleType);
    }

    @Override
    public final ParamWrapper handle(ParamWrapper params) {
        throw new HandlerUseException(HandlerUseException.NO_USE_ASYNC + ",  handle name :" + this.getClass().getName());
    }

    public AsyncFnHandler clone() throws CloneNotSupportedException {
        return (AsyncFnHandler) super.clone();
    }
}
