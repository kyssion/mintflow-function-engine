package org.mintflow.handler.sync.condition;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.test.syncBaseTest.SyncConditionTest;
import org.mintflow.handler.sync.SyncConditionFncHandlerWrapper;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "condition_handle_4")
public class ConditionHandler4 extends SyncConditionFncHandlerWrapper.ConditionHandler {

    public ConditionHandler4(String name) {
        super(name);
    }

    @Override
    public boolean condition(ParamWrapper params) {
        String testOne = params.getContextParam("condition_4");
        if(SyncConditionTest.CAN_GO.equals(testOne)){
            return true;
        }else{
            return false;
        }
    }
}
