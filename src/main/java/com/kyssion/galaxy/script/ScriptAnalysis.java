package com.kyssion.galaxy.script;

import com.kyssion.galaxy.exception.AnalysisLexicalAnalysisException;
import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.header.StartHandler;
import com.kyssion.galaxy.param.ParamWrapper;
import com.kyssion.galaxy.script.translater.analysis.GrammaAnalysis;
import com.kyssion.galaxy.script.translater.analysis.LexicalAnalysis;
import com.kyssion.galaxy.script.translater.analysis.SemanticAnalysis;
import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;

import java.io.File;
import java.io.IOException;
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
            if (grammaAnalysis.getErrorItem() != null) {
                throw new AnalysisLexicalAnalysisException(grammaAnalysis.getErrorItem().getLineIndex() + " " +
                        grammaAnalysis.getErrorItem().getFileName());
            }
        }
        SemanticAnalysis semanticAnalysis = new SemanticAnalysis();
        semanticAnalysis.analysis(list, handleMap);
        Map<String, StartHandler> startHanderMap = semanticAnalysis.getMap();
        return startHanderMap;
    }
}
