package org.mintflow.simple.handle;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.HandlerType;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;
@MintFlowHandler(name="end_async_handle")
public class EndHandle extends AsyncSampleFnHandler {

    public EndHandle(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        asyncResult.doResult(params);
        return;
    }
}
