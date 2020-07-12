package org.mintflow.test.asyncBaseTest;

import org.junit.Test;
import org.mintflow.MintFlow;
import org.mintflow.MintFlowTemplate;
import org.mintflow.handler.MintFlowHandlerMap;
import org.mintflow.handler.util.MintFlowHandlerMapFinder;
import org.mintflow.templateFunction.Function1;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.handler.async.reorder.AsyncReorderHandler.random_number_reorder;
import static org.mintflow.handler.async.sample.AsyncCycleSampleHandler.ASYNC_CYCLE_STR;
import static org.mintflow.handler.async.sample.AsyncReorderSampleHandler.ASYNC_REORDER_STR;
import static org.mintflow.test.asyncBaseTest.AsyncConditionTest.CAN_GO;
import static org.mintflow.test.asyncBaseTest.AsyncConditionTest.NO_GO;
import static org.mintflow.test.asyncBaseTest.AsyncCycleTest.ADD_DATA_CYCLE;
import static org.mintflow.test.asyncBaseTest.AsyncReorderTest.ADD_DATA_REORDER;

public class AsyncTempleteTest {

    @Test
    public void test(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        MintFlowHandlerMap dataMapper = MintFlowHandlerMapFinder.findHandlerDataMap(
                "org.mintflow.handler"
        );
        String itemCycle = "test1";
        String itemReorder = "test1";
        MintFlow mintFlow = MintFlow.newBuilder(dataMapper).addFnMapper("base_async_test/async_complex_test.fn").build();
        MintFlowTemplate mintFlowTemplate = MintFlowTemplate.newBuilder().addInterface(mintFlow,"org.mintflow.templateFunction").build();
        Function1 function1 = mintFlowTemplate.getTemplateFunction(Function1.class);
        function1.test(1,itemCycle,itemReorder,NO_GO,CAN_GO,NO_GO,CAN_GO,false,false,param -> {
            assertEquals(13, (int) param.getResult(Integer.class));
            StringBuilder ansCycle = new StringBuilder(itemCycle);
            StringBuilder ansReorder = new StringBuilder(itemReorder);

            int numCycle = param.getContextParam("random_number_cycle");
            while(numCycle>0){
                ansCycle.append(ADD_DATA_CYCLE);
                numCycle--;
            }
            String nowCycleItem = param.getContextParam(ASYNC_CYCLE_STR);
            assertEquals(ansCycle.toString(),nowCycleItem);

            int numReorder = param.getContextParam(random_number_reorder);
            while(numReorder>0){
                ansReorder.append(ADD_DATA_REORDER);
                numReorder--;
            }
            String nowItem =  param.getContextParam(ASYNC_REORDER_STR);
            assertEquals(ansReorder.toString(),nowItem);


            assertTrue(param.getContextParam("show_start"));
            assertTrue(param.getContextParam("show_end"));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());

    }
}
