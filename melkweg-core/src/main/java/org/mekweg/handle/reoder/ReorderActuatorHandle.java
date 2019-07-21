package org.mekweg.handle.reoder;


import org.mekweg.handle.Handle;
import org.mekweg.handle.type.Type;
import org.mekweg.param.ParamWrapper;
import org.mekweg.scheduler.Scheduler;

import java.util.List;

public class ReorderActuatorHandle implements Handle {

    private ReorderHandle reorderHandle;
    private List<Handle> listHandle;
    private Scheduler scheduler;

    public ReorderHandle getReorderHandle() {
        return reorderHandle;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setReorderHandle(ReorderHandle reorderHandle) {
        this.reorderHandle = reorderHandle;
    }

    public List<Handle> getListHandle() {
        return listHandle;
    }

    public void setListHandle(List<Handle> listHandle) {
        this.listHandle = listHandle;
    }

    private ParamWrapper reorderHandleRun(ParamWrapper p) {
        if (reorderHandle == null || listHandle == null || scheduler == null) {
            return p;
        }
        reorderHandle.buildHandleSteam(this.listHandle, p);
        return this.scheduler.run(p, this.listHandle);
    }

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return reorderHandleRun(p);
    }

    @Override
    public Type getType() {
        return Type.REODER_HANDLE;
    }
}
