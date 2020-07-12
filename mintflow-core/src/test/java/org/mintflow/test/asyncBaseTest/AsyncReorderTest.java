package org.mintflow.test.asyncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mintflow.MintFlow;
import org.mintflow.param.ParamWrapper;
import org.mintflow.handler.util.MintFlowHandlerMapBuilder;
import org.mintflow.handler.async.condition.AsyncConditionHandler1;
import org.mintflow.handler.async.condition.AsyncConditionHandler2;
import org.mintflow.handler.async.condition.AsyncConditionHandler3;
import org.mintflow.handler.async.condition.AsyncConditionHandler4;
import org.mintflow.handler.async.reorder.AsyncReorderHandler;
import org.mintflow.handler.async.sample.*;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.handler.async.reorder.AsyncReorderHandler.random_number_reorder;
import static org.mintflow.handler.async.sample.AsyncReorderSampleHandler.async_reorder_str;
import static org.mintflow.test.BaseTestUtil.*;

public class AsyncReorderTest {

    public final static String ADD_DATA_REORDER="__reorder_handler";

    /**
     * 测试
     */
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
        String itemReorder = "test1";
        ParamWrapper paramWrapper = new ParamWrapper();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        StringBuilder ansReorder = new StringBuilder(itemReorder);
        paramWrapper.setContextParam(async_reorder_str,itemReorder);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_reorder_test1.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            int numReorder = param.getContextParam(random_number_reorder);
            while(numReorder>0){
                ansReorder.append(ADD_DATA_REORDER);
                numReorder--;
            }
            String nowItemReorder =  param.getContextParam(async_reorder_str);
            assertEquals(ansReorder.toString(),nowItemReorder);
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }

    @Test
    public void reorderTest2(){
        String itemReorder = "test1";
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParamWrapper paramWrapper = new ParamWrapper();

        StringBuilder ansReorder = new StringBuilder(itemReorder);
        paramWrapper.setContextParam(async_reorder_str,itemReorder);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_reorder_test2.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            int numReorder = param.getContextParam(random_number_reorder);
            while(numReorder>0){
                ansReorder.append(ADD_DATA_REORDER);
                numReorder--;
            }
            String nowItem =  param.getContextParam(async_reorder_str);
            assertEquals(ansReorder.toString(),nowItem);
            assertTrue(paramWrapper.getContextParam("show_start"));
            assertTrue(paramWrapper.getContextParam("show_end"));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }

}
