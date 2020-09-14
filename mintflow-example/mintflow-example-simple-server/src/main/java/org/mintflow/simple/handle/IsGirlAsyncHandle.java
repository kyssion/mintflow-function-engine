package org.mintflow.simple.handle;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.HandlerType;
import org.mintflow.handler.async.AsyncConditionFncHandlerWrapper;
import org.mintflow.param.ParamWrapper;
import org.mintflow.simple.bean.Buy;
import org.mintflow.simple.bean.People;
@MintFlowHandler(name="is_girl_async_handle")
public class IsGirlAsyncHandle extends  AsyncConditionFncHandlerWrapper.ConditionHandler{

    public IsGirlAsyncHandle(String name) {
        super(name);
    }

    @Override
    public boolean condition(ParamWrapper params) {
        Buy buy = params.getParam(Buy.class);
        People people = buy.getPeople();
        return people.getSex().equals("girl");
    }
}
