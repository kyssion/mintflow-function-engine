package org.melkweg.handle.async;

import org.melkweg.param.CycleParam;
import org.melkweg.param.ParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.scheduler.async.AsyncScheduler;
import org.melkweg.scheduler.async.FnAsyncEngineScheduler;
import org.melkweg.handle.HandleType;

import java.util.ArrayList;
import java.util.List;

public abstract class AsyncCycleFnHandler extends AsyncToolsFnHandler {

    protected AsyncCycleFnHandler(String name) {
        this(name, HandleType.CYCLE_HANDLE_ASYNC);
    }

    private AsyncCycleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    public abstract CycleParam cycleHandler(ParamWrapper paramWrapper);

    @Override
    public final void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        CycleParam cycleParam = cycleHandler(params);
        int times = cycleParam.getCycleTimes();
        ParamWrapper startParamWrapper = cycleParam.getStartParamWrapper();
        List<AsyncFnHandler> cycleList = new ArrayList<>();
        while(times>=0){
            cycleList.addAll(this.getAsyncChildren());
            times--;
        }
        new FnAsyncEngineScheduler(cycleList).next(params,nextParams->{
            asyncScheduler.next(nextParams,asyncResult);
        });
    }

}
