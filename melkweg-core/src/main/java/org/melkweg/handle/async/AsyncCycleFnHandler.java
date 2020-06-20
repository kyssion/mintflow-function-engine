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
    protected AsyncCycleFnHandler(String name) {
        this(name, HandleType.CYCLE_HANDLE_ASYNC);
    }

    private AsyncCycleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }
    public ParamWrapper handle(ParamWrapper paramWrapper, Scheduler scheduler){
        return scheduler.run(paramWrapper, this.getChilds());
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        //create a new Async Scheduler for child process
        new FnAsyncEngineScheduler().asyncRun(params,this.getChilds(), paramWrapper -> asyncScheduler.next(paramWrapper,asyncResult));
    }

}
