package org.mintflow.handle.util;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.exception.BaseRuntimeException;
import org.mintflow.exception.HandleRepeatRuntimeException;
import org.mintflow.handle.*;
import org.mintflow.handle.async.AsyncFnHandler;
import org.mintflow.handle.sync.SyncFnHandler;
import org.mintflow.util.ClassUtill;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Scan all implementation classes of the handler interface and generate map mappings
 */
public class MintFlowHandleMapFinder {

    private static final Logger logger = Logger.getLogger(MintFlowHandleMapFinder.class.getName());

    public static MintFlowHandleMapBuilder.Mapper findHandleDataMap(String...pkgNames){
        MintFlowHandleMapBuilder.Mapper mapper = new MintFlowHandleMapBuilder.Mapper();
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
    private static void addNewHandler(String pkgName, MintFlowHandleMapBuilder.Mapper mapper) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<Class<?>> pkgClassSet = ClassUtill.getClassSet(pkgName, FnHandler.class);
        for(Class<?> itemClass : pkgClassSet){

            if(!FnHandler.class.isAssignableFrom(itemClass)){
                continue;
            }
            Class<FnHandler> handlerClass = (Class<FnHandler>) itemClass;
            MintFlowHandler MintFlowHandler = handlerClass.getAnnotation(MintFlowHandler.class);
            if(MintFlowHandler ==null){
                continue;
            }
            String name = MintFlowHandler.name().equals("")?handlerClass.getName(): MintFlowHandler.name();
            FnHandler fnHandler = handlerClass.getConstructor(String.class).newInstance(name);

            if(fnHandler instanceof AsyncFnHandler ){
                Map<String,AsyncFnHandler> asyncFnHandleMap  = mapper.getAsyncFnHandleMap();
                if(!asyncFnHandleMap.containsKey(name)){
                    if(MintFlowHandler.type()!=HandleType.UNDERFIND_HANDLE_SYNC){
                        fnHandler.setType(MintFlowHandler.type());
                    }
                    asyncFnHandleMap.put(name, (AsyncFnHandler) fnHandler);
                }else{
                    throw new HandleRepeatRuntimeException("当前handle名称存在冲突 : name ->"+name +"| class ->"+handlerClass.getName());
                }
            }else if(fnHandler instanceof SyncFnHandler){
                Map<String,SyncFnHandler> syncFnHandleMap = mapper.getSyncFnHandleMap();
                if(!syncFnHandleMap.containsKey(name)){
                    if(MintFlowHandler.type()!=HandleType.UNDERFIND_HANDLE_SYNC){
                        fnHandler.setType(MintFlowHandler.type());
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
