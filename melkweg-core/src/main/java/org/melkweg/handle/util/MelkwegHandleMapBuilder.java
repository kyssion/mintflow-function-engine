package org.melkweg.handle.util;

import org.melkweg.Melkweg;
import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.handle.async.AsyncFnHandle;

import java.util.HashMap;
import java.util.Map;

public class MelkwegHandleMapBuilder {
    private final Map<HandleType, Map<String, FnHandler>> handlemap;

    public MelkwegHandleMapBuilder(){
        this.handlemap= new HashMap<>();
    }

    public void put(FnHandler fnHandler){
        MelkwegHander melkwegHander = fnHandler.getClass().getAnnotation(MelkwegHander.class);
        if(melkwegHander!=null){
            put(melkwegHander.name(),fnHandler);
        }else{
            put(fnHandler.getClass().getName(),fnHandler);
        }
    }
    public void put(String name,FnHandler fnHandler){
        boolean isAsync = fnHandler instanceof AsyncFnHandle;
        Map<String,FnHandler> item = isAsync?handlemap.computeIfAbsent(HandleType.ASYNC_HANDLE,k->new HashMap<>()):
                handlemap.computeIfAbsent(HandleType.SYNC_HANDLE,k->new HashMap<>());
        item.put(name,fnHandler);
    }

    public Map<HandleType, Map<String, FnHandler>> build(){
        return handlemap;
    }
}
