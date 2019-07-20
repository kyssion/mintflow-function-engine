package org.galaxy.scheduler;

import org.galaxy.handle.Handle;
import org.galaxy.param.ParamWrapper;

import java.util.List;

/**
 *
 */
public interface Scheduler {
    ParamWrapper run(ParamWrapper paramWrapper, List<Handle> handleList);
}
