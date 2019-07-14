package org.galaxy.test.handler.reoader;

import org.galaxy.annotation.Handler;
import org.galaxy.handle.Handle;
import org.galaxy.handle.reoder.ReorderHandle;
import org.galaxy.param.ParamWrapper;

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
