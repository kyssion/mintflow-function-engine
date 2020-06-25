package org.mintflow.scheduler.sync;

import org.mintflow.handler.sync.SyncFnHandler;
import org.mintflow.param.ParamWrapper;

import java.util.List;

public interface SyncScheduler {
    ParamWrapper run(ParamWrapper paramWrapper, List<SyncFnHandler> fnHandlerList);
}
