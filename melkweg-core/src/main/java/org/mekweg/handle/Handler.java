package org.mekweg.handle;

import org.mekweg.param.ParamWrapper;
import org.mekweg.scheduler.Scheduler;

/**
 * process Node -> used to dealing with a small part of the logic
 */
public abstract class Handler implements Cloneable{
    private String name;
    private HandleType type;
    private Scheduler scheduler;

    public Handler(String name,HandleType handleType){
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
