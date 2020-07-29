package org.mintflow.reflection.agent;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;

public interface Agent {

    Object invoke(Object target, Object...args) throws InvocationTargetException, IllegalAccessException;

    Class<?> getType();

    Class<?>[] getParamType();

    Class<?> getReturnType();

    Annotation[] getAllAnnotation();

    <T extends Annotation> T getAnnotation(Class<T> type);

    AccessibleObject getAccessibleObject();

    String getName();
}
