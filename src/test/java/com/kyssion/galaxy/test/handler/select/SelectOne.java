package com.kyssion.galaxy.test.handler.select;

import com.kyssion.galaxy.annotation.Handler;
import com.kyssion.galaxy.handle.select.SelectorHandle;
import com.kyssion.galaxy.param.ParamWrapper;

@Handler("sone")
public class SelectOne extends SelectorHandle {
    @Override
    public boolean select(ParamWrapper p) {
        Integer integer = p.get(Integer.class);
        return integer != null && integer > 1000;
    }
}
