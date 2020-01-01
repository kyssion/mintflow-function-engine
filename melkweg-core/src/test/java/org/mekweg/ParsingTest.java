package org.mekweg;

import org.junit.jupiter.api.Test;
import org.mekweg.handle.ConditionHandlerWrapper;
import org.mekweg.handle.Handler;
import org.mekweg.handle.ReorderHandler;
import org.mekweg.handle.SampleHandler;
import org.mekweg.param.ParamWrapper;
import org.mekweg.parsing.FnEngineDataStructureTool;
import org.mekweg.parsing.WordParticipleTool;
import org.mekweg.parsing.mark.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsingTest {
    @Test
    public void readFileAndParsingTest() throws Exception {
        List<Word> list = WordParticipleTool.createWordParticipleList("p.galaxy");
        System.out.println();
    }


    @Test
    public void FnEngineDataStructureTest() throws Exception {
        List<Word> list = WordParticipleTool.createWordParticipleList("p.galaxy");
        System.out.println();
        Map<String,Handler> dataMap= new HashMap<>();
        dataMap.put("x3",new SampleHandler());
        dataMap.put("x4", new ReorderHandler() {
            @Override
            public void reorderHandlerList(List<Handler> handlers) {

            }
        });
        dataMap.put("x5",new SampleHandler());
        dataMap.put("x6",new SampleHandler());
        dataMap.put("x7", new ConditionHandlerWrapper.ConditionHander() {
            @Override
            public boolean condition(ParamWrapper params) {
                return false;
            }
        });
        dataMap.put("x8", new ConditionHandlerWrapper.ConditionHander() {
            @Override
            public boolean condition(ParamWrapper params) {
                return false;
            }
        });
        dataMap.put("x9",new SampleHandler());
        dataMap.put("x10",new SampleHandler());
        Map<String, Map<String, List<Handler>>> map =
                new FnEngineDataStructureTool(dataMap).runGrammarAnalysisTool(list);
        System.out.println();
    }

    @Test
    public void test3() throws CloneNotSupportedException {
        class One implements Cloneable{
            List<String> iiii = new ArrayList<>();
            @Override
            public Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        }
        One one1 = new One();
        one1.iiii.add("sdf");
        One one2 = (One) one1.clone();
        one2.iiii.add("123");
        System.out.println();
    }
}
