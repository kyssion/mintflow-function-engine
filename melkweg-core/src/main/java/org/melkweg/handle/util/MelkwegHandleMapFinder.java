package org.melkweg.handle.util;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.exception.BaseRuntimeException;
import org.melkweg.exception.HandleRepeatRuntimeException;
import org.melkweg.handle.*;
import org.melkweg.handle.async.AsyncFnHandle;
import org.melkweg.handle.sync.SyncFnHandle;
import org.melkweg.util.ClassUtill;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Scan all implementation classes of the handler interface and generate map mappings
 */
public class MelkwegHandleMapFinder {

    private static final Logger logger = Logger.getLogger(MelkwegHandleMapFinder.class.getName());

    public static Map<HandleType, Map<String,FnHandler>> findHandleDataMap(String...pkgNames){
        Map<HandleType, Map<String,FnHandler>> map = new HashMap<>();
        for (String pkgName : pkgNames){
            try {
                addNewHandler(pkgName,map);
            } catch (BaseRuntimeException e) {
                throw e;
            } catch (Exception e) {
                logger.warning("当前包检索handle 异常 : pkg name : " +pkgName);
            }
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private static void addNewHandler(String pkgName, Map<HandleType, Map<String,FnHandler>> map) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<Class<?>> pkgClassSet = ClassUtill.getClassSet(pkgName, FnHandler.class);
        for(Class<?> itemClass : pkgClassSet){

            if(!FnHandler.class.isAssignableFrom(itemClass)){
                continue;
            }
            Class<FnHandler> handlerClass = (Class<FnHandler>) itemClass;
            MelkwegHander melkwegHander = handlerClass.getAnnotation(MelkwegHander.class);
            if(melkwegHander==null){
                continue;
            }
            String name = melkwegHander.name().equals("")?handlerClass.getName():melkwegHander.name();
            FnHandler fnHandler = handlerClass.getConstructor(String.class).newInstance(name);

            Map<String,FnHandler> itemHandleMap = null;
            if(fnHandler instanceof AsyncFnHandle){
                itemHandleMap = map.computeIfAbsent(HandleType.ASYNC_HANDLE,(p)->new HashMap<>());
            }else if(fnHandler instanceof SyncFnHandle){
                itemHandleMap = map.computeIfAbsent(HandleType.SYNC_HANDLE,(p)->new HashMap<>());
            }else{
                throw new HandleRepeatRuntimeException("当前hanlle为不支持类型 : class name :"+fnHandler.getClass().getName());
            }
            if(!itemHandleMap.containsKey(name)){
                if(melkwegHander.type()!=HandleType.UNDERFIND_HANDLE_SYNC){
                    fnHandler.setType(melkwegHander.type());
                }
                itemHandleMap.put(name, fnHandler);
            }else{
                throw new HandleRepeatRuntimeException("当前handle名称存在冲突 : name ->"+name +"| class ->"+handlerClass.getName());
            }

        }
    }
}
