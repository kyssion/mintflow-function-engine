package org.mekweg.handle;

import org.mekweg.param.ParamWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Reorder Handler -> used to sub-pack all washable handle components
 */
public abstract class ReorderHandler extends Handler {

    public ReorderHandler() {
        this.setType(HandleType.REORDER_HANDLE);
    }

    private List<Handler> childHandlrs = new ArrayList<>();

    public void addChilds(Handler... handlers) {
        childHandlrs.addAll(Arrays.asList(handlers));
    }

    public void addChilds(List<Handler> handlers) {
        this.childHandlrs.addAll(handlers);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        if (this.childHandlrs == null || this.childHandlrs.size() == 0) {
            return params;
        }
        if (this.getScheduler() != null) {
            reorderHandlerList(this.childHandlrs);
            return this.getScheduler().run(params, this.childHandlrs);
        } else {
            return params;
        }
    }

    public abstract void reorderHandlerList(List<Handler> handlers);

    @Override
    public ReorderHandler clone() throws CloneNotSupportedException {
        ReorderHandler reorderHandler = (ReorderHandler) super.clone();
        reorderHandler.childHandlrs = new ArrayList<>();
        return reorderHandler;
    }
}
