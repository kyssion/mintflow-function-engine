package org.mintFlow.vertxSupportExample.handle.async.reorder;

import org.mintFlow.vertxSupportExample.handle.async.sample.AsyncReorderSampleHandler;
import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.async.AsyncFnHandler;
import org.mintflow.handler.async.AsyncReorderFnHandler;
import org.mintflow.param.ParamWrapper;

import java.util.List;

@MintFlowHandler(name = "async_reorder_handle")
public class AsyncReorderHandler extends AsyncReorderFnHandler {
    public AsyncReorderHandler(String name) {
        super(name);
    }

    @Override
    public void reorderHandlerList(ParamWrapper paramWrapper, List<AsyncFnHandler> fnHandlers) {
        int num= (int) (Math.random()*10);
        paramWrapper.setContextParam("random_number",num);
        while(num>=0) {
            fnHandlers.add(new AsyncReorderSampleHandler("async_reorder_handle"));
            num--;
        }
    }
}
