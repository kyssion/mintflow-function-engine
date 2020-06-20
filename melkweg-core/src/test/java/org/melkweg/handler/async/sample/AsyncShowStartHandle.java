package org.melkweg.handler.async.sample;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.async.scheduler.AsyncScheduler;
import org.melkweg.handle.async.AsyncSampleFnHandler;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "async_show_start_handle")
public class AsyncShowStartHandle extends AsyncSampleFnHandler {

    public AsyncShowStartHandle(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        params.setContextParam("show_start",true);
        asyncScheduler.next(params,asyncResult);
    }
}
