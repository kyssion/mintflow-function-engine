package com.kyssion.galaxy.test.handler.select;

import com.kyssion.galaxy.annotation.Handler;
import com.kyssion.galaxy.handle.select.SelectorHandle;
import com.kyssion.galaxy.param.ParamWrapper;

@Handler("stwo")
public class SelectTwo extends SelectorHandle {
    @Override
    public boolean select(ParamWrapper p) {
        return false;
    }
}
