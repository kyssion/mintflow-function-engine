package org.mintflow.handler.sync.simple;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.sync.SyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;

@MintFlowHandler(name = "base_test_handle2")
public class BaseTestHandler2 extends SyncSampleFnHandler {
    public BaseTestHandler2(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        Integer item = params.getParam(Integer.class);
        params.setParam(item+2);
        return params;
    }
}
