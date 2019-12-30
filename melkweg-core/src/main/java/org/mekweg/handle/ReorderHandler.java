package org.mekweg.handle;

import org.mekweg.param.ParamWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ReorderHandler extends Handler {

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
        return null;
    }

    @Override
    public ReorderHandler clone() throws CloneNotSupportedException {
        ReorderHandler reorderHandler = (ReorderHandler) super.clone();
        reorderHandler.childHandlrs = new ArrayList<>();
        return reorderHandler;
    }
}
