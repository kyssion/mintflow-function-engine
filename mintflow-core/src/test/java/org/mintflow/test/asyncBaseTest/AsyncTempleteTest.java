package org.mintflow.test.asyncBaseTest;

import org.junit.Test;
import org.mintflow.MintFlow;
import org.mintflow.MintFlowTemplate;
import org.mintflow.handle.util.MintFlowHandleMapBuilder;
import org.mintflow.handle.util.MintFlowHandleMapFinder;
import org.mintflow.param.ParamWrapper;
import org.mintflow.templateFunction.Function1;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mintflow.test.syncBaseTest.ConditionTest.CAN_GO;
import static org.mintflow.test.syncBaseTest.ConditionTest.NO_GO;
import static org.mintflow.test.syncBaseTest.ReorderTest.ADD_DATA;

public class AsyncTempleteTest {

    @Test
    public void test(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        MintFlowHandleMapBuilder.Mapper dataMapper = MintFlowHandleMapFinder.findHandleDataMap(
                "org.mintflow.handler"
        );
        MintFlow mintFlow = MintFlow.newBuilder(dataMapper).addFnMapper("base_async_test/async_complex_test.fn").build();
        MintFlowTemplate mintFlowTemplate = MintFlowTemplate.newBuilder().addInterface(mintFlow,"org.mintflow.templateFunction").build();
        Function1 function1 = mintFlowTemplate.getTemplateFunction(Function1.class);
        function1.test(1,"item",NO_GO,CAN_GO,NO_GO,CAN_GO,false,false,param -> {
            assertEquals(13, (int) param.getResult(Integer.class));
            StringBuilder ans = new StringBuilder("item"+ ADD_DATA);
            int num = param.getContextParam("random_number");
            while(num>=0){
                ans.append(ADD_DATA);
                num--;
            }
            String item = param.getParam(String.class);
            assertEquals(ans.toString(),item);

            assertTrue(param.getContextParam("show_start"));
            assertTrue(param.getContextParam("show_end"));
            atomicBoolean.set(true);
        });
        assertTrue(atomicBoolean.get());

    }
}
