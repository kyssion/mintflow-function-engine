package org.melkweg.handle.sync;

import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * Comparing processors . Used to encapsulate comparable collections
 */
public class SyncConditionFncHandlerWrapper extends SyncToolsConditionHanderWrapper {

    private List<ConditionHander> conditionHanders = new ArrayList<>();

    public SyncConditionFncHandlerWrapper(){
        this(SyncConditionFncHandlerWrapper.class.getName(), HandleType.CONDITION_HANDLE_WRAPPER_SYNC);
    }

    private SyncConditionFncHandlerWrapper(String name, HandleType handleType) {
        super(name, handleType);
    }

    public abstract static class ConditionHander extends SyncToolsFnHandle {


        public ConditionHander(String name) {
            this(name, HandleType.CONDITION_HANDLE_SYNC);
        }

        private ConditionHander(String name, HandleType handleType) {
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
        if(this.conditionHanders==null||this.conditionHanders.size()==0){
            return paramWrapper;
        }
        if(scheduler!=null){
            for (ConditionHander conditionHander: conditionHanders){
                if(conditionHander.condition(paramWrapper)){
                    paramWrapper = conditionHander.handle(paramWrapper,scheduler);
                    break;
                }
            }
        }
        return paramWrapper;
    }
}
