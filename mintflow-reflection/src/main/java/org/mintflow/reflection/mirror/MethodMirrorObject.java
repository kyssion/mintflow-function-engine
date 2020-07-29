package org.mintflow.reflection.mirror;

import org.mintflow.reflection.wrapper.ObjectWrapper;

public class MethodMirrorObject {

    private final ObjectWrapper objectWrapper;

    public MethodMirrorObject(ObjectWrapper objectWrapper){
        this.objectWrapper = objectWrapper;
    }

    public Object invoke(String name, Object[] params){
        return objectWrapper.invoke(name,params);
    }
}
