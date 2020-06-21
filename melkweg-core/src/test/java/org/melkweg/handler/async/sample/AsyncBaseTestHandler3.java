package org.melkweg.handler.async.sample;

import org.melkweg.annotation.MelkwegHandler;
import org.melkweg.param.ParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.scheduler.async.AsyncScheduler;
import org.melkweg.handle.async.AsyncSampleFnHandler;

@MelkwegHandler(name = "async_base_test_handle3")
public class AsyncBaseTestHandler3 extends AsyncSampleFnHandler {

    public AsyncBaseTestHandler3(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        Integer item = params.getParam(Integer.class);
        params.setParam(item+3);
        asyncScheduler.next(params,asyncResult);
    }
}
