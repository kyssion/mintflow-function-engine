package org.mintFlow.vertxSupportExample.handle.async.sample;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;

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
