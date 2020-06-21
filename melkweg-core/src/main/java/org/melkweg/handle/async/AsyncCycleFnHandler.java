package org.melkweg.handle.async;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.scheduler.async.AsyncScheduler;
import org.melkweg.scheduler.async.FnAsyncEngineScheduler;
import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;

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
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        //create a new Async Scheduler for child process
        new FnAsyncEngineScheduler().asyncRun(params,this.getAsyncChildren(), paramWrapper -> asyncScheduler.next(paramWrapper,asyncResult));
    }

}
