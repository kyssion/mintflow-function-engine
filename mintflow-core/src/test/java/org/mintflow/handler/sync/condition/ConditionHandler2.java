package org.mintflow.handler.sync.condition;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.test.syncBaseTest.ConditionTest;
import org.mintflow.handler.sync.SyncConditionFncHandlerWrapper;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "condition_handle_2")
public class ConditionHandler2 extends SyncConditionFncHandlerWrapper.ConditionHandler {

    public ConditionHandler2(String name) {
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
