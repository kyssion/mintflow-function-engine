package org.melkweg.handler.async.sample;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.async.scheduler.AsyncScheduler;
import org.melkweg.handle.async.AsyncSampleFnHandler;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "async_base_test_handle2")
public class AsyncBaseTestHandle2 extends AsyncSampleFnHandler {
    public AsyncBaseTestHandle2(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        Integer item = params.getParam(Integer.class);
        params.setParam(item+2);
        asyncScheduler.next(params,asyncResult);
    }

}
