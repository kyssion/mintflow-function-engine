package org.melkweg.handle;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.exception.HandleUseSyncException;
import org.melkweg.param.ParamWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Comparing processors . Used to encapsulate comparable collections
 */
public class AsyncConditionFncHandlerWrapper extends FnHandler {

    private List<ConditionHander> conditionHanders = new ArrayList<>();

    public AsyncConditionFncHandlerWrapper(){
        this(AsyncConditionFncHandlerWrapper.class.getName(),HandleType.CONDITION_HANDLE_WRAPPER);
    }

    private AsyncConditionFncHandlerWrapper(String name, HandleType handleType) {
        super(name, handleType);
    }

    public abstract static class ConditionHander extends FnHandler {

        private List<FnHandler> childs = new ArrayList<>();

        public ConditionHander(String name){
            this(name,HandleType.CONDITION_HANDLE);
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

        public ParamWrapper handle(ParamWrapper params) {
            if(this.childs==null||this.childs.size()==0){
                return params;
            }
            if(this.getScheduler()!=null){
                return this.getScheduler().run(params,this.childs);
            }else{
                return params;
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
    public ParamWrapper handle(ParamWrapper params) {
        throw new HandleUseSyncException(HandleUseSyncException.NO_USE_ASYNC+",  handle name :" + this.getClass().getName());
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult) {
        if(this.conditionHanders==null||this.conditionHanders.size()==0){
            return params;
        }
        if(this.getScheduler()!=null){
            for (ConditionHander conditionHander: conditionHanders){
                if(conditionHander.condition(params)){
                    conditionHander.setScheduler(this.getScheduler());
                    params = conditionHander.handle(params);
                    break;
                }
            }
        }
        return params;
    }
}
