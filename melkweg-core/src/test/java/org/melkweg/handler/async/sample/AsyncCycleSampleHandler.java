package org.melkweg.handler.async.sample;

import org.melkweg.annotation.MelkwegHandler;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.handle.async.AsyncSampleFnHandler;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.async.AsyncScheduler;

import static org.melkweg.test.syncBaseTest.SyncCycleTest.ADD_DATA;

@MelkwegHandler(name = "async_cycle_sample_handler")
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
