package org.melkweg.handler.sync.condition;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.test.syncBaseTest.ConditionTest;
import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "condition_handle_3")
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
