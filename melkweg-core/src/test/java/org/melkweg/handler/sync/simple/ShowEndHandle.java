package org.melkweg.handler.sync.simple;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "show_end_handle")
public class ShowEndHandle extends SyncSampleFnHandler {
    public ShowEndHandle(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        params.setContextParam("show_end",true);
        return params;
    }
}
