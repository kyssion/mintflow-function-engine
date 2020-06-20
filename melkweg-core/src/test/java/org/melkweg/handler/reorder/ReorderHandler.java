package org.melkweg.handler.reorder;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.sync.SyncReorderFnHandler;
import org.melkweg.handler.simple.ReorderSampleHandle;
import org.melkweg.param.ParamWrapper;

import java.util.List;

@MelkwegHander(name = "reorder_handle")
public class ReorderHandler extends SyncReorderFnHandler {
    public ReorderHandler(String name) {
        super(name);
    }

    @Override
    public void reorderHandlerList(ParamWrapper paramWrapper, List<FnHandler> fnHandlers) {
        int num= (int) (Math.random()*10);
        paramWrapper.setContextParam("random_number",num);
        while(num>=0) {
            fnHandlers.add(new ReorderSampleHandle("reorder_handle"));
            num--;
        }
    }
}
