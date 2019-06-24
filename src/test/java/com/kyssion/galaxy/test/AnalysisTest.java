package com.kyssion.galaxy.test;

import com.kyssion.galaxy.script.translater.analysis.GrammaAnalysis;
import com.kyssion.galaxy.script.translater.analysis.LexicalAnalysis;
import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;

import java.io.*;
import java.util.List;

public class AnalysisTest {
    public static void main(String[] args) throws IOException {
        System.out.println(new File("").getAbsolutePath());
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(new FileInputStream("D:/project/java-project/Galaxy/src/test/resources/x.gal"))
//        );
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("/home/kyssion/project/java-project/galaxy/src/test/resources/x.gal"))
        );
        LexicalAnalysis lexicalAnalysis = new LexicalAnalysis();
        List<List<LexicalAnalysisData>> lists = lexicalAnalysis.analysis(reader);
        System.out.println();
        GrammaAnalysis grammaAnalysis = new GrammaAnalysis();
        for (List<LexicalAnalysisData> list: lists) {
            int a = grammaAnalysis.analysis(list);
            System.out.println(a);
        }
    }
}
