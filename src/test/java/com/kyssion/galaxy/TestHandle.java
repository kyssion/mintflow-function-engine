package com.kyssion.galaxy;


import com.kyssion.galaxy.annotation.Handler;
import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.param.ParamWrapper;

@Handler("testId")
public class TestHandle implements Handle {

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return null;
    }
}
