package org.mintflow.reflection.wrapper;

import org.mintflow.reflection.agent.Agent;
import org.mintflow.reflection.exception.ExceptionUtil;
import org.mintflow.reflection.exception.ReflectionException;
import org.mintflow.reflection.MirrorClass;
import org.mintflow.reflection.MirrorObject;
import org.mintflow.reflection.mirror.SystemMirrorObject;
import org.mintflow.reflection.object.ObjectFactory;
import org.mintflow.reflection.property.PropertyTokenizer;

import java.util.List;

/**
 * 本质上是针对metaClass的一层封装,没有解决层级获取的问题
 */
public class BeanWrapper extends BaseWrapper {

    private final Object object;

    public BeanWrapper(MirrorObject mirrorObject, Object object) {
        super(mirrorObject);
        this.object = object;
    }

    @Override
    public Class<?> getType() {
        return this.mirrorObject.getMirrorClass().getType();
    }

    @Override
    public Object get(PropertyTokenizer prop) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, object);
            return getCollectionValue(prop, collection);
        } else {
            return getBeanProperty(prop, object);
        }
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, object);
            setCollectionValue(prop, collection, value);
        } else {
            setBeanProperty(prop, object, value);
        }
    }

    @Override
    public String[] getGetterNames() {
        return this.mirrorObject.getMirrorClass().getGetterNames();
    }

    @Override
    public String[] getSetterNames() {
        return this.mirrorObject.getMirrorClass().getSetterNames();
    }

    @Override
    public Class<?> getSetterType(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MirrorObject metaValue = mirrorObject.mirrorObjectForProperty(prop.getIndexedName());
            if (metaValue == SystemMirrorObject.NULL_META_OBJECT) {
                return this.mirrorObject.getMirrorClass().getSetterType(name);
            } else {
                return metaValue.getFiledMirrorObject().getSetterType(prop.getChildren());
            }
        } else {
            return this.mirrorObject.getMirrorClass().getSetterType(name);
        }
    }

    @Override
    public Class<?> getGetterType(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MirrorObject metaValue = mirrorObject.mirrorObjectForProperty(prop.getIndexedName());
            if (metaValue == SystemMirrorObject.NULL_META_OBJECT) {
                return this.mirrorObject.getMirrorClass().getGetterType(name);
            } else {
                return metaValue.getFiledMirrorObject().getGetterType(prop.getChildren());
            }
        } else {
            return this.mirrorObject.getMirrorClass().getGetterType(name);
        }
    }

    @Override
    public boolean hasSetter(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            if (this.mirrorObject.getMirrorClass().hasSetter(prop.getIndexedName())) {
                MirrorObject metaValue = mirrorObject.mirrorObjectForProperty(prop.getIndexedName());
                if (metaValue == SystemMirrorObject.NULL_META_OBJECT) {
                    return this.mirrorObject.getMirrorClass().hasSetter(name);
                } else {
                    return metaValue.getFiledMirrorObject().hasSetter(prop.getChildren());
                }
            } else {
                return false;
            }
        } else {
            return this.mirrorObject.getMirrorClass().hasSetter(name);
        }
    }

    @Override
    public boolean hasGetter(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            if (this.mirrorObject.getMirrorClass().hasGetter(prop.getIndexedName())) {
                MirrorObject metaValue = mirrorObject.mirrorObjectForProperty(prop.getIndexedName());
                if (metaValue == SystemMirrorObject.NULL_META_OBJECT) {
                    return this.mirrorObject.getMirrorClass().hasGetter(name);
                } else {
                    return metaValue.getFiledMirrorObject().hasGetter(prop.getChildren());
                }
            } else {
                return false;
            }
        } else {
            return this.mirrorObject.getMirrorClass().hasGetter(name);
        }
    }

    @Override
    public MirrorObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory) {
        MirrorObject metaValue;
        Class<?> type = getSetterType(prop.getName());
        try {
            Object newObject = objectFactory.create(type);
            metaValue = MirrorObject.forObject(newObject);
            //新创建的类在当前位置上挂上引用
            set(prop, newObject);
        } catch (Exception e) {
            throw new ReflectionException("Cannot set value of property '" + name + "' because '" + name + "' is null and cannot be instantiated on instance of " + type.getName() + ". Cause:" + e.toString(), e);
        }
        //返回新创建的类继续更新
        return metaValue;
    }

    @Override
    public Object invoke(String name, Object[] params) {
        return doInvoke(name, params, object);
    }

    public Object doInvoke(String name, Object[] params, Object object) {
        Class<?>[] metaParams = new Class[params.length];
        for (int index = 0; index < params.length; index++) {
            //metaParams[index] = MirrorObject.forObject(params).getType();
            metaParams[index] = params[index].getClass();
        }
        try {
            Agent method = this.mirrorObject.getMirrorClass().getMethod(name, metaParams);
            try {
                return method.invoke(object, params);
            } catch (Throwable t) {
                throw ExceptionUtil.unwrapThrowable(t);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new ReflectionException("Could not get property '" + name + "' from " + object.getClass() + ".  Cause: " + t.toString(), t);
        }
    }

    private Object getBeanProperty(PropertyTokenizer prop, Object object) {
        try {
            Agent method = this.mirrorObject.getMirrorClass().getGetAgent(prop.getName());
            try {
                return method.invoke(object, NO_ARGUMENTS);
            } catch (Throwable t) {
                throw ExceptionUtil.unwrapThrowable(t);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new ReflectionException("Could not get property '" + prop.getName() + "' from " + object.getClass() + ".  Cause: " + t.toString(), t);
        }
    }

    private void setBeanProperty(PropertyTokenizer prop, Object object, Object value) {
        try {
            Agent method = this.mirrorObject.getMirrorClass().getSetAgent(prop.getName());
            Object[] params = {value};
            try {
                method.invoke(object, params);
            } catch (Throwable t) {
                throw ExceptionUtil.unwrapThrowable(t);
            }
        } catch (Throwable t) {
            throw new ReflectionException("Could not set property '" + prop.getName() + "' of '" + object.getClass() + "' with value '" + value + "' Cause: " + t.toString(), t);
        }
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public void add(Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <E> void addAll(List<E> list) {
        throw new UnsupportedOperationException();
    }
}

