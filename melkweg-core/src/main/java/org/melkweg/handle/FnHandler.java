package org.melkweg.handle;

import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.param.ParamWrapper;

/**
 * process Node -> used to dealing with a small part of the logic
 */
public abstract class FnHandler implements Cloneable{

    private String name;
    private HandleType type;

    protected FnHandler(String name) {
        this(name,HandleType.SAMPLE_HANDLE_SYNC);
    }

    protected FnHandler(String name, HandleType handleType){
        this.name = name;
        this.type= handleType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HandleType getType() {
        return type;
    }

    public void setType(HandleType type) {
        this.type = type;
    }

    public abstract ParamWrapper handle(ParamWrapper params);

    public abstract void asyncHandle(AsyncParamWrapper params, AsyncResult asyncResult);
}
