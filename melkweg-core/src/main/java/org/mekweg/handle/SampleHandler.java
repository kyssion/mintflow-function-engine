package org.mekweg.handle;

import org.mekweg.param.ParamWrapper;

public class SampleHandler extends Handler{

    public SampleHandler(){
        this.setType(HandleType.SAMPLE_HANDLE);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        return null;
    }

    @Override
    public SampleHandler clone() throws CloneNotSupportedException {
        return (SampleHandler) super.clone();
    }
}
