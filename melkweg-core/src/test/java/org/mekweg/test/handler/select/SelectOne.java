package org.mekweg.test.handler.select;

import org.mekweg.annotation.Handler;
import org.mekweg.handle.select.SelectorHandle;
import org.mekweg.param.ParamWrapper;

@Handler("sone")
public class SelectOne extends SelectorHandle {
    @Override
    public boolean select(ParamWrapper p) {
        Integer integer = p.get(Integer.class);
        return true;
    }
}
