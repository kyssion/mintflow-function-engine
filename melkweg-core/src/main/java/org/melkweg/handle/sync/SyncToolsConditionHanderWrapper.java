package org.melkweg.handle.sync;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.async.scheduler.AsyncScheduler;
import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.HandleType;
import org.melkweg.handle.ToolsConditonHandlerWrapper;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.Scheduler;

public abstract class SyncToolsConditionHanderWrapper extends ToolsConditonHandlerWrapper {

    protected SyncToolsConditionHanderWrapper(String name) {
        super(name);
    }

    protected SyncToolsConditionHanderWrapper(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public final ParamWrapper handle(ParamWrapper params) {
        throw new HandleUseException(HandleUseException.CAN_NOT_USE_SIMPLE_HANDLE_METHOD_FOR_TOOLS_HANDLE);
    }

    @Override
    public final void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        throw new HandleUseException(HandleUseException.NO_USE_SYNC + ",  handle name :" + this.getClass().getName());
    }

    public abstract ParamWrapper handle(ParamWrapper paramWrapper, Scheduler scheduler);
}
