package org.mintflow.handler.sync.simple;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.sync.SyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "show_start_handle")
public class ShowStartHandler extends SyncSampleFnHandler {
    public ShowStartHandler(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        params.setContextParam("show_start",true);
        return params;
    }
}
