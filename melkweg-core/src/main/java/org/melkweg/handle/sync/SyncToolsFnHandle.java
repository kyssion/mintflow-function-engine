package org.melkweg.handle.sync;

import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.Scheduler;

public abstract class SyncToolsFnHandle extends SyncFnHandle{

    protected SyncToolsFnHandle(String name) {
        super(name);
    }

    protected SyncToolsFnHandle(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public final ParamWrapper handle(ParamWrapper params) {
        throw new HandleUseException(HandleUseException.CAN_NOT_USE_SIMPLE_HANDLE_METHOD_FOR_TOOLS_HANDLE);
    }

    public abstract ParamWrapper handle(ParamWrapper paramWrapper, Scheduler scheduler);
}
