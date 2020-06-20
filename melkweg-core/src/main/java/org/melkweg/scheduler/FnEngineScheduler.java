package org.melkweg.scheduler;

import org.melkweg.handle.FnHandler;
import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;
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
                case SAMPLE_HANDLE_SYNC:
                    paramWrapper = fnHandler.handle(paramWrapper);
                    break;
                case REORDER_HANDLE_SYNC:
                case CONDITION_HANDLE_SYNC:
                case CONDITION_HANDLE_WRAPPER_SYNC:
                    //强制转化为 同步组建类 handle
                    SyncConditionFncHandlerWrapper item = (SyncConditionFncHandlerWrapper) fnHandler;
                    paramWrapper = item.handle(paramWrapper,this);
                    break;
                default:
                    break;
            }
        }
        return paramWrapper;
    }

}
