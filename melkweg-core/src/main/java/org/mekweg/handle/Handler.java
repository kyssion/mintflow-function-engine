package org.mekweg.handle;

import org.mekweg.param.ParamWrapper;
import org.mekweg.scheduler.Scheduler;

/**
 * process Node -> used to dealing with a small part of the logic
 */
public abstract class Handler implements Cloneable{
    private HandleType type;
    private Scheduler scheduler;
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
