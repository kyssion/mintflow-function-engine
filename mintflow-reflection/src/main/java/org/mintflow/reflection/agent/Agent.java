package org.mintflow.reflection.agent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public interface Agent {

    Object invoke(Object target, Object...args) throws IllegalAccessException, InvocationTargetException;

    Class<?> getType();

    Class<?>[] getParamType();

    Class<?> getReturnType();

    Annotation[] getAllAnnotation();

}
