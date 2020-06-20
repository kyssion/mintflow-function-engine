package org.melkweg.handle;

import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.Scheduler;

/**
 * process Node -> used to dealing with a small part of the logic
 */
public abstract class FnHandler implements Cloneable{

    private String name;
    private HandleType type;
    private Scheduler scheduler;

    protected FnHandler(String name) {
        this(name,HandleType.SAMPLE_HANDLE);
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

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public HandleType getType() {
        return type;
    }

    public void setType(HandleType type) {
        this.type = type;
    }

    public abstract ParamWrapper handle(ParamWrapper params);
}
