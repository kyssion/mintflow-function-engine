package org.mintFlow.vertxSupportExample.handle;

import io.vertx.core.json.Json;
import org.mintFlow.vertxSupportExample.controller.bean.DefaultRequestBean;
import org.mintFlow.vertxSupportExample.controller.bean.DefaultResponseBean;
import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;

@MintFlowHandler(name = "async_test_handle")
public class TestHandler extends AsyncSampleFnHandler {
    public TestHandler(String name) {
        super(name);
    }
    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        DefaultRequestBean defaultRequestBean =
                params.getParam(DefaultRequestBean.class);
        System.out.println(Json.encode(defaultRequestBean));
        System.out.println((String)params.getParam(String.class));
        DefaultResponseBean defaultResponseBean = new DefaultResponseBean();
        defaultResponseBean.setMessage("test is test");
        defaultResponseBean.setName("this is name");
        params.setParam(defaultResponseBean);
        asyncScheduler.next(params,asyncResult);
    }
}
