package org.mintflow.builder;

import org.mintflow.handle.HandlerDataMap;
import org.mintflow.handle.MintFlowHandlerMapper;
import org.mintflow.parsing.FnEngineDataStructureTool;
import org.mintflow.parsing.WordParticipleTool;
import org.mintflow.parsing.mark.Word;

import java.util.List;

public class FnMapperBuilder {

    public static HandlerDataMap build(MintFlowHandlerMapper handlerDataMap, String...paths) throws Exception {
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
