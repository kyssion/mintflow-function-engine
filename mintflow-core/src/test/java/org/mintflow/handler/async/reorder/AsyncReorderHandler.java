package org.mintflow.handler.async.reorder;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.handler.async.AsyncFnHandler;
import org.mintflow.handler.async.AsyncReorderFnHandler;
import org.mintflow.handler.async.sample.AsyncReorderSampleHandler;

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
