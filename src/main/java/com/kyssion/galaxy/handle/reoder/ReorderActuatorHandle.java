package com.kyssion.galaxy.handle.reoder;


import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.type.Type;
import com.kyssion.galaxy.param.ParamWrapper;
import com.kyssion.galaxy.scheduler.Scheduler;

import java.util.List;

public class ReorderActuatorHandle implements Handle {

    private ReorderHandle reorderHandle;
    private List<Handle> listHandle;
    private Scheduler scheduler;

    public ReorderHandle getReorderHandle() {
        return reorderHandle;
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
        return null;
    }

    @Override
    public Type getType() {
        return Type.Selector_HANDLE;
    }
}
