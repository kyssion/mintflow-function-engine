package org.mintflow.handler.async.condition;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handle.async.AsyncConditionFncHandlerWrapper;
import org.mintflow.param.ParamWrapper;
import org.mintflow.test.syncBaseTest.ConditionTest;

@MintFlowHandler(name = "async_condition_handle_2")
public class AsyncConditionHandler2 extends AsyncConditionFncHandlerWrapper.ConditionHandler {

    public AsyncConditionHandler2(String name) {
        super(name);
    }

    @Override
    public boolean condition(ParamWrapper params) {
        String testOne = params.getContextParam("condition_2");
        if(ConditionTest.CAN_GO.equals(testOne)){
            return true;
        }else{
            return false;
        }
    }
}
