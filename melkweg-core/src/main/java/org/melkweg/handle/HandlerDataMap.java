package org.melkweg.handle;

import org.melkweg.handle.async.AsyncFnHandler;
import org.melkweg.handle.sync.SyncFnHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerDataMap {
    private final Map<String,ProcessDataMap> handlerNamespaceMap= new HashMap<>();
    public static class ProcessDataMap{
        private Map<String, List<SyncFnHandler>> syncFnDataMap = new HashMap<>();
        private Map<String,List<AsyncFnHandler>> asyncFnDataMap = new HashMap<>();
        public void addSync(String key,List<SyncFnHandler> syncFnHandlers){
            this.syncFnDataMap.put(key,syncFnHandlers);
        }
        public void addAsync(String key,List<AsyncFnHandler> asyncFnHandlers){
            this.asyncFnDataMap.put(key,asyncFnHandlers);
        }

        public Map<String, List<SyncFnHandler>> getSyncFnDataMap() {
            return syncFnDataMap;
        }

        public Map<String, List<AsyncFnHandler>> getAsyncFnDataMap() {
            return asyncFnDataMap;
        }

        public void addAll(ProcessDataMap processDataMap){
            this.syncFnDataMap.putAll(processDataMap.getSyncFnDataMap());
            this.asyncFnDataMap.putAll(processDataMap.getAsyncFnDataMap());
        }
    }
    public void add(String namespace,ProcessDataMap processDataMap){
        handlerNamespaceMap.put(namespace,processDataMap);
    }

    public void addAll(HandlerDataMap handlerDataMap){
        this.handlerNamespaceMap.putAll(handlerDataMap.getHandlerNamespaceMap());
    }

    public Map<String, ProcessDataMap> getHandlerNamespaceMap() {
        return handlerNamespaceMap;
    }
}
