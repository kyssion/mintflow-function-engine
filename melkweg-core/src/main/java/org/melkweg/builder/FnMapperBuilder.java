package org.melkweg.builder;

import org.melkweg.handle.FnHandler;
import org.melkweg.handle.HandleType;
import org.melkweg.handle.HandlerDataMap;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.parsing.FnEngineDataStructureTool;
import org.melkweg.parsing.WordParticipleTool;
import org.melkweg.parsing.mark.Word;
import org.melkweg.process.ProcessType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FnMapperBuilder {

    public static HandlerDataMap build(MelkwegHandleMapBuilder.Mapper handlerDataMap, String...paths) throws Exception {
        FnEngineDataStructureTool fnEngineDataStructureTool = new FnEngineDataStructureTool(handlerDataMap);
        HandlerDataMap dataMap = new HandlerDataMap();
        for(String path : paths){
                List<Word> words =
                        WordParticipleTool.createWordParticipleListByFile(path);
            dataMap.addAll(fnEngineDataStructureTool.runGrammarAnalysisTool(words));
        }
        return dataMap;
    }
}
