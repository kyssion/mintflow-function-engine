package org.melkweg.handle.async;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.scheduler.async.AsyncScheduler;
import org.melkweg.scheduler.async.FnAsyncEngineScheduler;
import org.melkweg.handle.HandleType;

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
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        List<AsyncFnHandler> newFnHandlerList = new ArrayList<>(this.getAsyncChildren());
        reorderHandlerList(params,newFnHandlerList);
        new FnAsyncEngineScheduler().asyncRun(params,newFnHandlerList, paramWrapper -> asyncScheduler.next(paramWrapper,asyncResult));
    }

    public abstract void reorderHandlerList(AsyncParamWrapper paramWrapper, List<AsyncFnHandler> fnHandlers);

}
