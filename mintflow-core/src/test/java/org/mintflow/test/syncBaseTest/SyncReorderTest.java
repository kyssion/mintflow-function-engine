package org.mintflow.test.syncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mintflow.MintFlow;
import org.mintflow.handler.util.MintFlowHandlerMapBuilder;
import org.mintflow.handler.sync.condition.ConditionHandler1;
import org.mintflow.handler.sync.condition.ConditionHandler2;
import org.mintflow.handler.sync.condition.ConditionHandler3;
import org.mintflow.handler.sync.condition.ConditionHandler4;
import org.mintflow.handler.sync.reorder.SyncReorderHandler;
import org.mintflow.handler.sync.simple.*;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.sync.SyncFnEngineSyncScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.handler.async.reorder.AsyncReorderHandler.random_number_reorder;
import static org.mintflow.handler.sync.simple.ReorderSampleHandler.SYNC_REORDER_STR;
import static org.mintflow.test.BaseTestUtil.NAME_SPACE;
import static org.mintflow.test.BaseTestUtil.SYNC_PROCESS_NAME;

public class SyncReorderTest {

    public final static String ADD_DATA_REORDER="__reorder_handler";

    /**
     * 初始化
     */
    MintFlowHandlerMapBuilder mapBuilder;

    @Before
    public void initMapDate(){
        mapBuilder = new MintFlowHandlerMapBuilder();

        mapBuilder.put("condition_handle_1",new ConditionHandler1("condition_handle_1"));
        mapBuilder.put("condition_handle_2",new ConditionHandler2("condition_handle_2"));
        mapBuilder.put("condition_handle_3",new ConditionHandler3("condition_handle_3"));
        mapBuilder.put("condition_handle_4",new ConditionHandler4("condition_handle_4"));


        mapBuilder.put("base_test_handle1",new BaseTestHandler1("base_test_handle1"));
        mapBuilder.put("base_test_handle2",new BaseTestHandler2("base_test_handle2"));
        mapBuilder.put("base_test_handle3",new BaseTestHandler3("base_test_handle3"));
        mapBuilder.put("reorder_sample_handle",new ReorderSampleHandler("reorder_sample_handle"));

        mapBuilder.put("show_start_handle",new ShowStartHandler("show_start_handle"));
        mapBuilder.put("show_end_handle",new ShowEndHandler("show_end_handle"));

        mapBuilder.put("reorder_handle",new SyncReorderHandler("reorder_handle"));
        mapBuilder.put("reorder_sample_handle",new ReorderSampleHandler("reorder_sample_handle"));
    }

    /**
     * namespace(test_namespace){
     *     sync process(sync_test_process){
     *         ->reorder(reorder_handle){
     *             ->handle(reorder_sample_handle)
     *         }
     *     }
     * }
     */
    @Test
    public void reorderTest1(){
        ParamWrapper paramWrapper = new ParamWrapper();

        String itemReorder = "test1";
        StringBuilder ansReorder = new StringBuilder(itemReorder);
        paramWrapper.setContextParam(SYNC_REORDER_STR,itemReorder);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_reorder_test1.fn").build();
        paramWrapper = mintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
        int numReorder = paramWrapper.getContextParam(random_number_reorder);
        while(numReorder>0){
            ansReorder.append(ADD_DATA_REORDER);
            numReorder--;
        }
        itemReorder = paramWrapper.getContextParam(SYNC_REORDER_STR);
        assertEquals(ansReorder.toString(),itemReorder);
    }

    @Test
    public void reorderTest2(){
        ParamWrapper paramWrapper = new ParamWrapper();

        String itemReorder = "test1";
        StringBuilder ansReorder = new StringBuilder(itemReorder);
        paramWrapper.setContextParam(SYNC_REORDER_STR,itemReorder);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_reorder_test2.fn").build();
        paramWrapper = mintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
        int numReorder = paramWrapper.getContextParam(random_number_reorder);
        while(numReorder>0){
            ansReorder.append(ADD_DATA_REORDER);
            numReorder--;
        }
        itemReorder = paramWrapper.getContextParam(SYNC_REORDER_STR);
        assertEquals(ansReorder.toString(),itemReorder);
        assertTrue(paramWrapper.getContextParam("show_start"));
        assertTrue(paramWrapper.getContextParam("show_end"));
    }

}
