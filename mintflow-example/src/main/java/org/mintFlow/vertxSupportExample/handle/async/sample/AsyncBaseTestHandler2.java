package org.mintFlow.vertxSupportExample.handle.async.sample;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;

@MintFlowHandler(name = "async_base_test_handle2")
public class AsyncBaseTestHandler2 extends AsyncSampleFnHandler {
    public AsyncBaseTestHandler2(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        Integer item = params.getParam(Integer.class);
        params.setParam(item+2);
        asyncScheduler.next(params,asyncResult);
    }

}
