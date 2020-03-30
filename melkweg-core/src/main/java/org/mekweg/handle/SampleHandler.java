package org.mekweg.handle;

import org.mekweg.param.ParamWrapper;

/**
 * Implementation class of abstract classes
 */
public class SampleHandler extends Handler{

    String id;

    public SampleHandler(String id){
        this.setType(HandleType.SAMPLE_HANDLE);
        this.id = id;
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        System.out.println("run handle + {"+id+"}");
        return params;
    }

    @Override
    public SampleHandler clone() throws CloneNotSupportedException {
        return (SampleHandler) super.clone();
    }
}
