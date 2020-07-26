package org.mintflow.reflection;

import org.mintflow.reflection.mirror.SystemMirrorObject;
import org.mintflow.reflection.object.DefaultObjectFactory;
import org.mintflow.reflection.object.ObjectFactory;
import org.mintflow.reflection.property.PropertyTokenizer;
import org.mintflow.reflection.wrapper.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 本质上是针对O表决传统Wrapper的一层封装,额外提供了字符串名称解析的能力
 * mirrorObject的设计和ObjectWrapper的设计有鞋别扭, mirrorObject提供的了数据的迭代功能,ObjectWrapper提供了数据获取功能,二者暂时无法拆分
 */
public class MirrorObject {

    private final Object originalObject;
    private final ObjectWrapper objectWrapper;
    private final ObjectFactory objectFactory;
    private final ObjectWrapperFactory objectWrapperFactory;
    private final ReflectorFactory reflectorFactory;

    @SuppressWarnings("unchecked")
    private MirrorObject(Object object, ObjectFactory objectFactory,
                         ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {

        this.originalObject = object;
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;

        if (object instanceof ObjectWrapper) {
            this.objectWrapper = (ObjectWrapper) object;
        } else if (objectWrapperFactory.hasWrapperFor(object)) {
            this.objectWrapper = objectWrapperFactory.getWrapperFor(this, object);
        } else if (object instanceof Map) {
            this.objectWrapper = new MapWrapper(this, (Map<String, Object>) object);
        } else if (object instanceof Collection) {
            this.objectWrapper = new CollectionWrapper(this, (Collection) object);
        } else {
            this.objectWrapper = new BeanWrapper(this, object);
        }
    }

    public Class<?> getType() {
        return this.objectWrapper.getType();
    }

    public static MirrorObject forObject(Object object) {
        return forObject(object, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
    }

    public static MirrorObject forObject(Object object, ObjectFactory objectFactory,
                                         ObjectWrapperFactory objectWrapperFactory,
                                         ReflectorFactory reflectorFactory) {
        if (object == null) {
            return SystemMirrorObject.NULL_META_OBJECT;
        } else {
            return new MirrorObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
        }
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public ObjectWrapperFactory getObjectWrapperFactory() {
        return objectWrapperFactory;
    }

    public ReflectorFactory getReflectorFactory() {
        return reflectorFactory;
    }

    public Object getOriginalObject() {
        return originalObject;
    }

    public String findProperty(String propName, boolean useCamelCaseMapping) {
        return objectWrapper.findProperty(propName, useCamelCaseMapping);
    }

    public String[] getGetterNames() {
        return objectWrapper.getGetterNames();
    }

    public String[] getSetterNames() {
        return objectWrapper.getSetterNames();
    }

    public Class<?> getSetterType(String name) {
        return objectWrapper.getSetterType(name);
    }

    public Class<?> getGetterType(String name) {
        return objectWrapper.getGetterType(name);
    }

    public boolean hasSetter(String name) {
        return objectWrapper.hasSetter(name);
    }

    public boolean hasGetter(String name) {
        return objectWrapper.hasGetter(name);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String name, Class<T> item) {
        return (T) getValue(name);
    }

    public Object getValue(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MirrorObject metaValue = mirrorObjectForProperty(prop.getIndexedName());
            if (metaValue == SystemMirrorObject.NULL_META_OBJECT) {
                return null;
            } else {
                return metaValue.getValue(prop.getChildren());
            }
        } else {
            return objectWrapper.get(prop);
        }
    }

    public void setValue(String name, Object value) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MirrorObject metaValue = mirrorObjectForProperty(prop.getIndexedName());
            if (metaValue == SystemMirrorObject.NULL_META_OBJECT) {
                if (value == null) {
                    // don't instantiate child path if value is null
                    return;
                } else {
                    metaValue = objectWrapper.instantiatePropertyValue(name, prop, objectFactory);
                }
            }
            metaValue.setValue(prop.getChildren(), value);
        } else {
            objectWrapper.set(prop, value);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T invoke(String name, Class<T> item, Object... params) {
        if (item == null) {
            invoke(name, params);
            return null;
        }
        return (T) invoke(name, params);
    }

    public Object invoke(String name, Object... params) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MirrorObject metaValue = mirrorObjectForProperty(prop.getIndexedName());
            if (metaValue == SystemMirrorObject.NULL_META_OBJECT) {
                return null;
            } else {
                return metaValue.invoke(prop.getChildren(), params);
            }
        } else {
            return objectWrapper.invoke(name, params);
        }
    }

    public MirrorObject mirrorObjectForProperty(String name) {
        Object value = getValue(name);
        return MirrorObject.forObject(value, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    public ObjectWrapper getObjectWrapper() {
        return objectWrapper;
    }

    public boolean isCollection() {
        return objectWrapper.isCollection();
    }

    public void add(Object element) {
        objectWrapper.add(element);
    }

    public <E> void addAll(List<E> list) {
        objectWrapper.addAll(list);
    }

}
