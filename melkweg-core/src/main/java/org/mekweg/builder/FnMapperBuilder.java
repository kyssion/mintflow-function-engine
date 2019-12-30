package org.mekweg.builder;

import org.mekweg.handle.Handler;
import org.mekweg.parsing.FnEngineDataStructureTool;
import org.mekweg.parsing.WordParticipleTool;
import org.mekweg.parsing.mark.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FnMapperBuilder {

    public static Map<String, Map<String, List<Handler>>> build(String path,Map<String, Handler> handlerDataMap) throws Exception {
        FnEngineDataStructureTool fnEngineDataStructureTool = new FnEngineDataStructureTool(handlerDataMap);
        //TODO 发现所有的文件

        Map<String, Map<String, List<Handler>>> valueMap = new HashMap<>();
        List<String> filePathList = new ArrayList<>();
        for (String filePath : filePathList) {
            List<Word> words = WordParticipleTool.createWordParticipleList(filePath);
            valueMap.putAll(fnEngineDataStructureTool.runGrammarAnalysisTool(words));
        }
        return valueMap;
    }
}
