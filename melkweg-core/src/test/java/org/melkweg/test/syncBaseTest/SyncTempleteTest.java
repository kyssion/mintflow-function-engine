package org.melkweg.test.syncBaseTest;

import org.junit.Test;
import org.melkweg.Melkweg;
import org.melkweg.MelkwegTemplate;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.handle.util.MelkwegHandleMapFinder;
import org.melkweg.param.ParamWrapper;
import org.melkweg.templateFunction.Function1;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.melkweg.test.syncBaseTest.ConditionTest.CAN_GO;
import static org.melkweg.test.syncBaseTest.ConditionTest.NO_GO;
import static org.melkweg.test.syncBaseTest.ReorderTest.ADD_DATA;

public class SyncTempleteTest {

    @Test
    public void test(){
        Map<HandleType,Map<String, FnHandler>> dataMap = MelkwegHandleMapFinder.findHandleDataMap(
                "org.melkweg.handler"
        );
        Melkweg melkweg = Melkweg.newBuilder(dataMap).addFnMapper("base_sync_test/sync_complex_test.fn").build();
        MelkwegTemplate melkwegTemplate = MelkwegTemplate.newBuilder().addInterface(melkweg,"org.melkweg.templateFunction").build();
        Function1 function1 = melkwegTemplate.getTemplateFunction(Function1.class);
        ParamWrapper paramWrapper = function1.test(1,"item",NO_GO,CAN_GO,NO_GO,CAN_GO,false,false);

        assertEquals(13, (int) paramWrapper.getResult(Integer.class));
        StringBuilder ans = new StringBuilder("item"+ ADD_DATA);
        int num = paramWrapper.getContextParam("random_number");
        while(num>=0){
            ans.append(ADD_DATA);
            num--;
        }
        String item = paramWrapper.getParam(String.class);
        assertEquals(ans.toString(),item);

        assertTrue(paramWrapper.getContextParam("show_start"));
        assertTrue(paramWrapper.getContextParam("show_end"));
    }
}
