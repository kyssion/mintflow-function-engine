package org.mekweg.scheduler;

import org.mekweg.handle.Handle;
import org.mekweg.param.ParamWrapper;

import java.util.List;

/**
 *
 */
public interface Scheduler {
    ParamWrapper run(ParamWrapper paramWrapper, List<Handle> handleList);
}
