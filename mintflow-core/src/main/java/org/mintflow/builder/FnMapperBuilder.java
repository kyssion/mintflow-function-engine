package org.mintflow.builder;

import org.mintflow.handler.HandlerDataMap;
import org.mintflow.handler.MintFlowHandlerMap;
import org.mintflow.parsing.FnEngineDataStructureTool;
import org.mintflow.parsing.WordParticipleTool;
import org.mintflow.parsing.mark.Word;

import java.util.List;

public class FnMapperBuilder {

    public static HandlerDataMap build(MintFlowHandlerMap handlerDataMap, String...paths) throws Exception {
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
