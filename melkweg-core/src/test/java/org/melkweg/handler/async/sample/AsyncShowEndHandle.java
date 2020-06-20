package org.melkweg.handler.async.sample;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.async.scheduler.AsyncScheduler;
import org.melkweg.handle.async.AsyncFnHandle;
import org.melkweg.handle.async.AsyncSampleFnHandler;
import org.melkweg.handle.async.AsyncToolsFnHandle;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "async_show_end_handle")
public class AsyncShowEndHandle extends AsyncSampleFnHandler {

    public AsyncShowEndHandle(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        params.setContextParam("show_end",true);
        asyncScheduler.next(params,asyncResult);
    }
}
