package org.melkweg.handler.simple;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "show_start_handle")
public class ShowStartHandle extends SyncSampleFnHandler {
    public ShowStartHandle(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        params.setContextParam("show_start",true);
        return params;
    }
}
