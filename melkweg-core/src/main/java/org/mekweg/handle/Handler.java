package org.mekweg.handle;

import org.mekweg.param.ParamWrapper;
import org.mekweg.scheduler.Scheduler;

/**
 * process Node -> used to dealing with a small part of the logic
 */
public abstract class Handler implements Cloneable{
    private String name;
    private int code;
    private HandleType type;
    private Scheduler scheduler;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
