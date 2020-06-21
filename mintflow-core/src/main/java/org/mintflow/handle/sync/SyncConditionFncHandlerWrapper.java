package org.mintflow.handle.sync;

import org.mintflow.exception.HandleUseException;
import org.mintflow.handle.HandleType;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.sync.SyncScheduler;

/**
 * Comparing processors . Used to encapsulate comparable collections
 */
public final class SyncConditionFncHandlerWrapper extends SyncToolsFnHandler {

    public SyncConditionFncHandlerWrapper(){
        this(SyncConditionFncHandlerWrapper.class.getName(), HandleType.CONDITION_HANDLE_WRAPPER_SYNC);
    }

    private SyncConditionFncHandlerWrapper(String name, HandleType handleType) {
        super(name, handleType);
    }

    public abstract static class ConditionHandler extends SyncToolsFnHandler {


        public ConditionHandler(String name) {
            this(name, HandleType.CONDITION_HANDLE_SYNC);
        }

        private ConditionHandler(String name, HandleType handleType) {
            super(name, handleType);
        }


        public abstract boolean condition(ParamWrapper params);

        public ParamWrapper handle(ParamWrapper paramWrapper, SyncScheduler syncScheduler) {
            if (this.getSyncChildren() == null || this.getSyncChildren().size() == 0) {
                return paramWrapper;
            }
            if (syncScheduler != null) {
                return syncScheduler.run(paramWrapper, this.getSyncChildren());
            } else {
                return paramWrapper;
            }
        }
    }
    @Override
    public ParamWrapper handle(ParamWrapper paramWrapper, SyncScheduler syncScheduler) {
        if(this.getSyncChildren() ==null||this.getSyncChildren().size()==0){
            return paramWrapper;
        }
        if(syncScheduler !=null){
            for (SyncFnHandler syncFnHandler : getSyncChildren()){
                if(syncFnHandler.getType()!=HandleType.CONDITION_HANDLE_SYNC){
                    throw new HandleUseException("当前应该使用sync模式的condtion handle ，但是但前为handle为："+ syncFnHandler.getName());
                }
                ConditionHandler conditionHandler = (ConditionHandler) syncFnHandler;
                if(conditionHandler.condition(paramWrapper)){
                    paramWrapper = conditionHandler.handle(paramWrapper, syncScheduler);
                    break;
                }
            }
        }
        return paramWrapper;
    }
}
