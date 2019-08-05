package org.mekweg.builder;

import org.mekweg.annotation.Handler;
import org.mekweg.handle.Handle;
import org.mekweg.handle.StartHandler;
import org.mirror.reflection.Reflector;
import org.mirror.reflection.io.ClassFindleUtil;
import org.mirror.reflection.io.test.IsA;

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
            Reflector reflector = new Reflector(handle);
            Handler handlerAnno = reflector.getAnnotation(Handler.class);
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
                map.put(reflector.getClassName(), item);
            }
        });
        return map;
    }


    public static Map<String, StartHandler> startHanderMapBuilder(Map<String,Handle> handleMap,
                                                                  String...mapperPath){
        return null;
    }
}
