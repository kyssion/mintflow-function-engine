package org.mintflow.reflection.wrapper;


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
//        if (collection instanceof Map) {
//            return ((Map) collection).get(prop.getIndex());
//        } else {
//            int i = Integer.parseInt(prop.getIndex());
//            if (collection instanceof List) {
//                return ((List) collection).get(i);
//            } else if (collection instanceof Object[]) {
//                return ((Object[]) collection)[i];
//            } else if (collection instanceof char[]) {
//                return ((char[]) collection)[i];
//            } else if (collection instanceof boolean[]) {
//                return ((boolean[]) collection)[i];
//            } else if (collection instanceof byte[]) {
//                return ((byte[]) collection)[i];
//            } else if (collection instanceof double[]) {
//                return ((double[]) collection)[i];
//            } else if (collection instanceof float[]) {
//                return ((float[]) collection)[i];
//            } else if (collection instanceof int[]) {
//                return ((int[]) collection)[i];
//            } else if (collection instanceof long[]) {
//                return ((long[]) collection)[i];
//            } else if (collection instanceof short[]) {
//                return ((short[]) collection)[i];
//            } else {
//                throw new ReflectionException("The '" + prop.getName() + "' property of " + collection + " is not a List or Array.");
//            }
//        }
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
//        if (collection instanceof Map) {
//            ((Map) collection).put(prop.getIndex(), value);
//        } else {
//            int i = Integer.parseInt(prop.getIndex());
//            if (collection instanceof List) {
//                ((List) collection).set(i, value);
//            } else if (collection instanceof Object[]) {
//                ((Object[]) collection)[i] = value;
//            } else if (collection instanceof char[]) {
//                ((char[]) collection)[i] = (Character) value;
//            } else if (collection instanceof boolean[]) {
//                ((boolean[]) collection)[i] = (Boolean) value;
//            } else if (collection instanceof byte[]) {
//                ((byte[]) collection)[i] = (Byte) value;
//            } else if (collection instanceof double[]) {
//                ((double[]) collection)[i] = (Double) value;
//            } else if (collection instanceof float[]) {
//                ((float[]) collection)[i] = (Float) value;
//            } else if (collection instanceof int[]) {
//                ((int[]) collection)[i] = (Integer) value;
//            } else if (collection instanceof long[]) {
//                ((long[]) collection)[i] = (Long) value;
//            } else if (collection instanceof short[]) {
//                ((short[]) collection)[i] = (Short) value;
//            } else {
//                throw new ReflectionException("The '" + prop.getName() + "' property of " + collection + " is not a List or Array.");
//            }
//        }
        CollectionTypeParameterProcessor.setValueToCollection(prop.getIndex(),collection,value);
    }
}