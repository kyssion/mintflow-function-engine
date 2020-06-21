package org.mintflow.test.syncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mintflow.MintFlow;
import org.mintflow.handle.util.MintFlowHandleMapBuilder;
import org.mintflow.handler.sync.condition.ConditionHandler1;
import org.mintflow.handler.sync.condition.ConditionHandler2;
import org.mintflow.handler.sync.condition.ConditionHandler3;
import org.mintflow.handler.sync.condition.ConditionHandler4;
import org.mintflow.handler.sync.simple.*;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.sync.SyncFnEngineSyncScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.test.BaseTestUtil.NAME_SPACE;
import static org.mintflow.test.BaseTestUtil.SYNC_PROCESS_NAME;

public class ConditionTest {

    public static final String CAN_GO="can_go";
    public static final String NO_GO="no_go";
    /**
     * 测试
     */
    MintFlowHandleMapBuilder mapBuilder;
    @Before
    public void initMapDate(){
        mapBuilder = new MintFlowHandleMapBuilder();

        mapBuilder.put("condition_handle_1",new ConditionHandler1("condition_handle_1"));
        mapBuilder.put("condition_handle_2",new ConditionHandler2("condition_handle_2"));
        mapBuilder.put("condition_handle_3",new ConditionHandler3("condition_handle_3"));
        mapBuilder.put("condition_handle_4",new ConditionHandler4("condition_handle_4"));


        mapBuilder.put("base_test_handle1",new BaseTestHandler1("base_test_handle1"));
        mapBuilder.put("base_test_handle2",new BaseTestHandler2("base_test_handle2"));
        mapBuilder.put("base_test_handle3",new BaseTestHandler3("base_test_handle3"));

        mapBuilder.put("show_start_handle",new ShowStartHandler("show_start_handle"));
        mapBuilder.put("show_end_handle",new ShowEndHandler("show_end_handle"));
    }

    /**
     *
     * 测试脚本：
     * namespace(test_namespace){
     *     sync process(sync_test_process){
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
        MintFlow MintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_condition_base_test1.fn").build();
        paramWrapper = MintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
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
        MintFlow MintFlow = org.mintflow.MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_condition_base_test1.fn").build();
        paramWrapper = MintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
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
        MintFlow MintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_condition_base_test1.fn").build();
        paramWrapper = MintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
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
        MintFlow MintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_condition_base_test1.fn").build();
        paramWrapper = MintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
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
        MintFlow MintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_condition_base_test1.fn").build();
        paramWrapper = MintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
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
        MintFlow MintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_condition_base_test1.fn").build();
        paramWrapper = MintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
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
        MintFlow MintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_condition_base_test1.fn").build();
        paramWrapper = MintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
        assertEquals(8,(int)paramWrapper.getParam(Integer.class));
    }


    /**
     * 测试包括开头和结尾的逻辑
     */
    @Test
    public void syncConditionTest8(){
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",NO_GO);
        paramWrapper.setParam(1);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        MintFlow MintFlow = MintFlow.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_condition_base_test2.fn").build();
        paramWrapper = MintFlow.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
        assertEquals(7,(int)paramWrapper.getParam(Integer.class));
        assertTrue(paramWrapper.getContextParam("show_start"));
        assertTrue(paramWrapper.getContextParam("show_end"));
    }
}
