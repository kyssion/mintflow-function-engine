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
import java.util.List;

public abstract class AsyncCycleFnHandler extends AsyncToolsFnHandle {
    private List<FnHandler> cycleFnHandlerList = new ArrayList<>();
    protected AsyncCycleFnHandler(String name) {
        this(name, HandleType.CYCLE_HANDLE_SYNC);
    }

    private AsyncCycleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }
    public ParamWrapper handle(ParamWrapper paramWrapper, Scheduler scheduler){
        return scheduler.run(paramWrapper, cycleFnHandlerList);
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        //create a new Async Scheduler for child process
        new FnAsyncEngineScheduler().asyncRun(params,cycleFnHandlerList, paramWrapper -> asyncScheduler.next(paramWrapper,asyncResult));
    }

    public void setCycleFnHandlerList(List<FnHandler> cycleFnHandlerList) {
        this.cycleFnHandlerList = cycleFnHandlerList;
    }

    @Override
    public AsyncCycleFnHandler clone() throws CloneNotSupportedException {
        AsyncCycleFnHandler cycleHandler = (AsyncCycleFnHandler)super.clone();
        cycleHandler.cycleFnHandlerList = new ArrayList<>();
        return cycleHandler;
    }
}
