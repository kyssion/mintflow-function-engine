package org.melkweg.baseTest;


import org.junit.Test;
import org.melkweg.Melkweg;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.handler.condition.ConditionHandle1;
import org.melkweg.handler.condition.ConditionHandle2;
import org.melkweg.handler.condition.ConditionHandle3;
import org.melkweg.handler.condition.ConditionHandle4;
import org.melkweg.handler.simple.*;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.FnEngineScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class BaseTest {

    @Test
    public void sysnBaseTest1(){
        MelkwegHandleMapBuilder mapBuilder = new MelkwegHandleMapBuilder();
        mapBuilder.add("base_test_handle1",new BaseTestHandle1("base_test_handle1"));
        mapBuilder.add("base_test_handle2",new BaseTestHandle2("base_test_handle2"));
        mapBuilder.add("base_test_handle3",new BaseTestHandle3("base_test_handle3"));
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_test/sysn_base_test1.fn").build();
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setParam(1);
        paramWrapper = melkweg.runSync("test_namespace","sys_test_process", paramWrapper,new FnEngineScheduler());
        assertEquals(7, (int) paramWrapper.getResult(Integer.class));
    }

    @Test
    public void sysnBaseTest2(){
        MelkwegHandleMapBuilder mapBuilder = new MelkwegHandleMapBuilder();
        mapBuilder.add("base_test_handle1",new BaseTestHandle1("base_test_handle1"));
        mapBuilder.add("base_test_handle2",new BaseTestHandle2("base_test_handle2"));
        mapBuilder.add("base_test_handle3",new BaseTestHandle3("base_test_handle3"));
        mapBuilder.add("show_start_handle",new ShowStartHandle("show_start_handle"));
        mapBuilder.add("show_end_handle",new ShowEndHandle("show_end_handle"));
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_test/sysn_base_test2.fn").build();
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setParam(1);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        paramWrapper = melkweg.runSync("test_namespace","sys_test_process", paramWrapper,new FnEngineScheduler());

        assertEquals(7, (int) paramWrapper.getResult(Integer.class));
        assertTrue(paramWrapper.getContextParam("show_start"));
        assertTrue(paramWrapper.getContextParam("show_end"));
    }


    @Test
    public void syncConditionTest(){
        MelkwegHandleMapBuilder mapBuilder = new MelkwegHandleMapBuilder();

        mapBuilder.add("condition_handle_1",new ConditionHandle1("condition_handle_1"));
        mapBuilder.add("condition_handle_2",new ConditionHandle2("condition_handle_2"));
        mapBuilder.add("condition_handle_3",new ConditionHandle3("condition_handle_3"));
        mapBuilder.add("condition_handle_4",new ConditionHandle4("condition_handle_4"));


        mapBuilder.add("base_test_handle1",new BaseTestHandle1("base_test_handle1"));
        mapBuilder.add("base_test_handle2",new BaseTestHandle2("base_test_handle2"));
        mapBuilder.add("base_test_handle3",new BaseTestHandle3("base_test_handle3"));
    }
}
