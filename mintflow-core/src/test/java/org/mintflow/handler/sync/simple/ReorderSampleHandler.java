package org.mintflow.handler.sync.simple;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.handler.sync.SyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;

import static org.mintflow.test.syncBaseTest.SyncReorderTest.ADD_DATA_REORDER;

@MintFlowHandler(name = "reorder_sample_handle")
public class ReorderSampleHandler extends SyncSampleFnHandler {

    public static final String SYNC_REORDER_STR="sync_reorder_str";

    public ReorderSampleHandler(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        String itme = params.getContextParam(SYNC_REORDER_STR);
        params.setContextParam(SYNC_REORDER_STR,itme+ADD_DATA_REORDER);
        return params;
    }
}
