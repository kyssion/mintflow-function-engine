package org.mintflow.test.syncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mintflow.MintFlow;
import org.mintflow.handle.util.MintFlowHandlerMapperBuilder;
import org.mintflow.handler.sync.condition.ConditionHandler1;
import org.mintflow.handler.sync.condition.ConditionHandler2;
import org.mintflow.handler.sync.condition.ConditionHandler3;
import org.mintflow.handler.sync.condition.ConditionHandler4;
import org.mintflow.handler.sync.reorder.ReorderHandler;
import org.mintflow.handler.sync.simple.*;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.sync.SyncFnEngineSyncScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.test.BaseTestUtil.NAME_SPACE;
import static org.mintflow.test.BaseTestUtil.SYNC_PROCESS_NAME;
import static org.mintflow.test.syncBaseTest.ConditionTest.CAN_GO;
import static org.mintflow.test.syncBaseTest.ConditionTest.NO_GO;
import static org.mintflow.test.syncBaseTest.ReorderTest.ADD_DATA;

public class SyncComplexTest {
    /**
     * 初始化
     */
    MintFlowHandlerMapperBuilder mapBuilder;

    @Before
    public void initMapDate(){
        mapBuilder = new MintFlowHandlerMapperBuilder();

        mapBuilder.put("condition_handle_1",new ConditionHandler1("condition_handle_1"));
        mapBuilder.put("condition_handle_2",new ConditionHandler2("condition_handle_2"));
        mapBuilder.put("condition_handle_3",new ConditionHandler3("condition_handle_3"));
        mapBuilder.put("condition_handle_4",new ConditionHandler4("condition_handle_4"));


        mapBuilder.put("base_test_handle1",new BaseTestHandler1("base_test_handle1"));
        mapBuilder.put("base_test_handle2",new BaseTestHandler2("base_test_handle2"));
        mapBuilder.put("base_test_handle3",new BaseTestHandler3("base_test_handle3"));

        mapBuilder.put("show_start_handle",new ShowStartHandler("show_start_handle"));
        mapBuilder.put("show_end_handle",new ShowEndHandler("show_end_handle"));

        mapBuilder.put("reorder_handle",new ReorderHandler("reorder_handle"));
        mapBuilder.put("reorder_sample_handle",new ReorderSampleHandler("reorder_sample_handle"));
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
        String item = "test1";
        StringBuilder ans = new StringBuilder(item + ADD_DATA);
        ParamWrapper paramWrapper= new ParamWrapper();
        paramWrapper.setParam(1);
        paramWrapper.setParam(item);
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_complex_test.fn").build();
        paramWrapper = mintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
        assertEquals(13, (int) paramWrapper.getResult(Integer.class));

        int num = paramWrapper.getContextParam("random_number");
        while(num>=0){
            ans.append(ADD_DATA);
            num--;
        }
        item = paramWrapper.getParam(String.class);
        assertEquals(ans.toString(),item);

        assertTrue(paramWrapper.getContextParam("show_start"));
        assertTrue(paramWrapper.getContextParam("show_end"));
    }

}
