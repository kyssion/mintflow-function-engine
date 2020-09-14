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

import java.util.List;

/**
 * 优惠计算器
 */
@MintFlowHandler(name="preferential_actuator_async_handle")
public class PreferentialActuatorAsyncHandle extends AsyncSampleFnHandler {

    public PreferentialActuatorAsyncHandle(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        Buy buy = params.getParam(Buy.class);
        List<Goods> goodsList = buy.getGoodsList();
        ResMsg resMsg = params.getParam(ResMsg.class);
        double delete = goodsList.size()*10;
        resMsg.setMoney(resMsg.getMoney()-delete);
        asyncScheduler.next(params,asyncResult);
    }
}
