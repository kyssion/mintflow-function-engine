package org.melkweg.handler.async.cycle;

import org.melkweg.annotation.MelkwegHandler;
import org.melkweg.handle.async.AsyncCycleFnHandler;
import org.melkweg.param.CycleParam;
import org.melkweg.param.ParamWrapper;

@MelkwegHandler(name = "async_cycle_test")
public class AsyncCycleTestHandler extends AsyncCycleFnHandler {

    public AsyncCycleTestHandler(String name) {
        super(name);
    }

    @Override
    public CycleParam cycleHandler(ParamWrapper paramWrapper) {
        int times = 2000;
        Integer nowTimes = (Integer) paramWrapper.getContextParams().getOrDefault("random_number",0);
        paramWrapper.setContextParam("random_number",nowTimes+times);
        return new CycleParam(times,paramWrapper);
    }
}
