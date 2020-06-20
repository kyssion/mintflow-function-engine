package org.melkweg.syncBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.melkweg.Melkweg;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.handler.sync.condition.ConditionHandle1;
import org.melkweg.handler.sync.condition.ConditionHandle2;
import org.melkweg.handler.sync.condition.ConditionHandle3;
import org.melkweg.handler.sync.condition.ConditionHandle4;
import org.melkweg.handler.sync.reorder.ReorderHandler;
import org.melkweg.handler.sync.simple.*;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.FnEngineScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.melkweg.syncBaseTest.ConditionTest.CAN_GO;
import static org.melkweg.syncBaseTest.ConditionTest.NO_GO;
import static org.melkweg.syncBaseTest.ReorderTest.ADD_DATA;

public class SyncComplexTest {
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
     *     sync process(sync_test_process){
     *         ->handle(show_start_handle)
     *         ->handle(base_test_handle1)->handle(base_test_handle2)->handle(base_test_handle3)->if(condition_handle_1){
     *             ->if(condition_handle_3){
     *                 ->handle(base_test_handle1)
     *             }elif(condition_handle_4){
     *                 ->handle(base_test_handle2)
     *             }else{
     *                 ->handle(base_test_handle3)
     *             }
     *         }elif(condition_handle_2){
     *             ->if(condition_handle_3){
     *                 ->handle(base_test_handle3)->handle(base_test_handle1)
     *             }elif(condition_handle_4){
     *                 ->reorder(reorder_handle){
     *                     ->handle(reorder_sample_handle)
     *                 }
     *             }else{
     *                 ->handle(base_test_handle3)->handle(base_test_handle3)
     *             }
     *         }else{
     *                 ->handle(base_test_handle3)->handle(base_test_handle3)->handle(base_test_handle1)
     *         }->handle(base_test_handle1)->handle(base_test_handle2)->handle(base_test_handle3)->handle(show_end_handle)
     *     }
     * }
     */

    @Test
    public void complexTest(){
        String item = "test1";
        StringBuilder ans = new StringBuilder(item + ADD_DATA);
        ParamWrapper paramWrapper= new ParamWrapper();
        paramWrapper.setParam(1);
        paramWrapper.setParam(item);
        paramWrapper.setContextParam("condition_1",NO_GO);
        paramWrapper.setContextParam("condition_2",CAN_GO);
        paramWrapper.setContextParam("condition_3",NO_GO);
        paramWrapper.setContextParam("condition_4",CAN_GO);
        paramWrapper.setContextParam("show_start",false);
        paramWrapper.setContextParam("show_end",false);
        Melkweg melkweg = Melkweg.newBuilder(mapBuilder.build()).addFnMapper("base_sync_test/sync_complex_test.fn").build();
        paramWrapper = melkweg.runSync("test_namespace","sync_test_process",paramWrapper,new FnEngineScheduler());
        assertEquals(13, (int) paramWrapper.getResult(Integer.class));

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
