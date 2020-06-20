package org.melkweg.builder;

import org.melkweg.handle.FnHandler;
import org.melkweg.parsing.FnEngineDataStructureTool;
import org.melkweg.parsing.WordParticipleTool;
import org.melkweg.parsing.mark.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FnMapperBuilder {

    public static Map<String, Map<String, List<FnHandler>>> build(Map<String, FnHandler> handlerDataMap, String...paths) throws Exception {
        FnEngineDataStructureTool fnEngineDataStructureTool = new FnEngineDataStructureTool(handlerDataMap);
        Map<String, Map<String, List<FnHandler>>> valueMap = new HashMap<>();
        for(String path : paths){
                List<Word> words =
                        WordParticipleTool.createWordParticipleListByFile(path);
            valueMap.putAll(fnEngineDataStructureTool.runGrammarAnalysisTool(words));
        }
        return valueMap;
    }
}
