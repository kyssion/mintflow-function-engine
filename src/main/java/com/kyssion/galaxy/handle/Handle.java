package com.kyssion.galaxy.handle;

import com.kyssion.galaxy.param.ParamWrapper;

public interface Handle {

    default void before(){}

    default void after(){ }

    default void error(){}

    ParamWrapper handle(ParamWrapper p);
}
