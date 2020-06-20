package org.melkweg.handle.util;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.exception.HandleUseException;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.handle.async.AsyncFnHandle;
import org.melkweg.handle.async.AsyncToolsFnHandle;
import org.melkweg.handle.sync.SyncFnHandle;
import org.melkweg.handle.sync.SyncToolsFnHandle;

import java.util.HashMap;
import java.util.Map;

public class MelkwegHandleMapBuilder {
    private final Map<HandleType, Map<String, FnHandler>> handlemap;

    public MelkwegHandleMapBuilder(){
        this.handlemap= new HashMap<>();
    }

    public void add(FnHandler fnHandler){
        MelkwegHander melkwegHander = fnHandler.getClass().getAnnotation(MelkwegHander.class);
        if(melkwegHander!=null){
            add(melkwegHander.name(),fnHandler);
        }else{
            add(fnHandler.getClass().getName(),fnHandler);
        }
    }
    public void add(String name,FnHandler fnHandler){
        if(!FnHandlerUtil.checkHandleCanUse(fnHandler)){
            throw new HandleUseException("当前使用的handler必须继承自AsyncToolsFnHandle,AsyncFnHandle,SyncFnHandle,SyncToolsFnHandle其中任意一个");
        }
        boolean isAsync = fnHandler instanceof AsyncFnHandle || fnHandler instanceof AsyncToolsFnHandle;
        Map<String,FnHandler> item = isAsync?handlemap.computeIfAbsent(HandleType.ASYNC_HANDLE,k->new HashMap<>()):
                handlemap.computeIfAbsent(HandleType.SYNC_HANDLE,k->new HashMap<>());
        item.put(name,fnHandler);
    }


    public Map<HandleType, Map<String, FnHandler>> build(){
        return handlemap;
    }
}
