package org.mintflow.handler.sync.condition;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.test.syncBaseTest.ConditionTest;
import org.mintflow.handler.sync.SyncConditionFncHandlerWrapper;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "condition_handle_3")
public class ConditionHandler3 extends SyncConditionFncHandlerWrapper.ConditionHandler {

    public ConditionHandler3(String name) {
        super(name);
    }

    @Override
    public boolean condition(ParamWrapper params) {
        String testOne = params.getContextParam("condition_3");
        if(ConditionTest.CAN_GO.equals(testOne)){
            return true;
        }else{
            return false;
        }
    }
}
