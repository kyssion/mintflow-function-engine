package org.mintflow.handler.async;

import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.handler.HandlerType;

import java.util.ArrayList;
import java.util.List;

public abstract class AsyncToolsFnHandler extends AsyncFnHandler {

    private List<AsyncFnHandler> asyncChildren = new ArrayList<>();

    public List<AsyncFnHandler> getAsyncChildren() {
        return asyncChildren;
    }

    public void addChildren(AsyncFnHandler syncFnHandler){
        this.asyncChildren.add(syncFnHandler);
    }

    public void addChildren(List<AsyncFnHandler> asyncChildren){
        this.asyncChildren.addAll(asyncChildren);
    }

    protected AsyncToolsFnHandler(String name) {
        super(name);
    }

    protected AsyncToolsFnHandler(String name, HandlerType handleType) {
        super(name, handleType);
    }

    public abstract void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler);

    public AsyncToolsFnHandler clone() throws CloneNotSupportedException {
        AsyncToolsFnHandler asyncToolsFnHandler = (AsyncToolsFnHandler) super.clone();
        asyncToolsFnHandler.asyncChildren= new ArrayList<>();
        return asyncToolsFnHandler;
    }
}
