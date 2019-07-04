package com.kyssion.galaxy.handle.select;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.type.Type;
import com.kyssion.galaxy.param.ParamWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单选择器
 */
public abstract class SelectorHandle implements Handle {

    public abstract boolean select(ParamWrapper p);

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return p;
    }

}
