package org.melkweg.handle.async;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.async.scheduler.AsyncScheduler;
import org.melkweg.async.scheduler.FnAsyncEngineScheduler;
import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.HandleType;
import org.melkweg.handle.ToolsFnHandle;
import org.melkweg.param.ParamWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Comparing processors . Used to encapsulate comparable collections
 */
public class AsyncConditionFncHandlerWrapper extends AsyncToolsConditionHandlerWrapper {

    private List<ConditionHandler> conditionHandlers = new ArrayList<>();

    public AsyncConditionFncHandlerWrapper(){
        this(AsyncConditionFncHandlerWrapper.class.getName(), HandleType.CONDITION_HANDLE_WRAPPER_ASYNC);
    }

    private AsyncConditionFncHandlerWrapper(String name, HandleType handleType) {
        super(name, handleType);
    }

    public abstract static class ConditionHandler extends AsyncToolsFnHandle {


        public ConditionHandler(String name){
            this(name,HandleType.CONDITION_HANDLE_ASYNC);
        }

        private ConditionHandler(String name, HandleType handleType) {
            super(name, handleType);
        }

        public abstract boolean condition(ParamWrapper params);

        @Override
        public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
            if(asyncResult==null){
                throw new HandleUseException(HandleUseException.CAN_NOT_NOT_FIND_SCHEDULER);
            }
            if(this.getChilds()==null||this.getChilds().size()==0){
                asyncScheduler.next(params,asyncResult);
                return;
            }
            //need create a new AsyncScheduler for child process
            new FnAsyncEngineScheduler().asyncRun(params, this.getChilds(), paramWrapper -> asyncScheduler.next(paramWrapper,asyncResult));
        }
    }


    @Override
    public void asyncHandle(AsyncParamWrapper paramWrapper, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        if(getToolsFnHandles() ==null||getToolsFnHandles().size()==0){
            asyncScheduler.next(paramWrapper,asyncResult);
        }
        if(asyncResult!=null){
            for (ToolsFnHandle toolsFnHandle : getToolsFnHandles()){
                if(toolsFnHandle.getType()!=HandleType.CONDITION_HANDLE_ASYNC){
                    throw new HandleUseException("当前应该使用async模式的condtion handle ，但是但前为handle为："+toolsFnHandle.getName());
                }
                ConditionHandler conditionHandler = (ConditionHandler) toolsFnHandle;
                if(conditionHandler.condition(paramWrapper)){
                    conditionHandler.asyncHandle(paramWrapper,asyncResult,asyncScheduler);
                    break;
                }
            }
        }
    }
}
