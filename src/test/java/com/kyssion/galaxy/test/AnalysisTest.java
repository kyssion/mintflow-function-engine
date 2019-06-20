package com.kyssion.galaxy.test;

import com.kyssion.galaxy.script.translater.analysis.GrammaAnalysis;
import com.kyssion.galaxy.script.translater.analysis.LexicalAnalysis;
import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.error.ZRule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnalysisTest {
    public static void main(String[] args) throws IOException {
        System.out.println(new File("").getAbsolutePath());
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("D:/project/java-project/Galaxy/src/test/resources/x.gal"))
        );
        List<List<LexicalAnalysisData>> lists = LexicalAnalysis.analysis(reader);
        System.out.println();
        List<ZRule> list = new ArrayList<>();
        for(List<LexicalAnalysisData> dataList:lists){
            list.add(GrammaAnalysis.analysis(dataList));
        }
        System.out.println(list);
    }
}
