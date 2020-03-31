package org.mekweg.handle;

import org.mekweg.param.ParamWrapper;

import static org.mekweg.handle.HandleType.SAMPLE_HANDLE;

/**
 * Implementation class of abstract classes
 */
public class SampleHandler extends Handler{

    public SampleHandler(String name){
        this(name,SAMPLE_HANDLE);
    }

    public SampleHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        System.out.println("run handle + {"+getName()+"}");
        return params;
    }

    @Override
    public SampleHandler clone() throws CloneNotSupportedException {
        return (SampleHandler) super.clone();
    }
}
