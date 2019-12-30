package org.mekweg.handle;

import java.util.Map;

public class SampleHandler extends Handler{

    public SampleHandler(){
        this.setType(HandleType.SAMPLE_HANDLE);
    }

    @Override
    public Map<Class<?>, Object> handle(Map<Class<?>, Object> params) {
        return null;
    }

    @Override
    public SampleHandler clone() throws CloneNotSupportedException {
        return (SampleHandler) super.clone();
    }
}
