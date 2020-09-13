package org.mintflow.handler.sync.condition;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.test.syncBaseTest.SyncConditionTest;
import org.mintflow.handler.sync.SyncConditionFncHandlerWrapper;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "condition_handle_1")
public class ConditionHandler1 extends SyncConditionFncHandlerWrapper.ConditionHandler {

    public ConditionHandler1(String name) {
        super(name);
    }

    @Override
    public boolean condition(ParamWrapper params) {
        String testOne = params.getContextParam("condition_1");
        if(SyncConditionTest.CAN_GO.equals(testOne)){
            return true;
        }else{
            return false;
        }
    }
}
