package org.mintflow.reflection.agent;

import org.mintflow.reflection.Reflector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SetFieldAgent implements Agent {
    private final Field field;
    private final Class<?> returnType;
    private final Class<?> type;
    private final Class<?>[] paramType;
    private final Annotation[] annotations;

    public SetFieldAgent(Field field) {
        this.field = field;
        this.type = field.getType();
        this.paramType = new Class[]{type};
        this.returnType = type;
        this.annotations = field.getAnnotations();
    }
    /**
     * 针对java9+ 对反射的控制,使用canControlMemberAccessible进行反射能力的检查和校验
     * @param target
     * @param args
     * @return
     * @throws IllegalAccessException
     */
    @Override
    public Object invoke(Object target, Object...args) throws IllegalAccessException {
        try {
            field.set(target, args[0]);
        } catch (IllegalAccessException e) {
            if (Reflector.canControlMemberAccessible()) {
                field.setAccessible(true);
                field.set(target, args[0]);
            } else {
                throw e;
            }
        }
        return null;
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
    public <T extends Annotation> Annotation getAnnotation(Class<T> type) {
        return this.field.getAnnotation(type);
    }
}

