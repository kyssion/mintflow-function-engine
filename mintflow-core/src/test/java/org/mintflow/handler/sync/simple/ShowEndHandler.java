package org.mintflow.handler.sync.simple;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handle.sync.SyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "show_end_handle")
public class ShowEndHandler extends SyncSampleFnHandler {
    public ShowEndHandler(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        params.setContextParam("show_end",true);
        return params;
    }
}
