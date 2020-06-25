package org.mintflow.handler.util;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.exception.BaseRuntimeException;
import org.mintflow.exception.HandlerRepeatRuntimeException;
import org.mintflow.handler.FnHandler;
import org.mintflow.handler.HandlerType;
import org.mintflow.handler.MintFlowHandlerMapper;
import org.mintflow.handler.async.AsyncFnHandler;
import org.mintflow.handler.sync.SyncFnHandler;
import org.mintflow.util.ClassUtill;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Scan all implementation classes of the handler interface and generate map mappings
 */
public class MintFlowHandlerMapperFinder {

    private static final Logger logger = Logger.getLogger(MintFlowHandlerMapperFinder.class.getName());

    public static MintFlowHandlerMapper findHandlerDataMap(String...pkgNames){
        MintFlowHandlerMapper mapper = new MintFlowHandlerMapper();
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
    private static void addNewHandler(String pkgName, MintFlowHandlerMapper mapper) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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
            String name = MintFlowHandler.name().equals("")?handlerClass.getSimpleName(): MintFlowHandler.name();
            FnHandler fnHandler = handlerClass.getConstructor(String.class).newInstance(name);

            if(fnHandler instanceof AsyncFnHandler ){
                Map<String,AsyncFnHandler> asyncFnHandlerMap  = mapper.getAsyncFnHandlerMap();
                if(!asyncFnHandlerMap.containsKey(name)){
                    if(MintFlowHandler.type()!= HandlerType.UNDERFIND_HANDLE_SYNC){
                        fnHandler.setType(MintFlowHandler.type());
                    }
                    asyncFnHandlerMap.put(name, (AsyncFnHandler) fnHandler);
                }else{
                    throw new HandlerRepeatRuntimeException("当前handle名称存在冲突 : name ->"+name +"| class ->"+handlerClass.getName());
                }
            }else if(fnHandler instanceof SyncFnHandler){
                Map<String,SyncFnHandler> syncFnHandlerMap = mapper.getSyncFnHandlerMap();
                if(!syncFnHandlerMap.containsKey(name)){
                    if(MintFlowHandler.type()!=HandlerType.UNDERFIND_HANDLE_SYNC){
                        fnHandler.setType(MintFlowHandler.type());
                    }
                    syncFnHandlerMap.put(name, (SyncFnHandler) fnHandler);
                }else{
                    throw new HandlerRepeatRuntimeException("当前handle名称存在冲突 : name ->"+name +"| class ->"+handlerClass.getName());
                }
            }else{
                throw new HandlerRepeatRuntimeException("当前handle为不支持类型 : class name :"+fnHandler.getClass().getName());
            }

        }
    }
}
