package org.mintflow.handler.async;

import org.mintflow.param.CycleParam;
import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.scheduler.async.FnAsyncEngineScheduler;
import org.mintflow.handler.HandlerType;

import java.util.ArrayList;
import java.util.List;

public abstract class AsyncCycleFnHandler extends AsyncToolsFnHandler {

    protected AsyncCycleFnHandler(String name) {
        this(name, HandlerType.CYCLE_HANDLE_ASYNC);
    }

    private AsyncCycleFnHandler(String name, HandlerType handleType) {
        super(name, handleType);
    }

    public abstract CycleParam cycleHandler(ParamWrapper paramWrapper);

    @Override
    public final void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        CycleParam cycleParam = cycleHandler(params);
        int times = cycleParam.getCycleTimes();
        ParamWrapper startParamWrapper = cycleParam.getStartParamWrapper();
        List<AsyncFnHandler> cycleList = new ArrayList<>();
        while(times>0){
            cycleList.addAll(this.getAsyncChildren());
            times--;
        }
        new FnAsyncEngineScheduler(cycleList).next(startParamWrapper,nextParams->{
            asyncScheduler.next(nextParams,asyncResult);
        });
    }
    /**
     * 使用递归调用性能太差，这里暂时使用copy方法来实现
     * @Override
     * public final void asyncHandler(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
     *     CycleParam cycleParam = cycleHandler(params);
     *     runCycleHandler(cycleParam.getStartParamWrapper(),0,cycleParam.getCycleTimes(),asyncResult,asyncScheduler);
     * }
     *
     * private void runCycleHandler(ParamWrapper startParamWrapper, int nowTimes, int cycleTimes, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
     *     if(nowTimes==cycleTimes){
     *         asyncScheduler.next(startParamWrapper,asyncResult);
     *         return;
     *     }
     *     new FnAsyncEngineScheduler(this.getAsyncChildren()).next(startParamWrapper,param->{
     *         runCycleHandler(param,nowTimes+1,cycleTimes,asyncResult,asyncScheduler);
     *     });
     * }
     */
}
