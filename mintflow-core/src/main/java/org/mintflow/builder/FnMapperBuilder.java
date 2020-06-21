package org.mintflow.builder;

import org.mintflow.handle.FnHandler;
import org.mintflow.handle.HandleType;
import org.mintflow.handle.HandlerDataMap;
import org.mintflow.handle.util.MintFlowHandleMapBuilder;
import org.mintflow.parsing.FnEngineDataStructureTool;
import org.mintflow.parsing.WordParticipleTool;
import org.mintflow.parsing.mark.Word;
import org.mintflow.process.ProcessType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FnMapperBuilder {

    public static HandlerDataMap build(MintFlowHandleMapBuilder.Mapper handlerDataMap, String...paths) throws Exception {
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
