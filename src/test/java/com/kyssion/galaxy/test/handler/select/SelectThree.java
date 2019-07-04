package com.kyssion.galaxy.test.handler.select;

import com.kyssion.galaxy.annotation.Handler;
import com.kyssion.galaxy.handle.select.SelectorHandle;
import com.kyssion.galaxy.param.ParamWrapper;

@Handler("sthree")
public class SelectThree extends SelectorHandle {
    @Override
    public boolean select(ParamWrapper p) {
        return true;
    }
}
