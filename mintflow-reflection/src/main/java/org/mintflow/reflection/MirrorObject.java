package org.mintflow.reflection;

import org.mintflow.reflection.mirror.SystemMirrorObject;
import org.mintflow.reflection.object.DefaultObjectFactory;
import org.mintflow.reflection.object.ObjectFactory;
import org.mintflow.reflection.property.PropertyTokenizer;
import org.mintflow.reflection.wrapper.*;

import java.lang.annotation.Annotation;
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
    private static final ObjectFactory objectFactory = new DefaultObjectFactory();
    private static final ReflectorFactory reflectorFactory = DefaultReflectorFactory.getReflectorFactory();

    @SuppressWarnings("unchecked")
    private MirrorObject(Object object) {
        this.originalObject = object;
        if (object instanceof ObjectWrapper) {
            this.objectWrapper = (ObjectWrapper) object;
        } else if (object instanceof Map) {
            this.objectWrapper = new MapWrapper(this, (Map<String, Object>) object);
        } else if (object instanceof Collection) {
            this.objectWrapper = new CollectionWrapper(this, (Collection<Object>) object);
        } else {
            this.objectWrapper = new BeanWrapper(this, object);
        }
    }

    public Class<?> getType() {
        return this.objectWrapper.getType();
    }

    public static MirrorObject forObject(Object object) {
        if (object == null) {
            return SystemMirrorObject.NULL_META_OBJECT;
        } else {
            return new MirrorObject(object);
        }
    }


    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public ReflectorFactory getReflectorFactory() {
        return reflectorFactory;
    }

    public Object getOriginalObject() {
        return originalObject;
    }

//    public String findProperty(String propName, boolean useCamelCaseMapping) {
//        return objectWrapper.findProperty(propName, useCamelCaseMapping);
//    }

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
        return MirrorObject.forObject(value);
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
