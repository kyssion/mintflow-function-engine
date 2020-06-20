package org.melkweg.scheduler;

import org.melkweg.handle.FnHandler;
import org.melkweg.param.ParamWrapper;

import java.util.List;

public class FnEngineScheduler implements Scheduler {

    @Override
    public ParamWrapper run(ParamWrapper paramWrapper, List<FnHandler> fnHandlerList) {
        if (fnHandlerList == null || fnHandlerList.size() == 0) {
            return paramWrapper;
        }
        for (FnHandler fnHandler : fnHandlerList) {
            switch (fnHandler.getType()){
                case SAMPLE_HANDLE:
                    paramWrapper = fnHandler.handle(paramWrapper);
                    break;
                case REORDER_HANDLE:
                case CONDITION_HANDLE:
                case CONDITION_HANDLE_WRAPPER:
                    fnHandler.setScheduler(this);
                    paramWrapper = fnHandler.handle(paramWrapper);
                    break;
                default:
                    break;
            }
        }
        return paramWrapper;
    }

}
