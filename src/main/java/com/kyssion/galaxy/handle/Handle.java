package com.kyssion.galaxy.handle;

import com.kyssion.galaxy.mark.Type;
import com.kyssion.galaxy.param.ParamWrapper;


public interface Handle {

    default Type type(){
        return Type.HANDLE;
    }

    default void before(){}

    default void after(){ }

    default void error(){}

    ParamWrapper handle(ParamWrapper p);
}
