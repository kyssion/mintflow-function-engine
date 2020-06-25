package org.mintflow.handle;

import org.mintflow.handle.async.AsyncFnHandler;
import org.mintflow.handle.sync.SyncFnHandler;

import java.util.HashMap;
import java.util.Map;

public class MintFlowHandlerMapper {
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
