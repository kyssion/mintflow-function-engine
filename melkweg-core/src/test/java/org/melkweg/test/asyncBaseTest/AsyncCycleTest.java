package org.melkweg.test.asyncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.melkweg.Melkweg;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.handler.async.condition.AsyncConditionHandler1;
import org.melkweg.handler.async.condition.AsyncConditionHandler2;
import org.melkweg.handler.async.condition.AsyncConditionHandler3;
import org.melkweg.handler.async.condition.AsyncConditionHandler4;
import org.melkweg.handler.async.cycle.AsyncCycleTestHandler;
import org.melkweg.handler.async.reorder.AsyncReorderHandler;
import org.melkweg.handler.async.sample.*;
import org.melkweg.handler.sync.condition.ConditionHandler1;
import org.melkweg.handler.sync.condition.ConditionHandler2;
import org.melkweg.handler.sync.condition.ConditionHandler3;
import org.melkweg.handler.sync.condition.ConditionHandler4;
import org.melkweg.handler.sync.cycle.SyncCycleTestHandler;
import org.melkweg.handler.sync.reorder.ReorderHandler;
import org.melkweg.handler.sync.simple.*;
import org.melkweg.handler.sync.simple.CycleSampleHandler;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.sync.SyncFnEngineSyncScheduler;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.melkweg.test.BaseTestUtil.*;

public class AsyncCycleTest {

    public final static String ADD_DATA = "_cycle";

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
        mapBuilder.put("async_cycle_test",new AsyncCycleTestHandler("async_cycle_test"));
        mapBuilder.put("async_cycle_sample_handler",new AsyncCycleSampleHandler("async_cycle_sample_handler"));


    }

    @Test
    public void cycleTest1(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        String item = "test1";
        StringBuilder ans = new StringBuilder(item);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setParam(item);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_cycle_test1.fn").build();
        melkweg.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            int num = param.getContextParam("random_number");
            while(num>0){
                ans.append(ADD_DATA);
                num--;
            }
            String nowitem = paramWrapper.getParam(String.class);
            assertEquals(ans.toString(),nowitem);
            atomicBoolean.set(true);
        });

        assertTrue(atomicBoolean.get());

    }

    @Test
    public void cycleTest2(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        String item = "test1";
        StringBuilder ans = new StringBuilder(item);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setParam(item);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_cycle_test2.fn").build();
        melkweg.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            int num = param.getContextParam("random_number");
            System.out.println(num);
            while(num>0){
                ans.append(ADD_DATA);
                num--;
            }
            String nowitem = paramWrapper.getParam(String.class);
            assertEquals(ans.toString(),nowitem);
            atomicBoolean.set(true);
            assertTrue(paramWrapper.getContextParam("show_start"));
            assertTrue(paramWrapper.getContextParam("show_end"));
        });

        assertTrue(atomicBoolean.get());
    }
}
