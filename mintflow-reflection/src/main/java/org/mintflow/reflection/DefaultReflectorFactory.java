package org.mintflow.reflection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 提供了一个Reflector的map,可以通过这个方法快速的添加class和Reflector的映射关系关系
 */
public class DefaultReflectorFactory implements ReflectorFactory {
    private boolean classCacheEnabled = true;
    private final ConcurrentMap<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<>();
    private static ReflectorFactory defaultReflectorFactoryFactory;

    private DefaultReflectorFactory() {
        super();
    }

    public static synchronized ReflectorFactory getReflectorFactory(){
        if(defaultReflectorFactoryFactory==null){
            defaultReflectorFactoryFactory = new DefaultReflectorFactory();
        }
        return defaultReflectorFactoryFactory;
    }

    @Override
    public boolean isClassCacheEnabled() {
        return classCacheEnabled;
    }

    @Override
    public void setClassCacheEnabled(boolean classCacheEnabled) {
        this.classCacheEnabled = classCacheEnabled;
    }

    /**
     * 简单的一层缓存
     * @param type
     * @return
     */
    @Override
    public Reflector findForClass(Class<?> type) {
        if (classCacheEnabled) {
            return reflectorMap.computeIfAbsent(type, Reflector::new);
        } else {
            return new Reflector(type);
        }
    }

}
