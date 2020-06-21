package org.melkweg.handler.async.sample;

import org.melkweg.annotation.MelkwegHandler;
import org.melkweg.param.ParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.scheduler.async.AsyncScheduler;
import org.melkweg.handle.async.AsyncSampleFnHandler;

@MelkwegHandler(name = "async_base_test_handle1")
public class AsyncBaseTestHandler1 extends AsyncSampleFnHandler {
    public AsyncBaseTestHandler1(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        Integer item = params.getParam(Integer.class);
        params.setParam(item+1);
        asyncScheduler.next(params,asyncResult);
    }
}
