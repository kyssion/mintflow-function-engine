package org.mintflow.handler.async.exception;

import org.mintflow.async.result.AsyncResult;
import org.mintflow.param.ParamWrapper;

public abstract class AsyncExceptionHandler {

    public static final AsyncExceptionHandler defaultAsyncExceptionHandler =
            new AsyncExceptionHandler(Exception.class) {
                @Override
                public void handler(Exception e,AsyncResult asyncResult, ParamWrapper paramWrapper) {
                    e.printStackTrace();
                    paramWrapper.fail();
                    paramWrapper.setException(e);
                    asyncResult.doResult(paramWrapper);
                }
            };

    private final Class<? extends Exception> exceptionType;
    public AsyncExceptionHandler(Class<? extends Exception> exceptionType){
        this.exceptionType = exceptionType;
    }
    public Class<? extends Exception> getExceptionType() {
        return exceptionType;
    }
    public abstract void handler(Exception e,AsyncResult asyncResult, ParamWrapper paramWrapper);
}
