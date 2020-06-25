package org.mintflow.scheduler.async;

import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.exception.HandlerUseException;
import org.mintflow.handler.FnHandler;
import org.mintflow.handler.async.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FnAsyncEngineScheduler implements AsyncScheduler {

    private Iterator<AsyncFnHandler> handlerIterable;
    private final List<AsyncFnHandler> handlerList;

    public FnAsyncEngineScheduler(List<AsyncFnHandler> handlerList){
        this.handlerIterable = handlerList.iterator();
        this.handlerList = handlerList;
    }

    @Override
    public void next(ParamWrapper paramWrapper, AsyncResult asyncResult) {
        if(!handlerIterable.hasNext()){
            asyncResult.doResult(paramWrapper);
            return;
        }
        FnHandler asyncFnHandler = handlerIterable.next();
        switch (asyncFnHandler.getType()){
            case SAMPLE_HANDLE_ASYNC:
                AsyncSampleFnHandler asyncSampleFnHandler = (AsyncSampleFnHandler) asyncFnHandler;
                asyncSampleFnHandler.asyncHandle(paramWrapper,asyncResult,this);
                break;
            case REORDER_HANDLE_ASYNC:
                //强制转化为 同步组建类 handle
                AsyncReorderFnHandler asyncReorderFnHandler = (AsyncReorderFnHandler) asyncFnHandler;
                asyncReorderFnHandler.asyncHandle(paramWrapper,asyncResult,this);
                break;
            case CONDITION_HANDLE_WRAPPER_ASYNC:
                //强制转化为 同步组建类 handle
                AsyncConditionFncHandlerWrapper asyncConditionFncHandlerWrapper = (AsyncConditionFncHandlerWrapper) asyncFnHandler;
                asyncConditionFncHandlerWrapper.asyncHandle(paramWrapper,asyncResult,this);
                break;
            case CYCLE_HANDLE_ASYNC:
                //强制转化为 同步组建类 handle
                AsyncCycleFnHandler asyncCycleFnHandler = (AsyncCycleFnHandler) asyncFnHandler;
                asyncCycleFnHandler.asyncHandle(paramWrapper,asyncResult,this);
                break;
            default:
                throw new HandlerUseException("出现未知类型，不能在迭代器中运行，name："+asyncFnHandler.getName()+" type:"+asyncFnHandler.getType().getName());
        }
    }

    @Override
    public AsyncScheduler clone() throws CloneNotSupportedException {
        super.clone();
        return new FnAsyncEngineScheduler(new ArrayList<>(this.handlerList));
    }

    @Override
    public void reset() {
        this.handlerIterable = handlerList.iterator();
    }
}
