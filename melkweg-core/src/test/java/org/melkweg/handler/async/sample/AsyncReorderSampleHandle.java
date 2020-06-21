package org.melkweg.handler.async.sample;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.async.scheduler.AsyncScheduler;
import org.melkweg.handle.async.AsyncSampleFnHandler;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;
import org.melkweg.test.syncBaseTest.ReorderTest;

@MelkwegHander(name = "async_reorder_sample_handle")
public class AsyncReorderSampleHandle extends AsyncSampleFnHandler {

    public AsyncReorderSampleHandle(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        String itme = params.getParam(String.class);
        params.setParam(itme+ ReorderTest.ADD_DATA);
        asyncScheduler.next(params,asyncResult);
    }
}
