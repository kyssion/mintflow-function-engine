package org.mekweg.test.handler.select;

import org.mekweg.annotation.Handler;
import org.mekweg.handle.select.SelectorHandle;
import org.mekweg.param.ParamWrapper;

@Handler("stwo")
public class SelectTwo extends SelectorHandle {
    @Override
    public boolean select(ParamWrapper p) {
        return false;
    }
}
