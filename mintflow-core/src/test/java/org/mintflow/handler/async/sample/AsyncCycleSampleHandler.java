package org.mintflow.handler.async.sample;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;

import static org.mintflow.test.syncBaseTest.SyncCycleTest.ADD_DATA;

@MintFlowHandler(name = "async_cycle_sample_handler")
public class AsyncCycleSampleHandler extends AsyncSampleFnHandler {

    public AsyncCycleSampleHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        String item = params.getParam(String.class);
        params.setParam(item+ADD_DATA);
        asyncScheduler.next(params,asyncResult);
    }

}
