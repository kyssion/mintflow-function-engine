package com.kyssion.galaxy.test;

import com.kyssion.galaxy.exception.AnalysisNoHandleException;
import com.kyssion.galaxy.script.translater.analysis.GrammaAnalysis;
import com.kyssion.galaxy.script.translater.analysis.LexicalAnalysis;
import com.kyssion.galaxy.script.translater.data.error.ErrorInfoData;
import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;

import java.io.*;
import java.util.List;

public class AnalysisTest {
    public static void main(String[] args) throws IOException, AnalysisNoHandleException {
        System.out.println(new File("").getAbsolutePath());
        File file = new File("D:/project/java-project/Galaxy/src/test/resources/x.galaxy");
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
        }
    }
}
