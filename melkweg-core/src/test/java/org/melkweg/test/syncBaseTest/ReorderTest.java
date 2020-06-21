package org.melkweg.test.syncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.melkweg.Melkweg;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.handler.sync.condition.ConditionHandler1;
import org.melkweg.handler.sync.condition.ConditionHandler2;
import org.melkweg.handler.sync.condition.ConditionHandler3;
import org.melkweg.handler.sync.condition.ConditionHandler4;
import org.melkweg.handler.sync.reorder.ReorderHandler;
import org.melkweg.handler.sync.simple.*;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.sync.SyncFnEngineSyncScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.melkweg.test.BaseTestUtil.NAME_SPACE;
import static org.melkweg.test.BaseTestUtil.SYNC_PROCESS_NAME;

public class ReorderTest {

    public final static String ADD_DATA="__reorder_handler";

    /**
     * 初始化
     */
    MelkwegHandleMapBuilder mapBuilder;

    @Before
    public void initMapDate(){
        mapBuilder = new MelkwegHandleMapBuilder();

        mapBuilder.put("condition_handle_1",new ConditionHandler1("condition_handle_1"));
        mapBuilder.put("condition_handle_2",new ConditionHandler2("condition_handle_2"));
        mapBuilder.put("condition_handle_3",new ConditionHandler3("condition_handle_3"));
        mapBuilder.put("condition_handle_4",new ConditionHandler4("condition_handle_4"));


        mapBuilder.put("base_test_handle1",new BaseTestHandler1("base_test_handle1"));
        mapBuilder.put("base_test_handle2",new BaseTestHandler2("base_test_handle2"));
        mapBuilder.put("base_test_handle3",new BaseTestHandler3("base_test_handle3"));
        mapBuilder.put("reorder_sample_handle",new ReorderSampleHandler("reorder_sample_handle"));

        mapBuilder.put("show_start_handle",new ShowStartHandler("show_start_handle"));
        mapBuilder.put("show_end_handle",new ShowEndHandler("show_end_handle"));

        mapBuilder.put("reorder_handle",new ReorderHandler("reorder_handle"));
        mapBuilder.put("reorder_sample_handle",new ReorderSampleHandler("reorder_sample_handle"));
    }

    /**
     * namespace(test_namespace){
     *     sync process(sync_test_process){
     *         ->reorder(reorder_handle){
     *             ->handle(reorder_sample_handle)
     *         }
     *     }
     * }
     */
    @Test
    public void reorderTest1(){
        String item = "test1";
        StringBuilder ans = new StringBuilder(item + ADD_DATA);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setParam(item);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_reorder_test1.fn").build();
        paramWrapper = melkweg.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
        int num = paramWrapper.getContextParam("random_number");
        while(num>=0){
            ans.append(ADD_DATA);
            num--;
        }
        item = paramWrapper.getParam(String.class);
        assertEquals(ans.toString(),item);
    }

    @Test
    public void reorderTest2(){
        String item = "test1";
        StringBuilder ans = new StringBuilder(item + ADD_DATA);
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setParam(item);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_reorder_test2.fn").build();
        paramWrapper = melkweg.runSync(NAME_SPACE,SYNC_PROCESS_NAME,paramWrapper,new SyncFnEngineSyncScheduler());
        int num = paramWrapper.getContextParam("random_number");
        while(num>=0){
            ans.append(ADD_DATA);
            num--;
        }
        item = paramWrapper.getParam(String.class);
        assertEquals(ans.toString(),item);
        assertTrue(paramWrapper.getContextParam("show_start"));
        assertTrue(paramWrapper.getContextParam("show_end"));
    }

}
