package org.mintflow.handle;

import org.mintflow.param.ParamWrapper;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.scheduler.async.AsyncScheduler;

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

    public abstract void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler);

    @Override
    public FnHandler clone() throws CloneNotSupportedException {
        return (FnHandler) super.clone();
    }
}
