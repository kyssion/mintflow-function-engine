package org.melkweg;

import org.junit.jupiter.api.Test;
import org.melkweg.exception.UsermelkwegException;
import org.melkweg.handle.ConditionHandlerWrapper;
import org.melkweg.handle.Handler;
import org.melkweg.handle.ReorderHandler;
import org.melkweg.handle.SampleHandler;
import org.melkweg.param.ParamWrapper;
import org.melkweg.param.TreeParams;
import org.melkweg.parsing.FnEngineDataStructureTool;
import org.melkweg.parsing.WordParticipleTool;
import org.melkweg.parsing.mark.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsingTest {
    @Test
    public void melkwegTest() throws CloneNotSupportedException, UsermelkwegException {
        Map<String,Handler> dataMap= new HashMap<>();
        dataMap.put("x3",new SampleHandler("x3"));
        dataMap.put("x4", new ReorderHandler("x4") {
            @Override
            public void reorderHandlerList(List<Handler> handlers) {

            }
        });
        dataMap.put("x5",new SampleHandler("x5"));
        dataMap.put("x6",new SampleHandler("x6"));
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
        dataMap.put("x9",new SampleHandler("x9"));
        dataMap.put("x10",new SampleHandler("x10"));
        melkweg melkweg = melkweg.create(dataMap).addFnMapper("p.fn");
        ParamWrapper paramWrapper = melkweg.run("x1","x2", new ParamWrapper());
        System.out.println();
    }
}
