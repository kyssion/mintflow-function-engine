package org.melkweg.handler.async.condition;

import org.melkweg.annotation.MelkwegHandler;
import org.melkweg.handle.async.AsyncConditionFncHandlerWrapper;
import org.melkweg.param.ParamWrapper;
import org.melkweg.test.syncBaseTest.ConditionTest;

@MelkwegHandler(name = "async_condition_handle_1")
public class AsyncConditionHandler1 extends AsyncConditionFncHandlerWrapper.ConditionHandler {

    public AsyncConditionHandler1(String name) {
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
