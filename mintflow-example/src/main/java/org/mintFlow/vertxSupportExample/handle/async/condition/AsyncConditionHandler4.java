package org.mintFlow.vertxSupportExample.handle.async.condition;

import org.mintFlow.vertxSupportExample.handle.async.AsyncConditionTest;
import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.async.AsyncConditionFncHandlerWrapper;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "async_condition_handle_4")
public class AsyncConditionHandler4 extends AsyncConditionFncHandlerWrapper.ConditionHandler {

    public AsyncConditionHandler4(String name) {
        super(name);
    }

    @Override
    public boolean condition(ParamWrapper params) {
        String testOne = params.getContextParam("condition_4");
        if(AsyncConditionTest.CAN_GO.equals(testOne)){
            return true;
        }else{
            return false;
        }
    }
}
