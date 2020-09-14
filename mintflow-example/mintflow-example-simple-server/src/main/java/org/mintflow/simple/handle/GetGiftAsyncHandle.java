package org.mintflow.simple.handle;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.HandlerType;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.simple.bean.Buy;
import org.mintflow.simple.bean.Goods;
import org.mintflow.simple.bean.ResMsg;
import org.mintflow.util.CollectionUtils;

import java.util.List;

/**
 * 商品价格计算器
 */
@MintFlowHandler(name="get_gift_async_handle")
public class GetGiftAsyncHandle extends AsyncSampleFnHandler {


    public GetGiftAsyncHandle(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        ResMsg resMsg = params.getParam(ResMsg.class);
        resMsg.setGift("给他一个布娃娃");
        asyncScheduler.next(params,asyncResult);
    }

}
