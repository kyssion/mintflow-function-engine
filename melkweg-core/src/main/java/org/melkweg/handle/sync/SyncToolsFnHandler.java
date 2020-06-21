package org.melkweg.handle.sync;

import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.sync.SyncScheduler;

import java.util.ArrayList;
import java.util.List;

public abstract class SyncToolsFnHandler extends SyncFnHandler {

    private List<SyncFnHandler> syncChildren = new ArrayList<>();

    public List<SyncFnHandler> getSyncChildren() {
        return syncChildren;
    }

    public void addChildren(SyncFnHandler syncFnHandler){
        this.syncChildren.add(syncFnHandler);
    }

    public void addChildren(List<SyncFnHandler> syncChildren){
        this.syncChildren.addAll(syncChildren);
    }

    protected SyncToolsFnHandler(String name) {
        super(name);
    }

    protected SyncToolsFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public final ParamWrapper handle(ParamWrapper params) {
        throw new HandleUseException(HandleUseException.CAN_NOT_USE_SIMPLE_HANDLE_METHOD_FOR_TOOLS_HANDLE);
    }

    public abstract ParamWrapper handle(ParamWrapper paramWrapper, SyncScheduler syncScheduler);

    public SyncToolsFnHandler clone() throws CloneNotSupportedException {
        SyncToolsFnHandler syncToolsFnHandle = (SyncToolsFnHandler) super.clone();
        syncToolsFnHandle.syncChildren = new ArrayList<>();
        return syncToolsFnHandle;
    }
}
