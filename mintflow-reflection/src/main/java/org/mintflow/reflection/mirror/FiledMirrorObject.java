package org.mintflow.reflection.mirror;

import org.mintflow.reflection.MirrorObject;
import org.mintflow.reflection.object.ObjectFactory;
import org.mintflow.reflection.property.PropertyTokenizer;
import org.mintflow.reflection.wrapper.ObjectWrapper;

import java.util.List;

public class FiledMirrorObject {

    private final ObjectWrapper objectWrapper;

    public FiledMirrorObject(ObjectWrapper objectWrapper){
        this.objectWrapper = objectWrapper;
    }

    public Object get(PropertyTokenizer prop){
        return objectWrapper.get(prop);
    }

    public void set(PropertyTokenizer prop, Object value){
        objectWrapper.set(prop,value);
    }

    public String[] getGetterNames(){
        return objectWrapper.getGetterNames();
    }
    public String[] getSetterNames(){
        return objectWrapper.getSetterNames();
    }

    public Class<?> getSetterType(String name){
        return objectWrapper.getSetterType(name);
    }

    public Class<?> getGetterType(String name){
        return objectWrapper.getGetterType(name);
    }

    public boolean hasSetter(String name){
        return objectWrapper.hasSetter(name);
    }

    public boolean hasGetter(String name) {
        return objectWrapper.hasSetter(name);
    }

    public boolean isCollection(){
        return objectWrapper.isCollection();
    }

    public void add(Object element){
        objectWrapper.add(element);
    }

    public <E> void addAll(List<E> element){
        objectWrapper.addAll(element);
    }

    public MirrorObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory){
        return this.objectWrapper.instantiatePropertyValue(name,prop,objectFactory);
    }

}
