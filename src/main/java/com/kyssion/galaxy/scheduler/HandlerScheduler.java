package com.kyssion.galaxy.scheduler;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.param.ParamWrapper;

import java.util.List;

public class HandlerScheduler implements Scheduler {
    @Override
    public ParamWrapper run(ParamWrapper paramWrapper, List<Handle> handleList) {
        for (Handle handle:handleList){
            try {
                paramWrapper = handle.handle(paramWrapper);
            }catch (Exception e){
                handle.error(e);
            }
        }
        return paramWrapper;
    }
}
