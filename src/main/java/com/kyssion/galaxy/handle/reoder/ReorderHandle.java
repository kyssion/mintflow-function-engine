package com.kyssion.galaxy.handle.reoder;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.type.Type;
import com.kyssion.galaxy.param.ParamWrapper;
import com.kyssion.galaxy.scheduler.Scheduler;

import java.util.List;

/**
 * 重排序handle
 */
public abstract class ReorderHandle implements Handle {

    private Scheduler scheduler;
    private List<Handle> handleList;

    public abstract void reorderHandleSteam(List<Handle> handleList, ParamWrapper paramWrapper);

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setHandleList(List<Handle> handleList) {
        this.handleList = handleList;
    }

    private ParamWrapper reorderHandle(ParamWrapper p) {
        if (handleList == null) {
            return p;
        }
        reorderHandleSteam(this.handleList, p);
        return this.scheduler.run(p, handleList);
    }

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return reorderHandle(p);
    }

    @Override
    public Type getType() {
        return Type.REODER_HANDLE;
    }
}
