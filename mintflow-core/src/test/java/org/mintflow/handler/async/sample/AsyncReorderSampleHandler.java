package org.mintflow.handler.async.sample;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.handler.async.AsyncSampleFnHandler;

import static org.mintflow.test.asyncBaseTest.AsyncReorderTest.ADD_DATA_REORDER;

@MintFlowHandler(name = "async_reorder_sample_handle")
public class AsyncReorderSampleHandler extends AsyncSampleFnHandler {

    public static final  String ASYNC_REORDER_STR = "async_reorder_str";
    public AsyncReorderSampleHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        String itme = params.getContextParam(ASYNC_REORDER_STR);
        params.setContextParam(ASYNC_REORDER_STR,itme+ADD_DATA_REORDER);
        asyncScheduler.next(params,asyncResult);
    }
}
