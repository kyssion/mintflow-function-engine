package org.melkweg.handler.async.condition;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.async.AsyncConditionFncHandlerWrapper;
import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;
import org.melkweg.param.ParamWrapper;
import org.melkweg.test.syncBaseTest.ConditionTest;

@MelkwegHander(name = "async_condition_handle_2")
public class AsyncConditionHandler2 extends AsyncConditionFncHandlerWrapper.ConditionHandler {

    public AsyncConditionHandler2(String name) {
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
