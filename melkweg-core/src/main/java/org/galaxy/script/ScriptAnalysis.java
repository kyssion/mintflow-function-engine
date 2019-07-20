package org.galaxy.script;

import org.galaxy.handle.Handle;
import org.galaxy.handle.StartHandler;
import org.galaxy.script.translater.analysis.GrammaAnalysis;
import org.galaxy.script.translater.analysis.LexicalAnalysis;
import org.galaxy.script.translater.analysis.SemanticAnalysis;
import org.galaxy.script.translater.data.error.ErrorInfoData;
import org.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptAnalysis {
    public static Map<String, StartHandler> analysis(File file, Map<String,Handle> handleMap) throws IOException {
        LexicalAnalysis lexicalAnalysis = new LexicalAnalysis();
        List<LexicalAnalysisData> list = lexicalAnalysis.analysis(file);
        System.out.println(list.size());
        GrammaAnalysis grammaAnalysis = new GrammaAnalysis();
        int a = grammaAnalysis.analysis(list);

        if (a != list.size()) {
            if (grammaAnalysis.getTryItemDuque().size() != 0) {
                while (!grammaAnalysis.getTryItemDuque().isEmpty()) {
                    ErrorInfoData data = grammaAnalysis.getTryItemDuque().removeLast();
                    System.err.println("第"+data.getLineId()+"行" +
                            " "+"第" + data.getWordId()+"个字符"+"" +
                            " " + "字符:"+data.getValue() + " " +
                            "可能存在问题" + " " + data.getType().getDesc());
                }
            }
            return new HashMap<>();
        }

        SemanticAnalysis semanticAnalysis = new SemanticAnalysis();
        int i = semanticAnalysis.analysis(list,handleMap);
        Map<String, StartHandler> map = semanticAnalysis.getStartHandleMap();
        return map;
    }
}
