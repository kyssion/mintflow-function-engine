package com.kyssion.galaxy.builder;

import com.kyssion.galaxy.annotation.Handler;
import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.header.StartHander;
import org.mirror.reflection.io.ClassFindleUtil;
import org.mirror.reflection.io.test.IsA;
import org.mirror.reflection.mirror.MirrorClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HandleMapBuilder {

    /**
     * build handle map
     *
     * @param handlePath
     * @return
     * @throws Exception
     */
    public static Map<String, Handle> handleMapbuild(String... handlePath) throws Exception {
        Map<String, Handle> map = new HashMap<>();
        ClassFindleUtil<Handle> handleResolverUtil = new ClassFindleUtil<>();
        handleResolverUtil.find(new IsA(Handle.class), handlePath);
        Set<Class<? extends Handle>> handlerSet = handleResolverUtil.getClasses();

        handlerSet.forEach((handle) -> {
            MirrorClass handerClass = MirrorClass.forClass(handle);
            Handler handlerAnno = handerClass.getAnnotation(Handler.class);
            Handle item = null;
            try {
                item = handle.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (item == null) {
                return;
            }
            if (handlerAnno != null) {
                map.put(handlerAnno.value(), item);
            } else {
                map.put(handerClass.getClassName(), item);
            }
        });
        return map;
    }


    public static Map<String, StartHander> startHanderMapBuilder(Map<String,Handle> handleMap,
                                                                 String...mapperPath){

        return null;
    }
}
