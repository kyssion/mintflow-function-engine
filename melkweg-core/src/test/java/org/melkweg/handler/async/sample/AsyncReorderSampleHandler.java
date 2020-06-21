package org.melkweg.handler.async.sample;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.scheduler.async.AsyncScheduler;
import org.melkweg.handle.async.AsyncSampleFnHandler;
import org.melkweg.test.syncBaseTest.ReorderTest;

@MelkwegHander(name = "async_reorder_sample_handle")
public class AsyncReorderSampleHandler extends AsyncSampleFnHandler {

    public AsyncReorderSampleHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        String itme = params.getParam(String.class);
        params.setParam(itme+ ReorderTest.ADD_DATA);
        asyncScheduler.next(params,asyncResult);
    }
}
