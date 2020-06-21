package org.mintflow.handler.async.sample;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.handle.async.AsyncSampleFnHandler;

@MintFlowHandler(name = "async_base_test_handle1")
public class AsyncBaseTestHandler1 extends AsyncSampleFnHandler {
    public AsyncBaseTestHandler1(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        Integer item = params.getParam(Integer.class);
        params.setParam(item+1);
        asyncScheduler.next(params,asyncResult);
    }
}
