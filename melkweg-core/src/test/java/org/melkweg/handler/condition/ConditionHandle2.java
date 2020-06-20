package org.melkweg.handler.condition;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.baseTest.ConditionTest;
import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "condition_handle_2")
public class ConditionHandle2 extends SyncConditionFncHandlerWrapper.ConditionHandler {

    public ConditionHandle2(String name) {
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
