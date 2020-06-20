package org.melkweg.async.scheduler;


import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.handle.FnHandler;

import java.util.List;

public interface AsyncScheduler {
    void asyncRun(AsyncParamWrapper asyncParamWrapper, List<FnHandler> fnHandlerList, AsyncResult asyncResult);
}
