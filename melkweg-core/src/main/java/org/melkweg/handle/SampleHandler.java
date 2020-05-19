package org.melkweg.handle;

import org.melkweg.param.ParamWrapper;

import static org.melkweg.handle.HandleType.SAMPLE_HANDLE;

/**
 * Implementation class of abstract classes
 */
public abstract class SampleHandler extends Handler{

    public SampleHandler(String name){
        this(name,SAMPLE_HANDLE);
    }

    private SampleHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public SampleHandler clone() throws CloneNotSupportedException {
        return (SampleHandler) super.clone();
    }

}
