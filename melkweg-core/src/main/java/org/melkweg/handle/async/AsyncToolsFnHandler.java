package org.melkweg.handle.async;

import org.melkweg.param.ParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.scheduler.async.AsyncScheduler;
import org.melkweg.handle.HandleType;

import java.util.ArrayList;
import java.util.List;

public abstract class AsyncToolsFnHandler extends AsyncFnHandler {

    private List<AsyncFnHandler> asyncChildren = new ArrayList<>();

    public List<AsyncFnHandler> getAsyncChildren() {
        return asyncChildren;
    }

    public void addChildren(AsyncFnHandler syncFnHandle){
        this.asyncChildren.add(syncFnHandle);
    }

    public void addChildren(List<AsyncFnHandler> asyncChildren){
        this.asyncChildren.addAll(asyncChildren);
    }

    protected AsyncToolsFnHandler(String name) {
        super(name);
    }

    protected AsyncToolsFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    public abstract void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler);

    public AsyncToolsFnHandler clone() throws CloneNotSupportedException {
        AsyncToolsFnHandler asyncToolsFnHandle = (AsyncToolsFnHandler) super.clone();
        asyncToolsFnHandle.asyncChildren= new ArrayList<>();
        return asyncToolsFnHandle;
    }
}
