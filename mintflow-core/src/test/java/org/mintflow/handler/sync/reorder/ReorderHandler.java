package org.mintflow.handler.sync.reorder;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.sync.SyncFnHandler;
import org.mintflow.handler.sync.SyncReorderFnHandler;
import org.mintflow.handler.sync.simple.ReorderSampleHandler;
import org.mintflow.param.ParamWrapper;

import java.util.List;

@MintFlowHandler(name = "reorder_handle")
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
