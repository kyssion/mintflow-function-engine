package org.melkweg.handle.util;

import org.melkweg.annotation.MelkwegHander;
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
public class MelkwegHandleDataMapFinder {

    private static final Logger logger = Logger.getLogger(MelkwegHandleDataMapFinder.class.getName());

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

    @SuppressWarnings("unchecked")
    private static void addNewHandler(String pkgName, Map<String, Handler> map) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<Class<?>> pkgClassSet = ClassUtill.getClassSet(pkgName,Handler.class);
        for(Class<?> itemClass : pkgClassSet){

            if(!Handler.class.isAssignableFrom(itemClass)){
                continue;
            }
            Class<Handler> handlerClass = (Class<Handler>) itemClass;
            MelkwegHander melkwegHander = handlerClass.getAnnotation(MelkwegHander.class);
            if(melkwegHander==null){
                continue;
            }
            String name = melkwegHander.name().equals("")?handlerClass.getName():melkwegHander.name();
            Handler handler = handlerClass.getConstructor(String.class).newInstance(name);
            if(!map.containsKey(name)){
                if(melkwegHander.type()!=HandleType.UNDERFIND_HANDLE){
                    handler.setType(melkwegHander.type());
                }
                map.put(name, handler);
            }else{
                throw new HandleRepeatRuntimeError("当前handle名称存在冲突 : name ->"+name +"| class ->"+handlerClass.getName());
            }
        }
    }
}
