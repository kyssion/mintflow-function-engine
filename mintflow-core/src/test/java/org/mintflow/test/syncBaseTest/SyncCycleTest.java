package org.mintflow.test.syncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mintflow.MintFlow;
import org.mintflow.handler.util.MintFlowHandlerMapBuilder;
import org.mintflow.handler.sync.condition.ConditionHandler1;
import org.mintflow.handler.sync.condition.ConditionHandler2;
import org.mintflow.handler.sync.condition.ConditionHandler3;
import org.mintflow.handler.sync.condition.ConditionHandler4;
import org.mintflow.handler.sync.cycle.SyncCycleHandler;
import org.mintflow.handler.sync.reorder.SyncReorderHandler;
import org.mintflow.handler.sync.simple.*;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.sync.SyncFnEngineSyncScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.handler.async.cycle.AsyncCycleTestHandler.random_number_cycle;
import static org.mintflow.handler.sync.simple.CycleSampleHandler.SYNC_CYCLE_STR;
import static org.mintflow.test.BaseTestUtil.NAME_SPACE;
import static org.mintflow.test.BaseTestUtil.SYNC_PROCESS_NAME;

public class SyncCycleTest {

    public final static String ADD_DATA_CYCLE = "_cycle";

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

        mapBuilder.put("sync_cycle_sample_handler",new CycleSampleHandler("sync_cycle_sample_handler"));
        mapBuilder.put("sync_cycle_test",new SyncCycleHandler("sync_cycle_test"));
    }

    @Test
    public void cycleTest1(){
        ParamWrapper paramWrapper = new ParamWrapper();
        String itemCycle = "test1";
        StringBuilder ansCycle = new StringBuilder(itemCycle);
        paramWrapper.setContextParam(SYNC_CYCLE_STR,itemCycle);


        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_cycle_test1.fn").build();
        paramWrapper = mintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
        int numCycle = paramWrapper.getContextParam(random_number_cycle);
        while(numCycle>0){
            ansCycle.append(ADD_DATA_CYCLE);
            numCycle--;
        }
        itemCycle = paramWrapper.getContextParam(SYNC_CYCLE_STR);
        assertEquals(ansCycle.toString(),itemCycle);
    }

    @Test
    public void cycleTest2(){
        ParamWrapper paramWrapper = new ParamWrapper();

        String itemCycle = "test1";
        StringBuilder ansCycle = new StringBuilder(itemCycle);
        paramWrapper.setContextParam(SYNC_CYCLE_STR,itemCycle);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_cycle_test2.fn").build();
        paramWrapper = mintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
        int numCycle = paramWrapper.getContextParam(random_number_cycle);
        while(numCycle>0){
            ansCycle.append(ADD_DATA_CYCLE);
            numCycle--;
        }
        itemCycle = paramWrapper.getContextParam(SYNC_CYCLE_STR);
        assertEquals(ansCycle.toString(),itemCycle);
        assertTrue(paramWrapper.getContextParam("show_start"));
        assertTrue(paramWrapper.getContextParam("show_end"));
    }
}
