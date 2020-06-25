package org.mintflow.test.asyncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mintflow.MintFlow;
import org.mintflow.param.ParamWrapper;
import org.mintflow.handle.util.MintFlowHandlerMapperBuilder;
import org.mintflow.handler.async.condition.AsyncConditionHandler1;
import org.mintflow.handler.async.condition.AsyncConditionHandler2;
import org.mintflow.handler.async.condition.AsyncConditionHandler3;
import org.mintflow.handler.async.condition.AsyncConditionHandler4;
import org.mintflow.handler.async.reorder.AsyncReorderHandler;
import org.mintflow.handler.async.sample.*;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.test.BaseTestUtil.*;

public class AsyncReorderTest {

    public final static String ADD_DATA="__reorder_handler";

    /**
     * 测试
     */
    MintFlowHandlerMapperBuilder mapBuilder;
    @Before
    public void initMapDate(){
        mapBuilder = new MintFlowHandlerMapperBuilder();

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
        String item = "test1";
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        StringBuilder ans = new StringBuilder(item + ADD_DATA);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setParam(item);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_reorder_test1.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
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
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setParam(item);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_reorder_test2.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
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
