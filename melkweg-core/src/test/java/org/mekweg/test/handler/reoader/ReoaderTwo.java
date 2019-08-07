package org.mekweg.test.handler.reoader;

import org.mekweg.handle.Handle;
import org.mekweg.handle.reoder.ReorderHandle;
import org.mekweg.param.ParamWrapper;

import java.util.ArrayList;
import java.util.List;

public class ReoaderTwo extends ReorderHandle {

    @Override
    public List<Handle> buildHandleStream(List<Handle> handleList, ParamWrapper paramWrapper) {
        return new ArrayList<>();
    }
}
