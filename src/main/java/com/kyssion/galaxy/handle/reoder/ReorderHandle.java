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


    public abstract void buildHandleSteam(List<Handle> handleList, ParamWrapper paramWrapper);

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return p;
    }

    @Override
    public Type getType() {
        return Type.REODER_ITEM;
    }
}
