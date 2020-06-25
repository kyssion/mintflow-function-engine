package org.mintflow.handle.util;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handle.FnHandler;
import org.mintflow.handle.MintFlowHandlerMapper;
import org.mintflow.handle.async.AsyncFnHandler;
import org.mintflow.handle.sync.SyncFnHandler;


public class MintFlowHandlerMapperBuilder {

    private final MintFlowHandlerMapper handlemap;

    public MintFlowHandlerMapperBuilder(){
        this.handlemap= new MintFlowHandlerMapper();
    }

    public void put(SyncFnHandler syncFnHandler){
        put(getHandlerName(syncFnHandler),syncFnHandler);
    }

    public void put(String name, SyncFnHandler syncFnHandler){
        this.handlemap.getSyncFnHandlerMap().put(name, syncFnHandler);
    }

    public void put(AsyncFnHandler asyncFnHandler){
        put(getHandlerName(asyncFnHandler),asyncFnHandler);
    }

    public void put(String name, AsyncFnHandler asyncFnHandler){
        this.handlemap.getAsyncFnHandlerMap().put(name, asyncFnHandler);
    }

    private String getHandlerName(FnHandler fnHandler){
        MintFlowHandler MintFlowHandler = fnHandler.getClass().getAnnotation(MintFlowHandler.class);
        if(MintFlowHandler !=null){
            return MintFlowHandler.name();
        }else{
            return fnHandler.getClass().getName();
        }
    }


    public MintFlowHandlerMapper build(){
        return handlemap;
    }
}
