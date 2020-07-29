package org.mintflow.reflection.wrapper;


import org.mintflow.reflection.DefaultReflectorFactory;
import org.mintflow.reflection.MirrorClass;
import org.mintflow.reflection.MirrorObject;
import org.mintflow.reflection.property.CollectionTypeParameterProcessor;
import org.mintflow.reflection.property.PropertyTokenizer;


public abstract class BaseWrapper implements ObjectWrapper {

    protected static final Object[] NO_ARGUMENTS = new Object[0];
    protected final MirrorObject mirrorObject;
    protected BaseWrapper(MirrorObject mirrorObject) {
        this.mirrorObject = mirrorObject;
    }

    protected Object resolveCollection(PropertyTokenizer prop, Object object) {
        if ("".equals(prop.getName())) {
            return object;
        } else {
            return mirrorObject.getValue(prop.getName());
        }
    }

    /**
     * 不用考虑object的类型,获取prop 对象指定的变量对应的内容
     * @param prop
     * @param collection
     * @return
     */
    protected Object getCollectionValue(PropertyTokenizer prop, Object collection) {
        return CollectionTypeParameterProcessor.getValueFromCollection(prop.getIndex(),collection);
    }

    /**
     * 不用考虑collection的类型,为这个参数的prop指定的参数名称的变量初始化变量
     * @param prop
     * @param collection
     * @param value
     */
    @SuppressWarnings("unchecked")
    protected void setCollectionValue(PropertyTokenizer prop, Object collection, Object value) {
        CollectionTypeParameterProcessor.setValueToCollection(prop.getIndex(),collection,value);
    }

}