package org.mintflow.reflection;

import org.mintflow.reflection.mirror.FiledMirrorObject;
import org.mintflow.reflection.mirror.MethodMirrorObject;
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

    private final MirrorClass mirrorClass;
    private final FiledMirrorObject filedMirrorObject;
    private final MethodMirrorObject methodMirrorObject;

    private final Object originalObject;
    private static final ObjectFactory objectFactory = new DefaultObjectFactory();
    private static final ReflectorFactory reflectorFactory = DefaultReflectorFactory.getReflectorFactory();

    @SuppressWarnings("unchecked")
    private MirrorObject(Object object) {
        this.originalObject = object;
        ObjectWrapper objectWrapper = null;
        if (object instanceof ObjectWrapper) {
            objectWrapper = (ObjectWrapper) object;
        } else if (object instanceof Map) {
            objectWrapper = new MapWrapper(this, (Map<String, Object>) object);
        } else if (object instanceof Collection) {
            objectWrapper= new CollectionWrapper(this, (Collection<Object>) object);
        } else {
            objectWrapper = new BeanWrapper(this, object);
        }
        this.filedMirrorObject = new FiledMirrorObject(objectWrapper);
        this.methodMirrorObject = new MethodMirrorObject(objectWrapper);
        this.mirrorClass = MirrorClass.forClass(this.originalObject.getClass(),reflectorFactory);
    }

    public Class<?> getType() {
        return this.mirrorClass.getType();
    }

    public static MirrorObject forObject(Object object) {
        if (object == null) {
            return SystemMirrorObject.NULL_META_OBJECT;
        } else {
            return new MirrorObject(object);
        }
    }

    public Object getOriginalObject() {
        return originalObject;
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
            return this.filedMirrorObject.get(prop);
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
                    metaValue = this.filedMirrorObject.instantiatePropertyValue(name, prop, objectFactory);
                }
            }
            metaValue.setValue(prop.getChildren(), value);
        } else {
            this.filedMirrorObject.set(prop, value);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T invoke(String name, Class<T> returnType, Object... params) {
        if (returnType == null) {
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
            return methodMirrorObject.invoke(name, params);
        }
    }

    public MirrorObject mirrorObjectForProperty(String name) {
        Object value = getValue(name);
        return MirrorObject.forObject(value);
    }

    public MirrorClass getMirrorClass() {
        return mirrorClass;
    }

    public FiledMirrorObject getFiledMirrorObject() {
        return filedMirrorObject;
    }

    public MethodMirrorObject getMethodMirrorObject() {
        return methodMirrorObject;
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotation){
        return this.getMirrorClass().getAnnotation(annotation);
    }
}
