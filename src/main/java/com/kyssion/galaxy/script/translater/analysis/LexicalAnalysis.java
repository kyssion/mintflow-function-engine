package com.kyssion.galaxy.script.translater.analysis;

import com.kyssion.galaxy.script.translater.data.LexicalAnalysisData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LexicalAnalysis {
    public static List<List<LexicalAnalysisData>> analysis(BufferedReader reader) throws IOException {
        List<List<LexicalAnalysisData>> mapperAnalysisList =
                new ArrayList<>();
        String lineTxt;
        while ((lineTxt = reader.readLine()) != null) {

        }
        return mapperAnalysisList;
    }
}
