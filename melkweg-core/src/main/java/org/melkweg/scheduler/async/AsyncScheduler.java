package org.melkweg.scheduler.async;


import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.handle.async.AsyncFnHandler;

import java.util.List;

public interface AsyncScheduler extends Cloneable{
    void asyncRun(AsyncParamWrapper asyncParamWrapper, List<AsyncFnHandler> fnHandlerList, AsyncResult asyncResult);
    void next(AsyncParamWrapper paramWrapper,AsyncResult asyncResult);
    AsyncScheduler clone() throws CloneNotSupportedException;
}
