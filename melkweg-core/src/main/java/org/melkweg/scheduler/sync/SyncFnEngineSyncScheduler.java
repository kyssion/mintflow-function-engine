package org.melkweg.scheduler.sync;

import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;
import org.melkweg.handle.sync.SyncCycleFnHandler;
import org.melkweg.handle.sync.SyncFnHandler;
import org.melkweg.handle.sync.SyncReorderFnHandler;
import org.melkweg.param.ParamWrapper;

import java.util.List;

public class SyncFnEngineSyncScheduler implements SyncScheduler {

    @Override
    public ParamWrapper run(ParamWrapper paramWrapper, List<SyncFnHandler> fnHandlerList) {
        if (fnHandlerList == null || fnHandlerList.size() == 0) {
            return paramWrapper;
        }
        for (FnHandler fnHandler : fnHandlerList) {
            switch (fnHandler.getType()){
                case SAMPLE_HANDLE_SYNC:
                    paramWrapper = fnHandler.handle(paramWrapper);
                    break;
                case REORDER_HANDLE_SYNC:
                    //强制转化为 同步组建类 handle
                    SyncReorderFnHandler syncReorderFnHandler = (SyncReorderFnHandler) fnHandler;
                    paramWrapper = syncReorderFnHandler.handle(paramWrapper,this);
                    break;
                case CONDITION_HANDLE_WRAPPER_SYNC:
                    //强制转化为 同步组建类 handle
                    SyncConditionFncHandlerWrapper syncConditionFncHandlerWrapper = (SyncConditionFncHandlerWrapper) fnHandler;
                    paramWrapper = syncConditionFncHandlerWrapper.handle(paramWrapper,this);
                    break;
                case CYCLE_HANDLE_SYNC:
                    //强制转化为 同步组建类 handle
                    SyncCycleFnHandler syncCycleFnHandler = (SyncCycleFnHandler) fnHandler;
                    paramWrapper = syncCycleFnHandler.handle(paramWrapper,this);
                    break;
                default:
                    throw new HandleUseException("出现未知类型，不能在迭代器中运行，name："+fnHandler.getName()+" type:"+fnHandler.getType().getName());
            }
        }
        return paramWrapper;
    }

}
