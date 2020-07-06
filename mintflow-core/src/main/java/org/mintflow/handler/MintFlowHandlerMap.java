package org.mintflow.handler;

import org.mintflow.handler.async.AsyncFnHandler;
import org.mintflow.handler.sync.SyncFnHandler;

import java.util.HashMap;
import java.util.Map;

public class MintFlowHandlerMap {
    private Map<String, AsyncFnHandler> asyncFnHandlerMap = new HashMap<>();
    private Map<String, SyncFnHandler> syncFnHandlerMap = new HashMap<>();

    public Map<String, AsyncFnHandler> getAsyncFnHandlerMap() {
        return asyncFnHandlerMap;
    }

    public void setAsyncFnHandlerMap(Map<String, AsyncFnHandler> asyncFnHandlerMap) {
        this.asyncFnHandlerMap = asyncFnHandlerMap;
    }

    public Map<String, SyncFnHandler> getSyncFnHandlerMap() {
        return syncFnHandlerMap;
    }

    public void setSyncFnHandlerMap(Map<String, SyncFnHandler> syncFnHandlerMap) {
        this.syncFnHandlerMap = syncFnHandlerMap;
    }
}
