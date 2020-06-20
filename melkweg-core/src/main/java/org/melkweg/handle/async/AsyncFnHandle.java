package org.melkweg.handle.async;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.param.ParamWrapper;

public class AsyncFnHandle extends FnHandler {
    protected AsyncFnHandle(String name) {
        super(name);
    }

    protected AsyncFnHandle(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        return null;
    }

    @Override
    public void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult) {

    }
}
