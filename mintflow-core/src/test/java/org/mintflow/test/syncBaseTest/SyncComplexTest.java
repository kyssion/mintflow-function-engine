package org.mintflow.test.syncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mintflow.MintFlow;
import org.mintflow.handler.sync.cycle.SyncCycleHandler;
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
import static org.mintflow.handler.async.cycle.AsyncCycleTestHandler.random_number_cycle;
import static org.mintflow.handler.async.reorder.AsyncReorderHandler.random_number_reorder;
import static org.mintflow.handler.sync.simple.CycleSampleHandler.SYNC_CYCLE_STR;
import static org.mintflow.handler.sync.simple.ReorderSampleHandler.SYNC_REORDER_STR;
import static org.mintflow.test.BaseTestUtil.NAME_SPACE;
import static org.mintflow.test.BaseTestUtil.SYNC_PROCESS_NAME;
import static org.mintflow.test.syncBaseTest.ConditionTest.CAN_GO;
import static org.mintflow.test.syncBaseTest.ConditionTest.NO_GO;
import static org.mintflow.test.syncBaseTest.SyncCycleTest.ADD_DATA_CYCLE;
import static org.mintflow.test.syncBaseTest.SyncReorderTest.ADD_DATA_REORDER;

public class SyncComplexTest {
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

        mapBuilder.put("show_start_handle",new ShowStartHandler("show_start_handle"));
        mapBuilder.put("show_end_handle",new ShowEndHandler("show_end_handle"));

        mapBuilder.put("reorder_handle",new SyncReorderHandler("reorder_handle"));
        mapBuilder.put("reorder_sample_handle",new ReorderSampleHandler("reorder_sample_handle"));

        mapBuilder.put("sync_cycle_sample_handler",new CycleSampleHandler("sync_cycle_sample_handler"));
        mapBuilder.put("sync_cycle_test",new SyncCycleHandler("sync_cycle_test"));
    }

    /**
     * namespace(test_namespace){
     *     sync process(sync_test_process){
     *         ->handle(show_start_handle)
     *         ->handle(base_test_handle1)->handle(base_test_handle2)->handle(base_test_handle3)->if(condition_handle_1){
     *             ->if(condition_handle_3){
     *                 ->handle(base_test_handle1)
     *             }elif(condition_handle_4){
     *                 ->handle(base_test_handle2)
     *             }else{
     *                 ->handle(base_test_handle3)
     *             }
     *         }elif(condition_handle_2){
     *             ->if(condition_handle_3){
     *                 ->handle(base_test_handle3)->handle(base_test_handle1)
     *             }elif(condition_handle_4){
     *                 ->reorder(reorder_handle){
     *                     ->handle(reorder_sample_handle)
     *                 }
     *             }else{
     *                 ->handle(base_test_handle3)->handle(base_test_handle3)
     *             }
     *         }else{
     *                 ->handle(base_test_handle3)->handle(base_test_handle3)->handle(base_test_handle1)
     *         }->handle(base_test_handle1)->handle(base_test_handle2)->handle(base_test_handle3)->handle(show_end_handle)
     *     }
     * }
     */

    @Test
    public void complexTest(){


        ParamWrapper paramWrapper= new ParamWrapper();
        paramWrapper.setParam(1);
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);

        String itemCycle = "test1";
        StringBuilder ansCycle = new StringBuilder(itemCycle);
        paramWrapper.setContextParam(SYNC_CYCLE_STR,itemCycle);

        String itemReorder = "test1";
        StringBuilder ansReorder = new StringBuilder(itemReorder);
        paramWrapper.setContextParam(SYNC_REORDER_STR,itemReorder);

        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_complex_test.fn").build();
        paramWrapper = mintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
        assertEquals(13, (int) paramWrapper.getResult(Integer.class));

        int numCycle = paramWrapper.getContextParam(random_number_cycle);
        while(numCycle>0){
            ansCycle.append(ADD_DATA_CYCLE);
            numCycle--;
        }
        itemCycle = paramWrapper.getContextParam(SYNC_CYCLE_STR);
        assertEquals(ansCycle.toString(),itemCycle);

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
