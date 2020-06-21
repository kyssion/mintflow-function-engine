package org.melkweg.test.asyncBaseTest;

import org.junit.Test;
import org.melkweg.Melkweg;
import org.melkweg.MelkwegTemplate;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.handle.util.MelkwegHandleMapFinder;
import org.melkweg.param.ParamWrapper;
import org.melkweg.templateFunction.Function1;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.melkweg.test.syncBaseTest.ConditionTest.CAN_GO;
import static org.melkweg.test.syncBaseTest.ConditionTest.NO_GO;
import static org.melkweg.test.syncBaseTest.ReorderTest.ADD_DATA;

public class AsyncTempleteTest {

    @Test
    public void test(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        MelkwegHandleMapBuilder.Mapper dataMapper = MelkwegHandleMapFinder.findHandleDataMap(
                "org.melkweg.handler"
        );
        Melkweg melkweg = Melkweg.newBuilder(dataMapper).addFnMapper("base_async_test/async_complex_test.fn").build();
        MelkwegTemplate melkwegTemplate = MelkwegTemplate.newBuilder().addInterface(melkweg,"org.melkweg.templateFunction").build();
        Function1 function1 = melkwegTemplate.getTemplateFunction(Function1.class);
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
