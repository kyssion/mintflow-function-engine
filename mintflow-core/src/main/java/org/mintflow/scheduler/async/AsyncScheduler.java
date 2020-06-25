package org.mintflow.scheduler.async;


import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;

public interface AsyncScheduler extends Cloneable{
    void next(ParamWrapper paramWrapper, AsyncResult asyncResult);
    AsyncScheduler clone() throws CloneNotSupportedException;
    void reset();
}
