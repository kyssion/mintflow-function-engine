package org.melkweg.handle.async;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.async.scheduler.AsyncScheduler;
import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.HandleType;
import org.melkweg.handle.ToolsConditonHandlerWrapper;
import org.melkweg.param.ParamWrapper;

public abstract class AsyncToolsConditionHandlerWrapper extends ToolsConditonHandlerWrapper {
    protected AsyncToolsConditionHandlerWrapper(String name) {
        super(name);
    }

    protected AsyncToolsConditionHandlerWrapper(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public final ParamWrapper handle(ParamWrapper params) {
        throw new HandleUseException(HandleUseException.NO_USE_ASYNC + ",  handle name :" + this.getClass().getName());
    }

    public abstract void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler);
}
