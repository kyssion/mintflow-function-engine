package org.melkweg.scheduler;

import org.melkweg.handle.Handler;
import org.melkweg.param.ParamWrapper;

import java.util.List;

public interface Scheduler {
    ParamWrapper run(ParamWrapper paramWrapper, List<Handler> handlerList);
}
