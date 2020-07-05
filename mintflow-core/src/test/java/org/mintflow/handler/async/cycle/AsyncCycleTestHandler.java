package org.mintflow.handler.async.cycle;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.async.AsyncCycleFnHandler;
import org.mintflow.param.CycleParam;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "async_cycle_test")
public class AsyncCycleTestHandler extends AsyncCycleFnHandler {

    public AsyncCycleTestHandler(String name) {
        super(name);
    }

    @Override
    public CycleParam cycleHandler(ParamWrapper paramWrapper) {
        int times = 10;
        Integer nowTimes = (Integer) paramWrapper.getContextParams().getOrDefault("random_number",0);
        paramWrapper.setContextParam("random_number",nowTimes+times);
        return new CycleParam(times,paramWrapper);
    }
}
