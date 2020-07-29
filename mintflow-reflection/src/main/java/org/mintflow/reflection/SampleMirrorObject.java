package org.mintflow.reflection;

import org.mintflow.reflection.agent.Agent;
import org.mintflow.reflection.exception.ReflectionException;

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

    public void setValue(String name,Object value){
        Agent setFiled = reflector.getSetAgent(name);
        if(setFiled!=null){
            try {
                setFiled.invoke(originObjectItem,value);
            } catch (Exception e) {
                throw new ReflectionException("Could not set property '" + name + "' of '" + originObjectItem + "' with value '" + value + "' Cause: " +e.toString(), e);
            }
        }
    }

    public Object getValue(String name){
        Agent getFiled = reflector.getGetAgent(name);
        if(getFiled!=null){
            try {
                getFiled.invoke(originObjectItem);
            } catch (Exception e) {
                throw new ReflectionException("Could not get property '" + name + "' of '" + originObjectItem  + "' Cause: " +e.toString(), e);
            }
        }
        return null;
    }
}
