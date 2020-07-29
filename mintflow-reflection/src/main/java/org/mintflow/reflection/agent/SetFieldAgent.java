package org.mintflow.reflection.agent;

import org.mintflow.reflection.Reflector;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SetFieldAgent implements Agent {
    private final Field field;
    private final Agent setMehtodAgent;
    private final Class<?> returnType;
    private final Class<?> type;
    private final Class<?>[] paramType;
    private final Annotation[] annotations;

    public SetFieldAgent(Field field,Agent setMethodAgent) {
        this.field = field;
        this.type = field.getType();
        this.paramType = new Class[]{type};
        this.returnType = type;
        this.annotations = field.getAnnotations();
        this.setMehtodAgent = setMethodAgent;
    }
    /**
     * 针对java9+ 对反射的控制,使用canControlMemberAccessible进行反射能力的检查和校验
     * @param target
     * @param args
     * @return
     * @throws IllegalAccessException
     */
    @Override
    public Object invoke(Object target, Object...args) throws InvocationTargetException, IllegalAccessException {
        return this.setMehtodAgent.invoke(target,args);
    }

    @Override
    public Class<?> getType() {
        return this.type;
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
    public <T extends Annotation> T getAnnotation(Class<T> type) {
        return this.field.getAnnotation(type);
    }

    @Override
    public AccessibleObject getAccessibleObject() {
        return this.field;
    }

    @Override
    public String getName() {
        return this.field.getName();
    }
}

