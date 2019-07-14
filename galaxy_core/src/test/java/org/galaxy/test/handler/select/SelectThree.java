package org.galaxy.test.handler.select;

import org.galaxy.annotation.Handler;
import org.galaxy.handle.select.SelectorHandle;
import org.galaxy.param.ParamWrapper;

@Handler("sthree")
public class SelectThree extends SelectorHandle {
    @Override
    public boolean select(ParamWrapper p) {
        return true;
    }
}
