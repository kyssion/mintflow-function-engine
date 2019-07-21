package org.mekweg.test.handler.reoader;

import org.mekweg.annotation.Handler;
import org.mekweg.handle.Handle;
import org.mekweg.handle.reoder.ReorderHandle;
import org.mekweg.param.ParamWrapper;

import java.util.Comparator;
import java.util.List;

@Handler(value = "rone")
public class ReoaderOne extends ReorderHandle {
    @Override
    public void buildHandleSteam(List<Handle> handleList, ParamWrapper paramWrapper) {
        handleList.sort(new Comparator<Handle>() {
            @Override
            public int compare(Handle o1, Handle o2) {
                return 0;
            }
        });
    }
}
