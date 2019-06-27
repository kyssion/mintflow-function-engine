package com.kyssion.galaxy.test;

import com.kyssion.galaxy.exception.AnalysisNoHandleException;
import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.param.ParamWrapper;
import com.kyssion.galaxy.script.translater.analysis.GrammaAnalysis;
import com.kyssion.galaxy.script.translater.analysis.LexicalAnalysis;
import com.kyssion.galaxy.script.translater.analysis.SemanticAnalysis;
import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.test.handler.TestHandleOne;
import com.kyssion.galaxy.test.handler.TestHandlerTwo;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisTest {
    public static void main(String[] args) throws IOException, AnalysisNoHandleException {
        System.out.println(new File("").getAbsolutePath());
        File file = new File("D:/project/java-project/Galaxy/src/test/resources/x.gal");
//        File file = new File("/home/kyssion/project/java-project/galaxy/src/test/resources/x.gal");
        LexicalAnalysis lexicalAnalysis = new LexicalAnalysis();
        List<LexicalAnalysisData> list = lexicalAnalysis.analysis(file);
        System.out.println(list.size());
        GrammaAnalysis grammaAnalysis = new GrammaAnalysis();
        int a = grammaAnalysis.analysis(list);
        if (a != list.size()) {
            if (grammaAnalysis.getErrorItem() != null) {
                System.err.println(grammaAnalysis.getErrorItem().getLineIndex() + " " +
                        grammaAnalysis.getErrorItem().getFileName());
            }
        }
        SemanticAnalysis semanticAnalysis = new SemanticAnalysis();

        Map<String, Handle> map = new HashMap<>();
        Handle handle = new Handle() {
            @Override
            public ParamWrapper handle(ParamWrapper p) {
                return null;
            }
        };
        map.put("one", new TestHandleOne());
        map.put("two", new TestHandlerTwo());

        int index = semanticAnalysis.analysis(list, map);

        String name = "test1";
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.put(name);
    }
}
