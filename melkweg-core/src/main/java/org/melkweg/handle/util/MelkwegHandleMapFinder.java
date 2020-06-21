package org.melkweg.handle.util;

import org.melkweg.annotation.MelkwegHandler;
import org.melkweg.exception.BaseRuntimeException;
import org.melkweg.exception.HandleRepeatRuntimeException;
import org.melkweg.handle.*;
import org.melkweg.handle.async.AsyncFnHandler;
import org.melkweg.handle.sync.SyncFnHandler;
import org.melkweg.util.ClassUtill;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Scan all implementation classes of the handler interface and generate map mappings
 */
public class MelkwegHandleMapFinder {

    private static final Logger logger = Logger.getLogger(MelkwegHandleMapFinder.class.getName());

    public static MelkwegHandleMapBuilder.Mapper findHandleDataMap(String...pkgNames){
        MelkwegHandleMapBuilder.Mapper mapper = new MelkwegHandleMapBuilder.Mapper();
        for (String pkgName : pkgNames){
            try {
                addNewHandler(pkgName,mapper);
            } catch (BaseRuntimeException e) {
                throw e;
            } catch (Exception e) {
                logger.warning("当前包检索handle 异常 : pkg name : " +pkgName);
            }
        }
        return mapper;
    }

    @SuppressWarnings("unchecked")
    private static void addNewHandler(String pkgName, MelkwegHandleMapBuilder.Mapper mapper) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<Class<?>> pkgClassSet = ClassUtill.getClassSet(pkgName, FnHandler.class);
        for(Class<?> itemClass : pkgClassSet){

            if(!FnHandler.class.isAssignableFrom(itemClass)){
                continue;
            }
            Class<FnHandler> handlerClass = (Class<FnHandler>) itemClass;
            MelkwegHandler melkwegHandler = handlerClass.getAnnotation(MelkwegHandler.class);
            if(melkwegHandler ==null){
                continue;
            }
            String name = melkwegHandler.name().equals("")?handlerClass.getName(): melkwegHandler.name();
            FnHandler fnHandler = handlerClass.getConstructor(String.class).newInstance(name);

            if(fnHandler instanceof AsyncFnHandler ){
                Map<String,AsyncFnHandler> asyncFnHandleMap  = mapper.getAsyncFnHandleMap();
                if(!asyncFnHandleMap.containsKey(name)){
                    if(melkwegHandler.type()!=HandleType.UNDERFIND_HANDLE_SYNC){
                        fnHandler.setType(melkwegHandler.type());
                    }
                    asyncFnHandleMap.put(name, (AsyncFnHandler) fnHandler);
                }else{
                    throw new HandleRepeatRuntimeException("当前handle名称存在冲突 : name ->"+name +"| class ->"+handlerClass.getName());
                }
            }else if(fnHandler instanceof SyncFnHandler){
                Map<String,SyncFnHandler> syncFnHandleMap = mapper.getSyncFnHandleMap();
                if(!syncFnHandleMap.containsKey(name)){
                    if(melkwegHandler.type()!=HandleType.UNDERFIND_HANDLE_SYNC){
                        fnHandler.setType(melkwegHandler.type());
                    }
                    syncFnHandleMap.put(name, (SyncFnHandler) fnHandler);
                }else{
                    throw new HandleRepeatRuntimeException("当前handle名称存在冲突 : name ->"+name +"| class ->"+handlerClass.getName());
                }
            }else{
                throw new HandleRepeatRuntimeException("当前handle为不支持类型 : class name :"+fnHandler.getClass().getName());
            }

        }
    }
}
