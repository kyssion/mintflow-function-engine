package org.melkweg.handler.async.sample;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.scheduler.async.AsyncScheduler;
import org.melkweg.handle.async.AsyncSampleFnHandler;

@MelkwegHander(name = "async_show_start_handle")
public class AsyncShowStartHandler extends AsyncSampleFnHandler {

    public AsyncShowStartHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        params.setContextParam("show_start",true);
        asyncScheduler.next(params,asyncResult);
    }
}
