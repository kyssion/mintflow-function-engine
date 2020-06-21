package org.melkweg.handler.async.reorder;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.async.AsyncReorderFnHandler;
import org.melkweg.handle.sync.SyncReorderFnHandler;
import org.melkweg.handler.async.sample.AsyncReorderSampleHandle;
import org.melkweg.handler.sync.simple.ReorderSampleHandle;
import org.melkweg.param.ParamWrapper;

import java.util.List;

@MelkwegHander(name = "async_reorder_handle")
public class AsyncReorderHandler extends AsyncReorderFnHandler {
    public AsyncReorderHandler(String name) {
        super(name);
    }

    @Override
    public void reorderHandlerList(AsyncParamWrapper paramWrapper,List<FnHandler> fnHandlers) {
        int num= (int) (Math.random()*10);
        paramWrapper.setContextParam("random_number",num);
        while(num>=0) {
            fnHandlers.add(new AsyncReorderSampleHandle("async_reorder_handle"));
            num--;
        }
    }
}
