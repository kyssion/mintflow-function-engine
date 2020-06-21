package org.melkweg.scheduler.async;


import org.melkweg.param.ParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.handle.async.AsyncFnHandler;

import java.util.List;

public interface AsyncScheduler extends Cloneable{
    void next(ParamWrapper paramWrapper, AsyncResult asyncResult);
    AsyncScheduler clone() throws CloneNotSupportedException;
    void reset();
}
