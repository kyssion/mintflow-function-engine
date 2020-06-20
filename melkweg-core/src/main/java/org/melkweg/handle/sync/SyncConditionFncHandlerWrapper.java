package org.melkweg.handle.sync;

import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Comparing processors . Used to encapsulate comparable collections
 */
public class SyncConditionFncHandlerWrapper extends SyncToolsFnHandle {

    private List<ConditionHander> conditionHanders = new ArrayList<>();

    public SyncConditionFncHandlerWrapper(){
        this(SyncConditionFncHandlerWrapper.class.getName(), HandleType.CONDITION_HANDLE_WRAPPER_SYNC);
    }

    private SyncConditionFncHandlerWrapper(String name, HandleType handleType) {
        super(name, handleType);
    }

    public abstract static class ConditionHander extends SyncToolsFnHandle {

        private List<FnHandler> childs = new ArrayList<>();

        public ConditionHander(String name){
            this(name,HandleType.CONDITION_HANDLE_SYNC);
        }

        private ConditionHander(String name, HandleType handleType) {
            super(name, handleType);
        }

        public void addChilds(FnHandler... fnHandlers) {
            childs.addAll(Arrays.asList(fnHandlers));
        }

        public void addChilds(List<FnHandler> fnHandlers) {
            childs.addAll(fnHandlers);
        }

        public abstract boolean condition(ParamWrapper params);

        public ParamWrapper handle(ParamWrapper paramWrapper, Scheduler scheduler){
            if(this.childs==null||this.childs.size()==0){
                return paramWrapper;
            }
            if(scheduler!=null){
                return scheduler.run(paramWrapper,this.childs);
            }else{
                return paramWrapper;
            }
        }

        @Override
        public ConditionHander clone() throws CloneNotSupportedException {
            ConditionHander conditionHander = (ConditionHander) super.clone();
            conditionHander.childs = new ArrayList<>();
            return conditionHander;
        }
    }

    public void addChilds(ConditionHander... handlers) {
        conditionHanders.addAll(Arrays.asList(handlers));
    }

    public void addChilds(List<ConditionHander> handlers) {
        conditionHanders.addAll(handlers);
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
