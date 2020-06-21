package org.melkweg.test.asyncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.melkweg.Melkweg;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.handler.async.condition.AsyncConditionHandle1;
import org.melkweg.handler.async.condition.AsyncConditionHandle2;
import org.melkweg.handler.async.condition.AsyncConditionHandle3;
import org.melkweg.handler.async.condition.AsyncConditionHandle4;
import org.melkweg.handler.async.reorder.AsyncReorderHandler;
import org.melkweg.handler.async.sample.*;
import org.melkweg.handler.sync.condition.ConditionHandle1;
import org.melkweg.handler.sync.condition.ConditionHandle2;
import org.melkweg.handler.sync.condition.ConditionHandle3;
import org.melkweg.handler.sync.condition.ConditionHandle4;
import org.melkweg.handler.sync.reorder.ReorderHandler;
import org.melkweg.handler.sync.simple.*;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.FnEngineScheduler;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.melkweg.test.BaseTestUtil.*;

public class AsyncReorderTest {

    public final static String ADD_DATA="__reorder_handler";

    /**
     * 测试
     */
    MelkwegHandleMapBuilder mapBuilder;
    @Before
    public void initMapDate(){
        mapBuilder = new MelkwegHandleMapBuilder();

        mapBuilder.add("async_condition_handle_1",new AsyncConditionHandle1("async_condition_handle_1"));
        mapBuilder.add("async_condition_handle_2",new AsyncConditionHandle2("async_condition_handle_2"));
        mapBuilder.add("async_condition_handle_3",new AsyncConditionHandle3("async_condition_handle_3"));
        mapBuilder.add("async_condition_handle_4",new AsyncConditionHandle4("async_condition_handle_4"));

        mapBuilder.add("async_base_test_handle1",new AsyncBaseTestHandle1("async_base_test_handle1"));
        mapBuilder.add("async_base_test_handle2",new AsyncBaseTestHandle2("async_base_test_handle2"));
        mapBuilder.add("async_base_test_handle3",new AsyncBaseTestHandle3("async_base_test_handle3"));

        mapBuilder.add("async_show_start_handle",new AsyncShowStartHandle("async_show_start_handle"));
        mapBuilder.add("async_show_end_handle",new AsyncShowEndHandle("async_show_end_handle"));

        mapBuilder.add("async_reorder_handle",new AsyncReorderHandler("async_reorder_handle"));
        mapBuilder.add("async_reorder_sample_handle",new AsyncReorderSampleHandle("async_reorder_sample_handle"));
    }


    /**
     * namespace(test_namespace){
     *     async process(async_test_process){
     *         ->reorder(async_reorder_handle){
     *             ->handle(async_reorder_sample_handle)
     *         }
     *     }
     * }
     */
    @Test
    public void reorderTest1(){
        String item = "test1";
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        StringBuilder ans = new StringBuilder(item + ADD_DATA);
        AsyncParamWrapper paramWrapper = new AsyncParamWrapper();
        paramWrapper.setParam(item);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_reorder_test1.fn").build();
        melkweg.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            int num = param.getContextParam("random_number");
            while(num>=0){
                ans.append(ADD_DATA);
                num--;
            }
            String nowItem =  param.getParam(String.class);
            assertEquals(ans.toString(),nowItem);
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }

    @Test
    public void reorderTest2(){
        String item = "test1";
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        StringBuilder ans = new StringBuilder(item + ADD_DATA);
        AsyncParamWrapper paramWrapper = new AsyncParamWrapper();
        paramWrapper.setParam(item);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_reorder_test2.fn").build();
        melkweg.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            int num = param.getContextParam("random_number");
            while(num>=0){
                ans.append(ADD_DATA);
                num--;
            }
            String nowItem =  param.getParam(String.class);
            assertEquals(ans.toString(),nowItem);
            assertTrue(paramWrapper.getContextParam("show_start"));
            assertTrue(paramWrapper.getContextParam("show_end"));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }

}
