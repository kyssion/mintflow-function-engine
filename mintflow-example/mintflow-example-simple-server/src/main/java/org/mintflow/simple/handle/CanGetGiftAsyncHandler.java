package org.mintflow.simple.handle;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.HandlerType;
import org.mintflow.handler.async.AsyncConditionFncHandlerWrapper;
import org.mintflow.param.ParamWrapper;
import org.mintflow.simple.bean.ResMsg;

@MintFlowHandler(name="can_get_gift_async_handle")
public class CanGetGiftAsyncHandler extends  AsyncConditionFncHandlerWrapper.ConditionHandler{

    public CanGetGiftAsyncHandler(String name) {
        super(name);
    }

    @Override
    public boolean condition(ParamWrapper params) {
        ResMsg resMsg = params.getParam(ResMsg.class);
        double allMoney = resMsg.getMoney();
        return allMoney>=20.0;
    }
}
