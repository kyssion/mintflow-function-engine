package org.melkweg.baseTest;

import org.junit.Before;
import org.junit.Test;
import org.melkweg.Melkweg;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.handler.condition.ConditionHandle1;
import org.melkweg.handler.condition.ConditionHandle2;
import org.melkweg.handler.condition.ConditionHandle3;
import org.melkweg.handler.condition.ConditionHandle4;
import org.melkweg.handler.reorder.ReorderHandler;
import org.melkweg.handler.simple.*;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.FnEngineScheduler;

import static org.junit.Assert.assertEquals;

public class ReorderTest {

    public final static String ADD_DATA="__reorder_handler";

    /**
     * 初始化
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
        mapBuilder.add("reorder_sample_handle",new ReorderSampleHandle("reorder_sample_handle"));

        mapBuilder.add("show_start_handle",new ShowStartHandle("show_start_handle"));
        mapBuilder.add("show_end_handle",new ShowEndHandle("show_end_handle"));

        mapBuilder.add("reorder_handle",new ReorderHandler("reorder_handle"));
        mapBuilder.add("reorder_sample_handle",new ReorderSampleHandle("reorder_sample_handle"));
    }

    /**
     * namespace(test_namespace){
     *     sync process(sys_test_process){
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
        paramWrapper = melkweg.runSync("test_namespace","sys_test_process",paramWrapper,new FnEngineScheduler());
        int num = paramWrapper.getContextParam("random_number");
        while(num>=0){
            ans.append(ADD_DATA);
            num--;
        }
        item = paramWrapper.getParam(String.class);
        assertEquals(ans.toString(),item);
    }
}
