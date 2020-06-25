package org.mintflow.handler.async.sample;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.handle.async.AsyncSampleFnHandler;
import org.mintflow.test.syncBaseTest.ReorderTest;

@MintFlowHandler(name = "async_reorder_sample_handle")
public class AsyncReorderSampleHandler extends AsyncSampleFnHandler {

    public AsyncReorderSampleHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandler(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        String itme = params.getParam(String.class);
        params.setParam(itme+ ReorderTest.ADD_DATA);
        asyncScheduler.next(params,asyncResult);
    }
}
