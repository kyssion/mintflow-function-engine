package org.melkweg.handle;

import org.melkweg.param.ParamWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Reorder Handler -> used to sub-pack all washable handle components
 */
public abstract class ReorderFnHandler extends SyncFnHandle {


    private List<FnHandler> childHandlrs = new ArrayList<>();

    public ReorderFnHandler(String name){
        this(name,HandleType.REORDER_HANDLE);
    }

    private ReorderFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    public void addChilds(FnHandler... fnHandlers) {
        childHandlrs.addAll(Arrays.asList(fnHandlers));
    }

    public void addChilds(List<FnHandler> fnHandlers) {
        this.childHandlrs.addAll(fnHandlers);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        if (this.childHandlrs == null || this.childHandlrs.size() == 0) {
            return params;
        }
        if (this.getScheduler() != null) {
            List<FnHandler> newFnHandlerList = new ArrayList<>(this.childHandlrs);
            reorderHandlerList(newFnHandlerList);
            return this.getScheduler().run(params, this.childHandlrs);
        } else {
            return params;
        }
    }

    public abstract void reorderHandlerList(List<FnHandler> fnHandlers);

    @Override
    public ReorderFnHandler clone() throws CloneNotSupportedException {
        ReorderFnHandler reorderHandler = (ReorderFnHandler) super.clone();
        reorderHandler.childHandlrs = new ArrayList<>();
        return reorderHandler;
    }
}
