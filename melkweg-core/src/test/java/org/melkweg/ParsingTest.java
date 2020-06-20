package org.melkweg;

import org.melkweg.handle.*;
import org.melkweg.handle.util.MelkwegHandleMapFinder;
import org.melkweg.templateFunction.*;

import java.util.Map;

public class ParsingTest {

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
