package org.melkweg.handler.sync.condition;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.test.syncBaseTest.ConditionTest;
import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "condition_handle_4")
public class ConditionHandler4 extends SyncConditionFncHandlerWrapper.ConditionHandler {

    public ConditionHandler4(String name) {
        super(name);
    }

    @Override
    public boolean condition(ParamWrapper params) {
        String testOne = params.getContextParam("condition_4");
        if(ConditionTest.CAN_GO.equals(testOne)){
            return true;
        }else{
            return false;
        }
    }
}
