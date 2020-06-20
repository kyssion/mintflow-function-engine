package org.melkweg.baseTest;

import org.junit.Before;
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

public class ConditionTest {

    public static final String CAN_GO="can_go";
    public static final String NO_GO="no_go";
    /**
     * 测试
     */
    MelkwegHandleMapBuilder mapBuilder;
    @Before
    public void initMapDate(){
        mapBuilder = new MelkwegHandleMapBuilder();

        mapBuilder.add("condition_handle_1",new ConditionHandle1("condition_handle_1"));
        mapBuilder.add("condition_handle_2",new ConditionHandle2("condition_handle_2"));
        mapBuilder.add("condition_handle_3",new ConditionHandle3("condition_handle_3"));
        mapBuilder.add("condition_handle_4",new ConditionHandle4("condition_handle_4"));


        mapBuilder.add("base_test_handle1",new BaseTestHandle1("base_test_handle1"));
        mapBuilder.add("base_test_handle2",new BaseTestHandle2("base_test_handle2"));
        mapBuilder.add("base_test_handle3",new BaseTestHandle3("base_test_handle3"));

        mapBuilder.add("show_start_handle",new ShowStartHandle("show_start_handle"));
        mapBuilder.add("show_end_handle",new ShowEndHandle("show_end_handle"));
    }

    /**
     *
     * 测试脚本：
     * namespace(test_namespace){
     *     sync process(sys_test_process){
     *         ->if(condition_handle_1){
     *             ->if(condition_handle_3){
     *                 ->handle(base_test_handle1)
     *             }->elif(condition_handle_4){
     *                 ->handle(base_test_handle2)
     *             }->else{
     *                 ->handle(base_test_handle3)
     *             }
     *         }->elif(condition_handle_2){
     *             if(condition_handle_3){
     *                 ->handle(base_test_handle3)->handle(base_test_handle1)
     *             }elif(condition_handle_4){
     *                 ->handle(base_test_handle3)->handle(base_test_handle2)
     *             }else{
     *                 ->handle(base_test_handle3)->handle(base_test_handle3)
     *             }
     *         }->else{
     *                 ->handle(base_test_handle3)->handle(base_test_handle3)->->handle(base_test_handle1)
     *         }
     *     }
     * }
     */

    /**
     * 测试 走 c1 c3 逻辑
     */
    @Test
    public void syncConditionTest1(){
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",CAN_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",CAN_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setParam(1);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_test/sysn_condition_base_test1.fn").build();
        paramWrapper = melkweg.runSync("test_namespace","sys_test_process",paramWrapper,new FnEngineScheduler());
        assertEquals(2,(int)paramWrapper.getParam(Integer.class));
    }
    /**
     * 测试 走 c1 c4 逻辑
     */
    @Test
    public void syncConditionTest2(){
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",CAN_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setParam(1);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_test/sysn_condition_base_test1.fn").build();
        paramWrapper = melkweg.runSync("test_namespace","sys_test_process",paramWrapper,new FnEngineScheduler());
        assertEquals(3,(int)paramWrapper.getParam(Integer.class));
    }
    /**
     * 测试 走 c1 else 逻辑
     */
    @Test
    public void syncConditionTest3(){
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",CAN_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",NO_GO);
        paramWrapper.setParam(1);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_test/sysn_condition_base_test1.fn").build();
        paramWrapper = melkweg.runSync("test_namespace","sys_test_process",paramWrapper,new FnEngineScheduler());
        assertEquals(4,(int)paramWrapper.getParam(Integer.class));
    }


    /**
     * 测试 走 c2 c3 逻辑
     */
    @Test
    public void syncConditionTest4(){
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",CAN_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setParam(1);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_test/sysn_condition_base_test1.fn").build();
        paramWrapper = melkweg.runSync("test_namespace","sys_test_process",paramWrapper,new FnEngineScheduler());
        assertEquals(5,(int)paramWrapper.getParam(Integer.class));
    }
    /**
     * 测试 走 c2 c4 逻辑
     */
    @Test
    public void syncConditionTest5(){
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setParam(1);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_test/sysn_condition_base_test1.fn").build();
        paramWrapper = melkweg.runSync("test_namespace","sys_test_process",paramWrapper,new FnEngineScheduler());
        assertEquals(6,(int)paramWrapper.getParam(Integer.class));
    }

    /**
     * 测试 走 c2 else 逻辑
     */
    @Test
    public void syncConditionTest6(){
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",NO_GO);
        paramWrapper.setParam(1);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_test/sysn_condition_base_test1.fn").build();
        paramWrapper = melkweg.runSync("test_namespace","sys_test_process",paramWrapper,new FnEngineScheduler());
        assertEquals(7,(int)paramWrapper.getParam(Integer.class));
    }

    /**
     * 测试 走 else else 逻辑
     */
    @Test
    public void syncConditionTest7(){
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",NO_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",NO_GO);
        paramWrapper.setParam(1);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_test/sysn_condition_base_test1.fn").build();
        paramWrapper = melkweg.runSync("test_namespace","sys_test_process",paramWrapper,new FnEngineScheduler());
        assertEquals(8,(int)paramWrapper.getParam(Integer.class));
    }


    /**
     * 测试包括开头和结尾的逻辑
     */
    @Test
    public void syncCondtionTest8(){
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",NO_GO);
        paramWrapper.setParam(1);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_test/sysn_condition_base_test2.fn").build();
        paramWrapper = melkweg.runSync("test_namespace","sys_test_process",paramWrapper,new FnEngineScheduler());
        assertEquals(7,(int)paramWrapper.getParam(Integer.class));
        assertTrue(paramWrapper.getContextParam("show_start"));
        assertTrue(paramWrapper.getContextParam("show_end"));
    }
}
