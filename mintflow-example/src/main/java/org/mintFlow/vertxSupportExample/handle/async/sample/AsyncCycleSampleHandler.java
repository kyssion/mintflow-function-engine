package org.mintFlow.vertxSupportExample.handle.async.sample;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;


@MintFlowHandler(name = "async_cycle_sample_handler")
public class AsyncCycleSampleHandler extends AsyncSampleFnHandler {

    private static final String ADD_DATA = "pppp";

    public AsyncCycleSampleHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        String item = params.getParam(String.class);
        params.setParam(item+ADD_DATA);
        asyncScheduler.next(params,asyncResult);
    }

}
