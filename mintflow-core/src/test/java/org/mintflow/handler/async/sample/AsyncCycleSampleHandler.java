package org.mintflow.handler.async.sample;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;

import static org.mintflow.test.asyncBaseTest.AsyncCycleTest.ADD_DATA_CYCLE;

@MintFlowHandler(name = "async_cycle_sample_handler")
public class AsyncCycleSampleHandler extends AsyncSampleFnHandler {

    public static final   String async_cycle_str = "async_cycle_str";

    public AsyncCycleSampleHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        String item = params.getContextParam(async_cycle_str);
        params.setContextParam(async_cycle_str,item+ADD_DATA_CYCLE);
        asyncScheduler.next(params,asyncResult);
    }

}
