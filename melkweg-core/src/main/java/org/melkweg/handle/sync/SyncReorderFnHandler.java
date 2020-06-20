package org.melkweg.handle.sync;

import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Reorder Handler -> used to sub-pack all washable handle components
 */
public abstract class SyncReorderFnHandler extends SyncToolsFnHandle {


    private List<FnHandler> childHandlrs = new ArrayList<>();

    public SyncReorderFnHandler(String name){
        this(name, HandleType.REORDER_HANDLE__SYNC);
    }

    private SyncReorderFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    public void addChilds(FnHandler... fnHandlers) {
        childHandlrs.addAll(Arrays.asList(fnHandlers));
    }

    public void addChilds(List<FnHandler> fnHandlers) {
        this.childHandlrs.addAll(fnHandlers);
    }


    @Override
    public ParamWrapper handle(ParamWrapper paramWrapper, Scheduler scheduler) {
        if (this.childHandlrs == null || this.childHandlrs.size() == 0) {
            return paramWrapper;
        }
        if (scheduler != null) {
            List<FnHandler> newFnHandlerList = new ArrayList<>(this.childHandlrs);
            reorderHandlerList(newFnHandlerList);
            return scheduler.run(paramWrapper, this.childHandlrs);
        } else {
            return paramWrapper;
        }
    }

    public abstract void reorderHandlerList(List<FnHandler> fnHandlers);

    @Override
    public SyncReorderFnHandler clone() throws CloneNotSupportedException {
        SyncReorderFnHandler reorderHandler = (SyncReorderFnHandler) super.clone();
        reorderHandler.childHandlrs = new ArrayList<>();
        return reorderHandler;
    }
}
