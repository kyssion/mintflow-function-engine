package org.mintFlow.vertxSupportExample.handle.async.sample;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;

@MintFlowHandler(name = "async_reorder_sample_handle")
public class AsyncReorderSampleHandler extends AsyncSampleFnHandler {

    private static final String ADD_DATA = "qqqqq";

    public AsyncReorderSampleHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        String itme = params.getParam(String.class);
        params.setParam(itme+ADD_DATA);
        asyncScheduler.next(params,asyncResult);
    }
}
