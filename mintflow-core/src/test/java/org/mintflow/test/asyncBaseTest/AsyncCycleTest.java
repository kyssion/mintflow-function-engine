package org.mintflow.test.asyncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mintflow.MintFlow;
import org.mintflow.handler.util.MintFlowHandlerMapBuilder;
import org.mintflow.handler.async.condition.AsyncConditionHandler1;
import org.mintflow.handler.async.condition.AsyncConditionHandler2;
import org.mintflow.handler.async.condition.AsyncConditionHandler3;
import org.mintflow.handler.async.condition.AsyncConditionHandler4;
import org.mintflow.handler.async.cycle.AsyncCycleTestHandler;
import org.mintflow.handler.async.reorder.AsyncReorderHandler;
import org.mintflow.handler.async.sample.*;
import org.mintflow.param.ParamWrapper;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.handler.async.cycle.AsyncCycleTestHandler.random_number_cycle;
import static org.mintflow.handler.async.sample.AsyncCycleSampleHandler.async_cycle_str;
import static org.mintflow.test.BaseTestUtil.*;

public class AsyncCycleTest {

    public final static String ADD_DATA_CYCLE = "_cycle";

    MintFlowHandlerMapBuilder mapBuilder;

    @Before
    public void initMapDate(){
        mapBuilder = new MintFlowHandlerMapBuilder();

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
        ParamWrapper paramWrapper = new ParamWrapper();
        String itemCycle = "test1";
        StringBuilder ansCycle = new StringBuilder(itemCycle);
        paramWrapper.setContextParam(async_cycle_str,itemCycle);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_cycle_test1.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            int numCycle = param.getContextParam(random_number_cycle);
            while(numCycle>0){
                ansCycle.append(ADD_DATA_CYCLE);
                numCycle--;
            }
            String nowCycleItem = paramWrapper.getContextParam(async_cycle_str);
            assertEquals(ansCycle.toString(),nowCycleItem);
            atomicBoolean.set(true);
        });

        assertTrue(atomicBoolean.get());

    }

    @Test
    public void cycleTest2(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        String itemCycle = "test1";
        StringBuilder ansCycle = new StringBuilder(itemCycle);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam(async_cycle_str,itemCycle);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_cycle_test2.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            int numCycle = param.getContextParam(random_number_cycle);
            while(numCycle>0){
                ansCycle.append(ADD_DATA_CYCLE);
                numCycle--;
            }
            String nowCycleItem = paramWrapper.getContextParam(async_cycle_str);
            assertEquals(ansCycle.toString(),nowCycleItem);
            atomicBoolean.set(true);
            assertTrue(paramWrapper.getContextParam("show_start"));
            assertTrue(paramWrapper.getContextParam("show_end"));
        });

        assertTrue(atomicBoolean.get());
    }
}
