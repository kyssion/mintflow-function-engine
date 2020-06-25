package org.mintflow.handler.async;

import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.scheduler.async.FnAsyncEngineScheduler;
import org.mintflow.exception.HandlerUseException;
import org.mintflow.handler.HandlerType;

import java.util.ArrayList;
import java.util.List;

/**
 * Comparing processors . Used to encapsulate comparable collections
 */
public class AsyncConditionFncHandlerWrapper extends AsyncToolsFnHandler {

    private List<ConditionHandler> conditionHandlers = new ArrayList<>();

    public AsyncConditionFncHandlerWrapper(){
        this(AsyncConditionFncHandlerWrapper.class.getName(), HandlerType.CONDITION_HANDLE_WRAPPER_ASYNC);
    }

    private AsyncConditionFncHandlerWrapper(String name, HandlerType handleType) {
        super(name, handleType);
    }

    public abstract static class ConditionHandler extends AsyncToolsFnHandler {


        public ConditionHandler(String name){
            this(name,HandlerType.CONDITION_HANDLE_ASYNC);
        }

        private ConditionHandler(String name, HandlerType handleType) {
            super(name, handleType);
        }

        public abstract boolean condition(ParamWrapper params);

        @Override
        public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
            if(asyncResult==null){
                throw new HandlerUseException(HandlerUseException.CAN_NOT_NOT_FIND_SCHEDULER);
            }
            if(this.getAsyncChildren()==null||this.getAsyncChildren().size()==0){
                asyncScheduler.next(params,asyncResult);
                return;
            }
            //need create a new AsyncScheduler for child process
            new FnAsyncEngineScheduler(this.getAsyncChildren()).next(params, paramWrapper -> asyncScheduler.next(paramWrapper,asyncResult));
        }
    }


    @Override
    public void asyncHandle(ParamWrapper paramWrapper, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        if(getAsyncChildren() ==null||getAsyncChildren().size()==0){
            asyncScheduler.next(paramWrapper,asyncResult);
        }
        if(asyncResult!=null){
            for (AsyncFnHandler asyncFnHandler : getAsyncChildren()){
                if(asyncFnHandler.getType()!=HandlerType.CONDITION_HANDLE_ASYNC){
                    throw new HandlerUseException("当前应该使用async模式的condtion handle ，但是但前为handle为："+ asyncFnHandler.getName());
                }
                ConditionHandler conditionHandler = (ConditionHandler) asyncFnHandler;
                if(conditionHandler.condition(paramWrapper)){
                    conditionHandler.asyncHandle(paramWrapper,asyncResult,asyncScheduler);
                    break;
                }
            }
        }
    }
}
