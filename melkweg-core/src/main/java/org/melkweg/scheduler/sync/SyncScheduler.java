package org.melkweg.scheduler.sync;

import org.melkweg.handle.sync.SyncFnHandler;
import org.melkweg.param.ParamWrapper;

import java.util.List;

public interface SyncScheduler {
    ParamWrapper run(ParamWrapper paramWrapper, List<SyncFnHandler> fnHandlerList);
}
