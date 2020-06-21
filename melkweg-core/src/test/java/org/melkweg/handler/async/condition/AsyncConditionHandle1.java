package org.melkweg.handler.async.condition;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.async.AsyncConditionFncHandlerWrapper;
import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;
import org.melkweg.param.ParamWrapper;
import org.melkweg.test.syncBaseTest.ConditionTest;

@MelkwegHander(name = "async_condition_handle_1")
public class AsyncConditionHandle1 extends AsyncConditionFncHandlerWrapper.ConditionHandler {

    public AsyncConditionHandle1(String name) {
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
