package com.kyssion.galaxy.handle;

import com.kyssion.galaxy.handle.type.Type;
import com.kyssion.galaxy.param.ParamWrapper;


public interface Handle {

    default Type getType() {
        return Type.HANDLE;
    }

    default void before() {
    }

    default void after() {
    }

    default void error(Exception e) {
    }

    ParamWrapper handle(ParamWrapper p);
}
