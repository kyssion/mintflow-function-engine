package org.melkweg.handler.async.reorder;

import org.melkweg.annotation.MelkwegHandler;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.handle.async.AsyncFnHandler;
import org.melkweg.handle.async.AsyncReorderFnHandler;
import org.melkweg.handler.async.sample.AsyncReorderSampleHandler;

import java.util.List;

@MelkwegHandler(name = "async_reorder_handle")
public class AsyncReorderHandler extends AsyncReorderFnHandler {
    public AsyncReorderHandler(String name) {
        super(name);
    }

    @Override
    public void reorderHandlerList(AsyncParamWrapper paramWrapper,List<AsyncFnHandler> fnHandlers) {
        int num= (int) (Math.random()*10);
        paramWrapper.setContextParam("random_number",num);
        while(num>=0) {
            fnHandlers.add(new AsyncReorderSampleHandler("async_reorder_handle"));
            num--;
        }
    }
}
