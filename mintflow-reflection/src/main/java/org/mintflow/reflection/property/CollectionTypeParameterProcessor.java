package org.mintflow.reflection.property;

import org.mintflow.reflection.exception.ReflectionException;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CollectionTypeParameterProcessor {
    public static int collectionSize(Object collection){
        if (collection instanceof Map) {
            return ((Map) collection).size();
        }else{
            if (collection instanceof List) {
                return ((List) collection).size();
            } else if (collection instanceof Object[]) {
                return ((Object[]) collection).length;
            } else if (collection instanceof char[]) {
                return ((char[]) collection).length;
            } else if (collection instanceof boolean[]) {
                return ((boolean[]) collection).length;
            } else if (collection instanceof byte[]) {
                return ((byte[]) collection).length;
            } else if (collection instanceof double[]) {
                return ((double[]) collection).length;
            } else if (collection instanceof float[]) {
                return ((float[]) collection).length;
            } else if (collection instanceof int[]) {
                return ((int[]) collection).length;
            } else if (collection instanceof long[]) {
                return ((long[]) collection).length;
            } else if (collection instanceof short[]) {
                return  ((short[]) collection).length;
            } else {
                throw new ReflectionException("The collection  is not a List or Array.");
            }
        }
    }

    public static void setValueToArr(int i , Object arr,Object value){
        if (arr instanceof List) {
            ((List) arr).set(i, value);
        } else if (arr instanceof Object[]) {
            ((Object[]) arr)[i] = value;
        } else if (arr instanceof char[]) {
            ((char[]) arr)[i] = (Character) value;
        } else if (arr instanceof boolean[]) {
            ((boolean[]) arr)[i] = (Boolean) value;
        } else if (arr instanceof byte[]) {
            ((byte[]) arr)[i] = (Byte) value;
        } else if (arr instanceof double[]) {
            ((double[]) arr)[i] = (Double) value;
        } else if (arr instanceof float[]) {
            ((float[]) arr)[i] = (Float) value;
        } else if (arr instanceof int[]) {
            ((int[]) arr)[i] = (Integer) value;
        } else if (arr instanceof long[]) {
            ((long[]) arr)[i] = (Long) value;
        } else if (arr instanceof short[]) {
            ((short[]) arr)[i] = (Short) value;
        } else {
            throw new ReflectionException("The '" + i + "' property of " + arr + " is not a List or Array.");
        }
    }

    public static Object getValueFromArr(int i,Object arr){
        if (arr instanceof List) {
            return ((List) arr).get(i);
        } else if (arr instanceof Object[]) {
            return ((Object[]) arr)[i];
        } else if (arr instanceof char[]) {
            return ((char[]) arr)[i];
        } else if (arr instanceof boolean[]) {
            return ((boolean[]) arr)[i];
        } else if (arr instanceof byte[]) {
            return ((byte[]) arr)[i];
        } else if (arr instanceof double[]) {
            return ((double[]) arr)[i];
        } else if (arr instanceof float[]) {
            return ((float[]) arr)[i];
        } else if (arr instanceof int[]) {
            return ((int[]) arr)[i];
        } else if (arr instanceof long[]) {
            return ((long[]) arr)[i];
        } else if (arr instanceof short[]) {
            return ((short[]) arr)[i];
        } else {
            throw new ReflectionException("The '" + i + "' property of " + arr + " is not a List or Array.");
        }
    }

    public static void setValueToCollection(String index,Object collection,Object value){
        if (collection instanceof Map) {
            ((Map) collection).put(index, value);
        } else {
            int i = Integer.parseInt(index);
            setValueToArr(i,collection,value);
        }
    }

    public static Object getValueFromCollection(String index,Object collection){
        if (collection instanceof Map) {
            return ((Map) collection).get(index);
        } else {
            int i = Integer.parseInt(index);
            return getValueFromArr(i,collection);
        }
    }
}
