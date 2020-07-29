package org.mintflow.reflection;

import java.lang.annotation.Annotation;

public class SampleMirrorObject {
    private final ReflectorFactory reflectorFactory = DefaultReflectorFactory.getReflectorFactory();
    private final Reflector reflector;
    private final Object originObjectItem;
    private SampleMirrorObject(Object item){
        super();
        this.reflector = reflectorFactory.findForClass(item.getClass());
        this.originObjectItem = item;
    }

    public static  SampleMirrorObject forObject(Object item){
        return new SampleMirrorObject(item);
    }

    public Reflector getReflector(){
        return this.reflector;
    }
}
