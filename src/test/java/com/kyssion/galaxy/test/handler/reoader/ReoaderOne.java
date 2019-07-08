package com.kyssion.galaxy.test.handler.reoader;

import com.kyssion.galaxy.annotation.Handler;
import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.reoder.ReorderHandle;
import com.kyssion.galaxy.param.ParamWrapper;

import java.util.Collections;
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
