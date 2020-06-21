package org.mintflow.handle.util;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handle.FnHandler;
import org.mintflow.handle.async.AsyncFnHandler;
import org.mintflow.handle.sync.SyncFnHandler;

import java.util.HashMap;
import java.util.Map;

public class MintFlowHandleMapBuilder {

    public static class Mapper{
        private Map<String, AsyncFnHandler> asyncFnHandleMap = new HashMap<>();
        private Map<String, SyncFnHandler> syncFnHandleMap = new HashMap<>();

        public Map<String, AsyncFnHandler> getAsyncFnHandleMap() {
            return asyncFnHandleMap;
        }

        public void setAsyncFnHandleMap(Map<String, AsyncFnHandler> asyncFnHandleMap) {
            this.asyncFnHandleMap = asyncFnHandleMap;
        }

        public Map<String, SyncFnHandler> getSyncFnHandleMap() {
            return syncFnHandleMap;
        }

        public void setSyncFnHandleMap(Map<String, SyncFnHandler> syncFnHandleMap) {
            this.syncFnHandleMap = syncFnHandleMap;
        }
    }

    private final Mapper handlemap;

    public MintFlowHandleMapBuilder(){
        this.handlemap= new Mapper();
    }

    public void put(SyncFnHandler syncFnHandler){
        put(getHandlerName(syncFnHandler),syncFnHandler);
    }

    public void put(String name, SyncFnHandler syncFnHandler){
        this.handlemap.getSyncFnHandleMap().put(name, syncFnHandler);
    }

    public void put(AsyncFnHandler asyncFnHandler){
        put(getHandlerName(asyncFnHandler),asyncFnHandler);
    }

    public void put(String name, AsyncFnHandler asyncFnHandler){
        this.handlemap.getAsyncFnHandleMap().put(name, asyncFnHandler);
    }

    private String getHandlerName(FnHandler fnHandler){
        MintFlowHandler MintFlowHandler = fnHandler.getClass().getAnnotation(MintFlowHandler.class);
        if(MintFlowHandler !=null){
            return MintFlowHandler.name();
        }else{
            return fnHandler.getClass().getName();
        }
    }


    public Mapper build(){
        return handlemap;
    }
}
