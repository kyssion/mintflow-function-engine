package org.mekweg.scheduler;

import org.mekweg.handle.Handle;
import org.mekweg.handle.reoder.ReorderActuatorHandle;
import org.mekweg.handle.select.SelectorStartHandle;
import org.mekweg.param.ParamWrapper;

import java.util.List;

public class HandlerScheduler implements Scheduler {
    @Override
    public ParamWrapper run(ParamWrapper paramWrapper, List<Handle> handleList) {
        if (handleList == null) {
            return paramWrapper;
        }
        SelectorStartHandle selectorStartHandles=null;
        ReorderActuatorHandle reorderActuatorHandle = null;
        for (Handle handle : handleList) {
            try {
                switch (handle.getType()) {
                    case REODER_HANDLE:
                        reorderActuatorHandle = (ReorderActuatorHandle) handle;
                        reorderActuatorHandle.setScheduler(this);
                        paramWrapper = reorderActuatorHandle.handle(paramWrapper);
                        break;
                    case Selector_HANDLE:
                        selectorStartHandles = (SelectorStartHandle) handle;
                        selectorStartHandles.setScheduler(this);
                        paramWrapper = selectorStartHandles.handle(paramWrapper);
                        break;
                    case HANDLE:
                        paramWrapper = handle.handle(paramWrapper);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return paramWrapper;
    }
}
