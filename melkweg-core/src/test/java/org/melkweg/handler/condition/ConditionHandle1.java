package org.melkweg.handler.condition;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.baseTest.ConditionTest;
import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;
import org.melkweg.param.ParamWrapper;

import java.util.logging.Logger;

@MelkwegHander(name = "condition_handle_1")
public class ConditionHandle1 extends SyncConditionFncHandlerWrapper.ConditionHandler {

    public ConditionHandle1(String name) {
        super(name);
    }

    @Override
    public boolean condition(ParamWrapper params) {
        String testOne = params.getContextParam("condition_1");
        if(ConditionTest.CAN_GO.equals(testOne)){
            return true;
        }else{
            return false;
        }
    }
}
