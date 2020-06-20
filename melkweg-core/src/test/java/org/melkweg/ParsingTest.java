package org.melkweg;

import org.melkweg.exception.UserMelkwegException;
import org.melkweg.handle.*;
import org.melkweg.handle.sync.SyncReorderFnHandler;
import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.handle.util.MelkwegHandleMapFinder;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.FnEngineScheduler;
import org.melkweg.templateFunction.*;

import java.util.List;
import java.util.Map;

public class ParsingTest {

    /**
     * Test automation build capabilities
     * Testing basic capabilities
     * @throws CloneNotSupportedException
     * @throws UserMelkwegException
     */

    /**
     * Testing basic capabilities
     */
    public void melkwegUpdateTest(){
        Map<HandleType,Map<String, FnHandler>> dataMap = MelkwegHandleMapFinder.findHandleDataMap(
                "org.melkweg.handler"
        );
        Melkweg melkweg = Melkweg.newBuilder(dataMap).addFnMapper("test.fn").build();
        MelkwegTemplate melkwegTemplate = MelkwegTemplate.newBuilder().addInterface(melkweg,"org.melkweg.templateFunction").build();
        Function1 function1 = melkwegTemplate.getTemplateFunction(Function1.class);
        Function2 function2 = melkwegTemplate.getTemplateFunction(Function2.class);
        System.out.println(function1.test("x1","x2"));
        System.out.println(function2.getName("123"));
    }
}
