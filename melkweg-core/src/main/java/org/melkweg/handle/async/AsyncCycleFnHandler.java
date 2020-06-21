package org.melkweg.handle.async;

import org.melkweg.param.ParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.scheduler.async.AsyncScheduler;
import org.melkweg.scheduler.async.FnAsyncEngineScheduler;
import org.melkweg.handle.HandleType;

public abstract class AsyncCycleFnHandler extends AsyncToolsFnHandler {
    protected AsyncCycleFnHandler(String name) {
        this(name, HandleType.CYCLE_HANDLE_ASYNC);
    }

    private AsyncCycleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }
    public ParamWrapper handle(ParamWrapper paramWrapper, AsyncScheduler asyncScheduler){
        return null;
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        //create a new Async Scheduler for child process
        new FnAsyncEngineScheduler().asyncRun(params,this.getAsyncChildren(), paramWrapper -> asyncScheduler.next(paramWrapper,asyncResult));
    }

}
