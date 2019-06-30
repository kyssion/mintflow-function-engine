package com.kyssion.galaxy.test;

import com.kyssion.galaxy.exception.AnalysisNoHandleException;
import com.kyssion.galaxy.script.translater.analysis.GrammaAnalysis;
import com.kyssion.galaxy.script.translater.analysis.LexicalAnalysis;
import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;

import java.io.*;
import java.util.List;

public class AnalysisTest {
    public static void main(String[] args) throws IOException, AnalysisNoHandleException {
        System.out.println(new File("").getAbsolutePath());
        File file = new File("D:/project/java-project/Galaxy/src/test/resources/x.galaxy");
//        File file = new File("/home/kyssion/project/java-project/galaxy/src/test/resources/x.galaxy");
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
    }
}
