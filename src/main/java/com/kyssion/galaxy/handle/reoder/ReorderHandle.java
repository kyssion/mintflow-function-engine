package com.kyssion.galaxy.handle.reoder;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.type.Type;
import com.kyssion.galaxy.param.ParamWrapper;
import com.kyssion.galaxy.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * 重排序handle
 */
public abstract class ReorderHandle implements Handle {

    private Scheduler scheduler;

    public abstract void buildHandleSteam(List<Handle> handleList, ParamWrapper paramWrapper);


    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return p;
    }

    @Override
    public Type getType() {
        return Type.HANDLE;
    }
}
