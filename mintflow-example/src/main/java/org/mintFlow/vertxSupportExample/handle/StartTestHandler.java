package org.mintFlow.vertxSupportExample.handle;

import org.mintFlow.vertxSupportExample.controller.bean.DefaultRequestBean;
import org.mintFlow.vertxSupportExample.controller.bean.DefaultResponseBean;
import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;

import static org.mintFlow.vertxSupportExample.handle.async.AsyncConditionTest.CAN_GO;
import static org.mintFlow.vertxSupportExample.handle.async.AsyncConditionTest.NO_GO;

@MintFlowHandler(name = "async_start_handle")
public class StartTestHandler extends AsyncSampleFnHandler {
    public StartTestHandler(String name) {
        super(name);
    }
    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        String item = "test1";
        ParamWrapper paramWrapper= new ParamWrapper();
        paramWrapper.setParam(1);
        paramWrapper.setParam(item);
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        asyncScheduler.next(paramWrapper,asyncResult);
    }
}
