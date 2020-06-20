package org.melkweg.async.scheduler;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.async.AsyncFnHandle;
import org.melkweg.handle.async.AsyncToolsFnHandle;
import org.melkweg.handle.sync.SyncToolsFnHandle;

import java.util.Iterator;
import java.util.List;

public class FnAsyncEngineScheduler implements AsyncScheduler {

    private Iterator<FnHandler> handlerIterable;

    @Override
    public void asyncRun(AsyncParamWrapper asyncParamWrapper, List<FnHandler> fnHandlerList, AsyncResult asyncResult){
        this.handlerIterable = fnHandlerList.iterator();
    }

    @Override
    public void next(AsyncParamWrapper paramWrapper, AsyncResult asyncResult) {
        if(!handlerIterable.hasNext()){
            asyncResult.doResult(paramWrapper);
            return;
        }
        AsyncFnHandle asyncFnHandle = (AsyncFnHandle) handlerIterable.next();
        asyncFnHandle.asyncHandle(paramWrapper,asyncResult,this);
    }

    @Override
    public AsyncScheduler clone() throws CloneNotSupportedException {
        super.clone();
        return new FnAsyncEngineScheduler();
    }
}
