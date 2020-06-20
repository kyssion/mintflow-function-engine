package org.melkweg.scheduler;

import org.melkweg.handle.FnHandler;
import org.melkweg.param.ParamWrapper;

import java.util.List;

public interface Scheduler {
    ParamWrapper run(ParamWrapper paramWrapper, List<FnHandler> fnHandlerList);
}
