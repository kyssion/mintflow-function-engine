package org.mintflow.handler.async.sample;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.handle.async.AsyncSampleFnHandler;

@MintFlowHandler(name = "async_show_end_handle")
public class AsyncShowEndHandler extends AsyncSampleFnHandler {

    public AsyncShowEndHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        params.setContextParam("show_end",true);
        asyncScheduler.next(params,asyncResult);
    }
}
