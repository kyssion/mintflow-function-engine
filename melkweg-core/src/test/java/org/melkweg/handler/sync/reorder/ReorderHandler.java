package org.melkweg.handler.sync.reorder;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.sync.SyncFnHandler;
import org.melkweg.handle.sync.SyncReorderFnHandler;
import org.melkweg.handler.sync.simple.ReorderSampleHandler;
import org.melkweg.param.ParamWrapper;

import java.util.List;

@MelkwegHander(name = "reorder_handle")
public class ReorderHandler extends SyncReorderFnHandler {
    public ReorderHandler(String name) {
        super(name);
    }

    @Override
    public void reorderHandlerList(ParamWrapper paramWrapper, List<SyncFnHandler> fnHandlers) {
        int num= (int) (Math.random()*10);
        paramWrapper.setContextParam("random_number",num);
        while(num>=0) {
            fnHandlers.add(new ReorderSampleHandler("reorder_handle"));
            num--;
        }
    }
}
