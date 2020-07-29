package org.mintflow.reflection.wrapper;

import org.mintflow.reflection.MirrorClass;
import org.mintflow.reflection.MirrorObject;
import org.mintflow.reflection.object.ObjectFactory;
import org.mintflow.reflection.property.PropertyTokenizer;

import java.util.List;

public interface ObjectWrapper {

    Class<?> getType();

    Object get(PropertyTokenizer prop);

    void set(PropertyTokenizer prop, Object value);

    String[] getGetterNames();

    String[] getSetterNames();

    Class<?> getSetterType(String name);

    Class<?> getGetterType(String name);

    boolean hasSetter(String name);

    boolean hasGetter(String name);

    /**
     * 初始化 name 对应的 mirrorObject 对象
     * @param name
     * @param prop
     * @param objectFactory
     * @return
     */
    MirrorObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory);

    boolean isCollection();

    void add(Object element);

    <E> void addAll(List<E> element);

    Object invoke(String name, Object[] params);
}
