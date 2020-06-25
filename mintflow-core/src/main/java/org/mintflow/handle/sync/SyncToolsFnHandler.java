package org.mintflow.handle.sync;

import org.mintflow.exception.HandlerUseException;
import org.mintflow.handle.HandlerType;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.sync.SyncScheduler;

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

    protected SyncToolsFnHandler(String name, HandlerType handleType) {
        super(name, handleType);
    }

    @Override
    public final ParamWrapper handle(ParamWrapper params) {
        throw new HandlerUseException(HandlerUseException.CAN_NOT_USE_SIMPLE_HANDLE_METHOD_FOR_TOOLS_HANDLE);
    }

    public abstract ParamWrapper handle(ParamWrapper paramWrapper, SyncScheduler syncScheduler);

    public SyncToolsFnHandler clone() throws CloneNotSupportedException {
        SyncToolsFnHandler syncToolsFnHandler = (SyncToolsFnHandler) super.clone();
        syncToolsFnHandler.syncChildren = new ArrayList<>();
        return syncToolsFnHandler;
    }
}
