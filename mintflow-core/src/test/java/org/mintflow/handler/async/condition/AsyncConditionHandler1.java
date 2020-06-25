package org.mintflow.handler.async.condition;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.async.AsyncConditionFncHandlerWrapper;
import org.mintflow.param.ParamWrapper;
import org.mintflow.test.syncBaseTest.ConditionTest;

@MintFlowHandler(name = "async_condition_handle_1")
public class AsyncConditionHandler1 extends AsyncConditionFncHandlerWrapper.ConditionHandler {

    public AsyncConditionHandler1(String name) {
        super(name);
    }

    @Override
    public boolean condition(ParamWrapper params) {
        String testOne = params.getContextParam("condition_1");
        if(ConditionTest.CAN_GO.equals(testOne)){
            return true;
        }else{
            return false;
        }
    }
}
