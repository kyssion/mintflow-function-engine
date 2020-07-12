package org.mintFlow.vertxSupportExample.handle.async.condition;

import org.mintFlow.vertxSupportExample.handle.async.AsyncConditionTest;
import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.async.AsyncConditionFncHandlerWrapper;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "async_condition_handle_3")
public class AsyncConditionHandler3 extends AsyncConditionFncHandlerWrapper.ConditionHandler {

    public AsyncConditionHandler3(String name) {
        super(name);
    }

    @Override
    public boolean condition(ParamWrapper params) {
        String testOne = params.getContextParam("condition_3");
        if(AsyncConditionTest.CAN_GO.equals(testOne)){
            return true;
        }else{
            return false;
        }
    }
}
