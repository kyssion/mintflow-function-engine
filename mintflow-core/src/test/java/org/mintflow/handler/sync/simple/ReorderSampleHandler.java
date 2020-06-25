package org.mintflow.handler.sync.simple;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.test.syncBaseTest.ReorderTest;
import org.mintflow.handler.sync.SyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "reorder_sample_handle")
public class ReorderSampleHandler extends SyncSampleFnHandler {

    public ReorderSampleHandler(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        String itme = params.getParam(String.class);
        params.setParam(itme+ ReorderTest.ADD_DATA);
        return params;
    }
}
