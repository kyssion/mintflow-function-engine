package org.galaxy.test.handler.select;

import org.galaxy.annotation.Handler;
import org.galaxy.handle.select.SelectorHandle;
import org.galaxy.param.ParamWrapper;

@Handler("sone")
public class SelectOne extends SelectorHandle {
    @Override
    public boolean select(ParamWrapper p) {
        Integer integer = p.get(Integer.class);
        return true;
    }
}
