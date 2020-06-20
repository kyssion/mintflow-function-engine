package org.melkweg.handle.sync;

import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.HandleType;
import org.melkweg.handle.ToolsFnHandle;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * Comparing processors . Used to encapsulate comparable collections
 */
public final class SyncConditionFncHandlerWrapper extends SyncToolsConditionHanderWrapper {

    public SyncConditionFncHandlerWrapper(){
        this(SyncConditionFncHandlerWrapper.class.getName(), HandleType.CONDITION_HANDLE_WRAPPER_SYNC);
    }

    private SyncConditionFncHandlerWrapper(String name, HandleType handleType) {
        super(name, handleType);
    }

    public abstract static class ConditionHandler extends SyncToolsFnHandle {


        public ConditionHandler(String name) {
            this(name, HandleType.CONDITION_HANDLE_SYNC);
        }

        private ConditionHandler(String name, HandleType handleType) {
            super(name, handleType);
        }


        public abstract boolean condition(ParamWrapper params);

        public ParamWrapper handle(ParamWrapper paramWrapper, Scheduler scheduler) {
            if (this.getChilds() == null || this.getChilds().size() == 0) {
                return paramWrapper;
            }
            if (scheduler != null) {
                return scheduler.run(paramWrapper, this.getChilds());
            } else {
                return paramWrapper;
            }
        }
    }
    @Override
    public ParamWrapper handle(ParamWrapper paramWrapper, Scheduler scheduler) {
        if(this.getToolsFnHandles() ==null||this.getToolsFnHandles().size()==0){
            return paramWrapper;
        }
        if(scheduler!=null){
            for (ToolsFnHandle toolsFnHandle : getToolsFnHandles()){
                if(toolsFnHandle.getType()!=HandleType.CONDITION_HANDLE_SYNC){
                    throw new HandleUseException("当前应该使用sync模式的condtion handle ，但是但前为handle为："+toolsFnHandle.getName());
                }
                ConditionHandler conditionHandler = (ConditionHandler) toolsFnHandle;
                if(conditionHandler.condition(paramWrapper)){
                    paramWrapper = conditionHandler.handle(paramWrapper,scheduler);
                    break;
                }
            }
        }
        return paramWrapper;
    }
}
