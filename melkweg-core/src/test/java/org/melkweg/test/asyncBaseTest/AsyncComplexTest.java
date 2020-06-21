package org.melkweg.test.asyncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.melkweg.Melkweg;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.handler.async.condition.AsyncConditionHandler1;
import org.melkweg.handler.async.condition.AsyncConditionHandler2;
import org.melkweg.handler.async.condition.AsyncConditionHandler3;
import org.melkweg.handler.async.condition.AsyncConditionHandler4;
import org.melkweg.handler.async.reorder.AsyncReorderHandler;
import org.melkweg.handler.async.sample.*;
import org.melkweg.handler.sync.condition.ConditionHandler1;
import org.melkweg.handler.sync.condition.ConditionHandler2;
import org.melkweg.handler.sync.condition.ConditionHandler3;
import org.melkweg.handler.sync.condition.ConditionHandler4;
import org.melkweg.handler.sync.reorder.ReorderHandler;
import org.melkweg.handler.sync.simple.*;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.sync.SyncFnEngineSyncScheduler;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.melkweg.test.BaseTestUtil.*;
import static org.melkweg.test.syncBaseTest.ConditionTest.CAN_GO;
import static org.melkweg.test.syncBaseTest.ConditionTest.NO_GO;
import static org.melkweg.test.syncBaseTest.ReorderTest.ADD_DATA;

public class AsyncComplexTest {
    /**
     * 初始化
     */
    MelkwegHandleMapBuilder mapBuilder;
    @Before
    public void initMapDate(){
        mapBuilder = new MelkwegHandleMapBuilder();

        mapBuilder.put("async_condition_handle_1",new AsyncConditionHandler1("async_condition_handle_1"));
        mapBuilder.put("async_condition_handle_2",new AsyncConditionHandler2("async_condition_handle_2"));
        mapBuilder.put("async_condition_handle_3",new AsyncConditionHandler3("async_condition_handle_3"));
        mapBuilder.put("async_condition_handle_4",new AsyncConditionHandler4("async_condition_handle_4"));

        mapBuilder.put("async_base_test_handle1",new AsyncBaseTestHandler1("async_base_test_handle1"));
        mapBuilder.put("async_base_test_handle2",new AsyncBaseTestHandler2("async_base_test_handle2"));
        mapBuilder.put("async_base_test_handle3",new AsyncBaseTestHandler3("async_base_test_handle3"));

        mapBuilder.put("async_show_start_handle",new AsyncShowStartHandler("async_show_start_handle"));
        mapBuilder.put("async_show_end_handle",new AsyncShowEndHandler("async_show_end_handle"));

        mapBuilder.put("async_reorder_handle",new AsyncReorderHandler("async_reorder_handle"));
        mapBuilder.put("async_reorder_sample_handle",new AsyncReorderSampleHandler("async_reorder_sample_handle"));
    }
    /**
     * namespace(test_namespace){
     *     async process(async_sync_test_process){
     *         ->handle(async_show_start_handle)
     *         ->handle(async_base_test_handle1)->handle(async_base_test_handle2)->handle(async_base_test_handle3)->if(async_condition_handle_1){
     *             ->if(async_condition_handle_3){
     *                 ->handle(async_base_test_handle1)
     *             }elif(async_condition_handle_4){
     *                 ->handle(async_base_test_handle2)
     *             }else{
     *                 ->handle(async_base_test_handle3)
     *             }
     *         }elif(async_condition_handle_2){
     *             ->if(async_condition_handle_3){
     *                 ->handle(async_base_test_handle3)->handle(async_base_test_handle1)
     *             }elif(async_condition_handle_4){
     *                 ->reorder(async_reorder_handle){
     *                     ->handle(async_reorder_sample_handle)
     *                 }
     *             }else{
     *                 ->handle(async_base_test_handle3)->handle(async_base_test_handle3)
     *             }
     *         }else{
     *                 ->handle(async_base_test_handle3)->handle(async_base_test_handle3)->handle(async_base_test_handle1)
     *         }->handle(async_base_test_handle1)->handle(async_base_test_handle2)->handle(async_base_test_handle3)->handle(async_show_end_handle)
     *     }
     * }
     */

    @Test
    public void complexTest(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        String item = "test1";
        StringBuilder ans = new StringBuilder(item + ADD_DATA);
        AsyncParamWrapper paramWrapper= new AsyncParamWrapper();
        paramWrapper.setParam(1);
        paramWrapper.setParam(item);
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_complex_test.fn").build();
        melkweg.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            assertEquals(13, (int) param.getResult(Integer.class));
            int num = param.getContextParam("random_number");
            while(num>=0){
                ans.append(ADD_DATA);
                num--;
            }
            String nowItem = param.getParam(String.class);
            assertEquals(ans.toString(),nowItem);
            assertTrue(param.getContextParam("show_start"));
            assertTrue(param.getContextParam("show_end"));
            atomicBoolean.set(true);
        });

        assertTrue(atomicBoolean.get());
    }

}