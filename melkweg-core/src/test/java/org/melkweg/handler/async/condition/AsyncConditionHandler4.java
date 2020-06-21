package org.melkweg.handler.async.condition;

import org.melkweg.annotation.MelkwegHandler;
import org.melkweg.handle.async.AsyncConditionFncHandlerWrapper;
import org.melkweg.param.ParamWrapper;
import org.melkweg.test.syncBaseTest.ConditionTest;

@MelkwegHandler(name = "async_condition_handle_4")
public class AsyncConditionHandler4 extends AsyncConditionFncHandlerWrapper.ConditionHandler {

    public AsyncConditionHandler4(String name) {
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
