package com.kyssion.galaxy.scheduler;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.param.ParamWrapper;

import java.util.List;

/**
 *
 */
public interface Scheduler {
    ParamWrapper run(ParamWrapper paramWrapper, List<Handle> handleList);
}
