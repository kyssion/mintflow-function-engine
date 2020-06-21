package org.melkweg.test.asyncBaseTest;


import org.junit.Before;
import org.junit.Test;
import org.melkweg.Melkweg;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.handler.async.sample.*;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AsyncBaseTest {


    MelkwegHandleMapBuilder mapBuilder;

    @Before
    public void initMaperData(){
        mapBuilder = new MelkwegHandleMapBuilder();
        mapBuilder.add("async_base_test_handle1",new AsyncBaseTestHandler1("async_base_test_handle1"));
        mapBuilder.add("async_base_test_handle2",new AsyncBaseTestHandler2("async_base_test_handle2"));
        mapBuilder.add("async_base_test_handle3",new AsyncBaseTestHandler3("async_base_test_handle3"));

        mapBuilder.add("async_show_start_handle",new AsyncShowStartHandler("async_show_start_handle"));
        mapBuilder.add("async_show_end_handle",new AsyncShowEndHandler("async_show_end_handle"));
    }

    /**
     * 测试脚本 ：
     * namespace(test_namespace){
     *     sync process(sync_test_process){
     *         ->handle(base_test_handle1)->handle(base_test_handle2)->handle(base_test_handle3)
     *     }
     * }
     *
     *
     * namespace(test_namespace){
     *     sync process(sync_test_process){
     *         ->handle(show_start_handle)->handle(base_test_handle1)->handle(base_test_handle2)->handle(base_test_handle3)
     *         ->handle(show_end_handle)
     *     }
     * }
     */

    /**
     * 测试简单的构造流程是否可以覆盖
     */
    @Test
    public void asyncBaseTest1(){
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_base_test1.fn").build();
        AsyncParamWrapper paramWrapper = new AsyncParamWrapper();
        paramWrapper.setParam(1);
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        melkweg.runAsync(
                "test_namespace", "async_test_process", paramWrapper,
                paramWrapper1 -> {
                    assertEquals(7, (int) paramWrapper1.getResult(Integer.class));
                    atomicBoolean.set(true);
                });

        assertTrue(atomicBoolean.get());

    }

    /**
     * 测试简单流程前后新增节点是否有问题
     */
    @Test
    public void asyncBaseTest2(){
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_async_test/async_base_test2.fn").build();
        AsyncParamWrapper paramWrapper = new AsyncParamWrapper();
        paramWrapper.setParam(1);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        melkweg.runAsync(
                "test_namespace", "async_test_process", paramWrapper,
                param -> {
                    assertEquals(7, (int) param.getResult(Integer.class));
                    assertTrue(param.getContextParam("show_start"));
                    assertTrue(param.getContextParam("show_end"));
                    atomicBoolean.set(true);
                });
        assertTrue(atomicBoolean.get());
    }

}
