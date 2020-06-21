package org.melkweg.handle.async;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.async.scheduler.AsyncScheduler;
import org.melkweg.async.scheduler.FnAsyncEngineScheduler;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.handle.sync.SyncToolsFnHandle;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reorder Handler -> used to sub-pack all washable handle components
 */
public abstract class AsyncReorderFnHandler extends AsyncToolsFnHandle {

    public AsyncReorderFnHandler(String name){
        this(name, HandleType.REORDER_HANDLE_ASYNC);
    }

    private AsyncReorderFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        List<FnHandler> newFnHandlerList = new ArrayList<>(this.getChilds());
        reorderHandlerList(params,newFnHandlerList);
        new FnAsyncEngineScheduler().asyncRun(params,newFnHandlerList, paramWrapper -> asyncScheduler.next(paramWrapper,asyncResult));
    }

    public abstract void reorderHandlerList(AsyncParamWrapper paramWrapper, List<FnHandler> fnHandlers);

}
