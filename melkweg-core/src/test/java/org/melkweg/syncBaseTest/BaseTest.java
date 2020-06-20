package org.melkweg.syncBaseTest;


import org.junit.Before;
import org.junit.Test;
import org.melkweg.Melkweg;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.handler.sync.simple.*;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.FnEngineScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class BaseTest {


    MelkwegHandleMapBuilder mapBuilder;

    @Before
    public void initMaperData(){
        mapBuilder = new MelkwegHandleMapBuilder();
        mapBuilder.add("base_test_handle1",new BaseTestHandle1("base_test_handle1"));
        mapBuilder.add("base_test_handle2",new BaseTestHandle2("base_test_handle2"));
        mapBuilder.add("base_test_handle3",new BaseTestHandle3("base_test_handle3"));

        mapBuilder.add("show_start_handle",new ShowStartHandle("show_start_handle"));
        mapBuilder.add("show_end_handle",new ShowEndHandle("show_end_handle"));
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
    public void syncBaseTest1(){
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_base_test1.fn").build();
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setParam(1);
        paramWrapper = melkweg.runSync("test_namespace","sync_test_process", paramWrapper,new FnEngineScheduler());
        assertEquals(7, (int) paramWrapper.getResult(Integer.class));
    }

    /**
     * 测试简单流程前后新增节点是否有问题
     */
    @Test
    public void syncBaseTest2(){
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_base_test2.fn").build();
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setParam(1);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        paramWrapper = melkweg.runSync("test_namespace","sync_test_process", paramWrapper,new FnEngineScheduler());

        assertEquals(7, (int) paramWrapper.getResult(Integer.class));
        assertTrue(paramWrapper.getContextParam("show_start"));
        assertTrue(paramWrapper.getContextParam("show_end"));
    }

}
