package org.mekweg;

import org.junit.jupiter.api.Test;
import org.mekweg.exception.UserMekwegException;
import org.mekweg.handle.ConditionHandlerWrapper;
import org.mekweg.handle.Handler;
import org.mekweg.handle.ReorderHandler;
import org.mekweg.handle.SampleHandler;
import org.mekweg.param.ParamWrapper;
import org.mekweg.param.TreeParams;
import org.mekweg.parsing.FnEngineDataStructureTool;
import org.mekweg.parsing.WordParticipleTool;
import org.mekweg.parsing.mark.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsingTest {
    @Test
    public void melkwegTest() throws CloneNotSupportedException, UserMekwegException {
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
        Mekweg mekweg = Mekweg.create(dataMap).addFnMapper("p.fn");
        ParamWrapper paramWrapper = mekweg.run("x1","x2", new ParamWrapper());
        System.out.println();
    }
}
