package org.mintflow.reflection.agent;

import java.lang.annotation.Annotation;

public class GetAndSetFieldAgent {
    private Agent getFieldAgent;
    private Agent setFieldAgent;
    public GetAndSetFieldAgent(Agent getFieldAgent,Agent setFieldAgent){
        this.getFieldAgent = getFieldAgent;
        this.setFieldAgent = setFieldAgent;
    }

    public Agent getGetFieldAgent() {
        return getFieldAgent;
    }

    public Agent getSetFieldAgent() {
        return setFieldAgent;
    }

    public Annotation[] getAllAnnotation() {
        return this.getFieldAgent.getAllAnnotation();
    }

    public <T extends Annotation> Annotation getAnnotation(Class<T> type) {
        return this.setFieldAgent.getAnnotation(type);
    }
}
