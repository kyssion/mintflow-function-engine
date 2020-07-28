package org.mintflow.reflection.agent;

import org.mintflow.reflection.Reflector;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class MethodAgent implements Agent {

    private final Class<?> type;
    private final Class<?> returnType;
    private final Class<?>[] paramType;
    private final Method method;
    private final Annotation[] annotations;
    private final Map<Class<?>, Annotation> annotationMap = new HashMap<>();

    public MethodAgent(Method method) {
        this.method = method;
        paramType = method.getParameterTypes();
        returnType = method.getReturnType();
        this.annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            this.annotationMap.put(annotation.annotationType(), annotation);
        }
        if (paramType.length == 1) {
            type = paramType[0];
        } else {
            type = returnType;
        }
    }

    /**
     * 针对java9+ 对反射的控制,使用canControlMemberAccessible进行反射能力的检查和校验
     *
     * @param target
     * @param args
     * @return
     * @throws IllegalAccessException
     */
    @Override
    public Object invoke(Object target, Object... args) throws IllegalAccessException, InvocationTargetException {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException e) {
            if (Reflector.canControlMemberAccessible()) {
                method.setAccessible(true);
                return method.invoke(target, args);
            } else {
                throw e;
            }
        }
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public Class<?>[] getParamType() {
        return this.paramType;
    }

    @Override
    public Class<?> getReturnType() {
        return this.returnType;
    }

    @Override
    public Annotation[] getAllAnnotation() {
        return this.annotations;
    }

    @Override
    public <T extends Annotation> Annotation getAnnotation(Class<T> type) {
        return this.method.getAnnotation(type);
    }

    public boolean hasAnnotation(Class<?> typeClass) {
        return this.annotationMap.containsKey(typeClass);
    }

    public Method getMethod() {
        return this.method;
    }

    public String getMethodName() {
        return this.method.getName();
    }

    public int getMethodParamCount() {
        return this.method.getParameterCount();
    }

    public Parameter[] getParams() {
        return this.method.getParameters();
    }
}