package org.mintflow.vertx.http.handle;

import io.vertx.core.json.Json;
import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.vertx.http.controller.bean.DefaultRequestBean;
import org.mintflow.vertx.http.controller.bean.DefaultResponseBean;

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
        DefaultResponseBean defaultResponseBean = new DefaultResponseBean();
        params.setParam(defaultResponseBean);
        asyncScheduler.next(params,asyncResult);
    }
}
