package org.mekweg.builder;

import org.mekweg.Mekweg;
import org.mekweg.exception.UserMekwegRuntimeError;
import org.mekweg.handle.Handler;
import org.mekweg.parsing.FnEngineDataStructureTool;
import org.mekweg.parsing.WordParticipleTool;
import org.mekweg.parsing.mark.Word;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FnMapperBuilder {

    private static final String fnFileName = ".fn";

    public static Map<String, Map<String, List<Handler>>> build(String path, Map<String, Handler> handlerDataMap) throws Exception {
        FnEngineDataStructureTool fnEngineDataStructureTool = new FnEngineDataStructureTool(handlerDataMap);
        URL url = Mekweg.class.getClassLoader().getResource(path);
        if (url == null) {
            throw new UserMekwegRuntimeError("未发现指定文件:{" + path + "}，请修正");
        }
        String abstartPath = url.getPath();
        File file = new File(url.toURI());
        Map<String, Map<String, List<Handler>>> valueMap = new HashMap<>();
        File[] fs = file.listFiles();
        if (fs != null && fs.length > 0) {
            for (File f : fs) {
                if (!f.isDirectory()) {
                    String pathNow = f.getAbsolutePath();
                    if(pathNow.endsWith(fnFileName)) {
                        List<Word> words =
                                WordParticipleTool.createWordParticipleList(pathNow.substring(abstartPath.length()));
                        valueMap.putAll(fnEngineDataStructureTool.runGrammarAnalysisTool(words));
                    }
                }
            }
        }
        return valueMap;
    }
}
