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


    public SyncReorderFnHandler(String name){
        this(name, HandleType.REORDER_HANDLE_SYNC);
    }

    private SyncReorderFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    public void addChilds(FnHandler... fnHandlers) {
        this.getChilds().addAll(Arrays.asList(fnHandlers));
    }

    public void addChilds(List<FnHandler> fnHandlers) {
        this.getChilds().addAll(fnHandlers);
    }


    @Override
    public ParamWrapper handle(ParamWrapper paramWrapper, Scheduler scheduler) {
        if (this.getChilds() == null || this.getChilds().size() == 0) {
            return paramWrapper;
        }
        if (scheduler != null) {
            List<FnHandler> newFnHandlerList = new ArrayList<>(this.getChilds());
            reorderHandlerList(newFnHandlerList);
            return scheduler.run(paramWrapper, this.getChilds());
        } else {
            return paramWrapper;
        }
    }

    public abstract void reorderHandlerList(List<FnHandler> fnHandlers);
}
