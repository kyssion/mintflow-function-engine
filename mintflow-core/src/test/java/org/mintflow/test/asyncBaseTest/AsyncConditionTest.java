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
import org.mintflow.handler.async.sample.*;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.test.BaseTestUtil.*;

public class AsyncConditionTest {

    public static final String CAN_GO="can_go";
    public static final String NO_GO="no_go";
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
    }

    /**
     *
     * 测试脚本：
     * namespace(test_namespace){
     *     sync process(async_test_process){
     *         ->if(async_condition_handle_1){
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
     *                 ->handle(async_base_test_handle3)->handle(async_base_test_handle2)
     *             }else{
     *                 ->handle(async_base_test_handle3)->handle(async_base_test_handle3)
     *             }
     *         }else{
     *             ->handle(async_base_test_handle3)->handle(async_base_test_handle3)->handle(async_base_test_handle1)
     *         }
     *     }
     * }
     */

    /**
     * 测试 走 c1 c3 逻辑
     */
    @Test
    public void asyncConditionTest1(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",CAN_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",CAN_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setParam(1);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_condition_base_test1.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            assertEquals(2,(int)param.getParam(Integer.class));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }
    /**
     * 测试 走 c1 c4 逻辑
     */
    @Test
    public void asyncConditionTest2(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",CAN_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setParam(1);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_condition_base_test1.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            assertEquals(3,(int)param.getParam(Integer.class));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }
    /**
     * 测试 走 c1 else 逻辑
     */
    @Test
    public void asyncConditionTest3(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",CAN_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",NO_GO);
        paramWrapper.setParam(1);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_condition_base_test1.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            assertEquals(4,(int)param.getParam(Integer.class));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }


    /**
     * 测试 走 c2 c3 逻辑
     */
    @Test
    public void asyncConditionTest4(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",CAN_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setParam(1);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_condition_base_test1.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            assertEquals(5,(int)param.getParam(Integer.class));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }
    /**
     * 测试 走 c2 c4 逻辑
     */
    @Test
    public void asyncConditionTest5(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setParam(1);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_condition_base_test1.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            assertEquals(6,(int)param.getParam(Integer.class));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }

    /**
     * 测试 走 c2 else 逻辑
     */
    @Test
    public void asyncConditionTest6(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",NO_GO);
        paramWrapper.setParam(1);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_condition_base_test1.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            assertEquals(7,(int)param.getParam(Integer.class));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }

    /**
     * 测试 走 else else 逻辑
     */
    @Test
    public void asyncConditionTest7(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",NO_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",NO_GO);
        paramWrapper.setParam(1);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_condition_base_test1.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            assertEquals(8,(int)param.getParam(Integer.class));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }


    /**
     * 测试包括开头和结尾的逻辑
     */
    @Test
    public void asyncConditionTest8(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",NO_GO);
        paramWrapper.setParam(1);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        MintFlow mintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_condition_base_test2.fn").build();
        mintFlow.runAsync(NAME_SPACE,ASYNC_PROCESS_NAME,paramWrapper,param->{
            assertEquals(7,(int)param.getParam(Integer.class));
            assertTrue(param.getContextParam("show_start"));
            assertTrue(param.getContextParam("show_end"));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());
    }
}
