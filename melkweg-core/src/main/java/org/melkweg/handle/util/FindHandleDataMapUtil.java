package org.melkweg.handle.util;

import org.melkweg.annotation.melkwegHander;
import org.melkweg.exception.BaseRuntimeError;
import org.melkweg.exception.HandleRepeatRuntimeError;
import org.melkweg.handle.*;
import org.melkweg.util.ClassUtill;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Scan all implementation classes of the handler interface and generate map mappings
 */
public class FindHandleDataMapUtil {

    private static final Logger logger = Logger.getLogger(FindHandleDataMapUtil.class.getName());

    public static Map<String, Handler> findHandleDataMap(String...pkgNames){
        Map<String,Handler> map = new HashMap<>();
        for (String pkgName : pkgNames){
            try {
                addNewHandler(pkgName,map);
            } catch (BaseRuntimeError e) {
                throw e;
            } catch (Exception e) {
                logger.warning("当前包检索handle 异常 : pkg name : " +pkgName);
            }
        }
        return map;
    }

    private static void addNewHandler(String pkgName, Map<String, Handler> map) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<Class<?>> pkgClassSet = ClassUtill.getClassSet(pkgName,Handler.class);
        for(Class<?> itemClass : pkgClassSet){

            if(!Handler.class.isAssignableFrom(itemClass)){
                continue;
            }
            Class<Handler> handlerClass = (Class<Handler>) itemClass;
            melkwegHander hander = handlerClass.getAnnotation(melkwegHander.class);
            if(hander==null){
                continue;
            }
            String name = hander.name().equals("")?handlerClass.getName():hander.name();

            HandleType handleType;
            if (SampleHandler.class.isAssignableFrom(handlerClass)){
                handleType = HandleType.SAMPLE_HANDLE;
            }else if(ReorderHandler.class.isAssignableFrom(handlerClass)){
                handleType = HandleType.REORDER_HANDLE;
            }else if(ConditionHandlerWrapper.ConditionHander.class.isAssignableFrom(handlerClass)){
                handleType = HandleType.CONDITION_HANDLE;
            }else{
                handleType = hander.type();
            }
            if(!map.containsKey(name)){
                map.put(name,handlerClass.getConstructor(String.class,HandleType.class).newInstance(name,handleType));
            }else{
                throw new HandleRepeatRuntimeError("当前handle名称存在冲突 : name ->"+name +"| class ->"+handlerClass.getName());
            }
        }
    }
}
