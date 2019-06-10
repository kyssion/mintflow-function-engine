package com.kyssion.galaxy.builder;

import com.kyssion.galaxy.annotation.Handler;
import com.kyssion.galaxy.handle.Handle;
import org.mirror.reflection.io.ResolverUtil;
import org.mirror.reflection.mirror.MirrorClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HandleMapBuilder {
    public static Map<String, Handle> build(String handlePath) throws Exception {
        Map<String, Handle> map = new HashMap<>();
        ResolverUtil<Handle> handleResolverUtil = new ResolverUtil<>();
        handleResolverUtil.find(new ResolverUtil.IsA(Handle.class), handlePath);
        Set<Class<? extends Handle>> handlerSet = handleResolverUtil.getClasses();
        for (Class<? extends Handle> handle : handlerSet) {
            MirrorClass handerClass = MirrorClass.forClass(handle);
            Handler handler = handerClass.getAnnotation(Handler.class);
            Handle item = handle.getDeclaredConstructor().newInstance();
            if (handle != null) {
                map.put(handler.value(), item);
            } else {
                map.put(handerClass.getClassName(), item);
            }
        }
        return map;
    }
}
