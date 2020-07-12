package org.mintflow.handler.async.sample;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.test.syncBaseTest.ReorderTest;

@MintFlowHandler(name = "async_reorder_sample_handle")
public class AsyncReorderSampleHandler extends AsyncSampleFnHandler {

    public static final  String async_reorder_str = "async_reorder_str";
    public AsyncReorderSampleHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        String itme = params.getContextParam(async_reorder_str);
        params.setContextParam(async_reorder_str,itme+ ReorderTest.ADD_DATA);
        asyncScheduler.next(params,asyncResult);
    }
}
