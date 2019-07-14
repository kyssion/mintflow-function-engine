package org.galaxy.test.handler.select;

import org.galaxy.annotation.Handler;
import org.galaxy.handle.select.SelectorHandle;
import org.galaxy.param.ParamWrapper;

@Handler("stwo")
public class SelectTwo extends SelectorHandle {
    @Override
    public boolean select(ParamWrapper p) {
        return false;
    }
}
