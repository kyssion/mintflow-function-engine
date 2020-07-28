package org.mintflow.reflection_bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanReflection {
    private Class<?> itemClass;
    private List<Field> fields= new ArrayList<>();
    private List<Method> methods = new ArrayList<>();
    private List<Annotation> annotations = new ArrayList<>();

    public BeanReflection(Object item){
        itemClass = item.getClass();
    }
}
