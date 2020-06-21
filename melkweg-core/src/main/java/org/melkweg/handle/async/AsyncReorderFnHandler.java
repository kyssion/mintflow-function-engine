package org.melkweg.handle.async;

import org.melkweg.param.ParamWrapper;
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
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        List<AsyncFnHandler> newFnHandlerList = new ArrayList<>(this.getAsyncChildren());
        reorderHandlerList(params,newFnHandlerList);
        new FnAsyncEngineScheduler(newFnHandlerList).next(params, paramWrapper -> asyncScheduler.next(paramWrapper,asyncResult));
    }

    public abstract void reorderHandlerList(ParamWrapper paramWrapper, List<AsyncFnHandler> fnHandlers);

}
