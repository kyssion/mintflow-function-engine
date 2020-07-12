package org.mintflow.test.syncBaseTest;

import org.junit.Test;
import org.mintflow.MintFlow;
import org.mintflow.MintFlowTemplate;
import org.mintflow.handler.MintFlowHandlerMap;
import org.mintflow.handler.util.MintFlowHandlerMapFinder;
import org.mintflow.param.ParamWrapper;
import org.mintflow.templateFunction.Function1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.handler.async.cycle.AsyncCycleTestHandler.random_number_cycle;
import static org.mintflow.handler.async.reorder.AsyncReorderHandler.random_number_reorder;
import static org.mintflow.handler.sync.simple.CycleSampleHandler.SYNC_CYCLE_STR;
import static org.mintflow.handler.sync.simple.ReorderSampleHandler.SYNC_REORDER_STR;
import static org.mintflow.test.syncBaseTest.ConditionTest.CAN_GO;
import static org.mintflow.test.syncBaseTest.ConditionTest.NO_GO;
import static org.mintflow.test.syncBaseTest.SyncCycleTest.ADD_DATA_CYCLE;
import static org.mintflow.test.syncBaseTest.SyncReorderTest.ADD_DATA_REORDER;

public class SyncTempleteTest {

    @Test
    public void test(){
        MintFlowHandlerMap dataMapper = MintFlowHandlerMapFinder.findHandlerDataMap(
                "org.mintflow.handler"
        );
        String itemCycle = "test1";
        StringBuilder ansCycle = new StringBuilder(itemCycle);

        String itemReorder = "test1";
        StringBuilder ansReorder = new StringBuilder(itemReorder);
        MintFlow mintFlow = MintFlow.newBuilder(dataMapper).addFnMapper("base_sync_test/sync_complex_test.fn").build();
        MintFlowTemplate mintFlowTemplate = MintFlowTemplate.newBuilder().addInterface(mintFlow,"org.mintflow.templateFunction").build();
        Function1 function1 = mintFlowTemplate.getTemplateFunction(Function1.class);
        ParamWrapper paramWrapper = function1.test(1,itemCycle,itemReorder,NO_GO,CAN_GO,NO_GO,CAN_GO,false,false);
        assertEquals(13, (int) paramWrapper.getResult(Integer.class));

        int numCycle = paramWrapper.getContextParam(random_number_cycle);
        while(numCycle>0){
            ansCycle.append(ADD_DATA_CYCLE);
            numCycle--;
        }
        itemCycle = paramWrapper.getContextParam(SYNC_CYCLE_STR);
        assertEquals(ansCycle.toString(),itemCycle);

        int numReorder = paramWrapper.getContextParam(random_number_reorder);
        while(numReorder>0){
            ansReorder.append(ADD_DATA_REORDER);
            numReorder--;
        }
        itemReorder = paramWrapper.getContextParam(SYNC_REORDER_STR);
        assertEquals(ansReorder.toString(),itemReorder);

        assertTrue(paramWrapper.getContextParam("show_start"));
        assertTrue(paramWrapper.getContextParam("show_end"));
    }
}
