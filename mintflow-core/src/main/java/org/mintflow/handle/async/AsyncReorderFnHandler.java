package org.mintflow.handle.async;

import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.scheduler.async.FnAsyncEngineScheduler;
import org.mintflow.handle.HandleType;

import java.util.ArrayList;
import java.util.List;

/**
 * Reorder Handler -> used to sub-pack all washable handle components
 */
public abstract class AsyncReorderFnHandler extends AsyncToolsFnHandler {

    public AsyncReorderFnHandler(String name){
        this(name, HandleType.REORDER_HANDLE_ASYNC);
    }

    private AsyncReorderFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        List<AsyncFnHandler> newFnHandlerList = new ArrayList<>(this.getAsyncChildren());
        reorderHandlerList(params,newFnHandlerList);
        new FnAsyncEngineScheduler(newFnHandlerList).next(params, paramWrapper -> asyncScheduler.next(paramWrapper,asyncResult));
    }

    public abstract void reorderHandlerList(ParamWrapper paramWrapper, List<AsyncFnHandler> fnHandlers);

}
