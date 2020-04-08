package org.melkweg;

import org.junit.jupiter.api.Test;
import org.melkweg.exception.UserMelkwegException;
import org.melkweg.handle.ConditionHandlerWrapper;
import org.melkweg.handle.Handler;
import org.melkweg.handle.ReorderHandler;
import org.melkweg.handle.SampleHandler;
import org.melkweg.handle.util.MelkwegHandleDataMapFinder;
import org.melkweg.param.ParamWrapper;
import org.melkweg.templateFunction.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsingTest {

    /**
     * Test automation build capabilities
     * Testing basic capabilities
     * @throws CloneNotSupportedException
     * @throws UserMelkwegException
     */
    @Test
    public void melkwegBaseTest() throws CloneNotSupportedException, UserMelkwegException {
        Map<String,Handler> dataMap= new HashMap<>();
        dataMap.put("x3", new SampleHandler("x3") {
            @Override
            public ParamWrapper handle(ParamWrapper params) {
                return params;
            }
        });
        dataMap.put("x4", new ReorderHandler("x4") {
            @Override
            public void reorderHandlerList(List<Handler> handlers) {

            }
        });
        dataMap.put("x5", new SampleHandler("x5") {
            @Override
            public ParamWrapper handle(ParamWrapper params) {
                return params;
            }
        });
        dataMap.put("x6", new SampleHandler("x6") {
            @Override
            public ParamWrapper handle(ParamWrapper params) {
                return params;
            }
        });
        dataMap.put("x7", new ConditionHandlerWrapper.ConditionHander("x7") {
            @Override
            public boolean condition(ParamWrapper params) {
                return false;
            }
        });
        dataMap.put("x8", new ConditionHandlerWrapper.ConditionHander("x8") {
            @Override
            public boolean condition(ParamWrapper params) {
                return false;
            }
        });
        dataMap.put("x9", new SampleHandler("x9") {
            @Override
            public ParamWrapper handle(ParamWrapper params) {
                return params;
            }
        });
        dataMap.put("x10", new SampleHandler("x10") {
            @Override
            public ParamWrapper handle(ParamWrapper params) {
                return params;
            }
        });
        Melkweg melkweg = Melkweg.newBuilder(dataMap).addFnMapper("p.fn").build();
        ParamWrapper paramWrapper = melkweg.run("x1","x2", new ParamWrapper());
    }

    /**
     * Testing basic capabilities
     */
    @Test
    public void melkwegUpdateTest(){
        Map<String,Handler> dataMap = MelkwegHandleDataMapFinder.findHandleDataMap(
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
