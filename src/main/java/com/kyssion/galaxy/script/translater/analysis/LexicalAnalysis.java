package com.kyssion.galaxy.script.translater.analysis;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.data.workKeyData.StopNoteMap;
import com.kyssion.galaxy.script.translater.symbol.LexicalType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LexicalAnalysis {

    /**
     * 构建词法表
     * @param reader
     * @return
     * @throws IOException
     */
    public List<List<LexicalAnalysisData>> analysis(BufferedReader reader) throws IOException {
        StopNoteMap stopNoteMap = new StopNoteMap();
        List<List<LexicalAnalysisData>> mapperAnalysisList =
                new ArrayList<>();
        String lineTxt;
        StringBuilder note = new StringBuilder();
        List<LexicalAnalysisData> analysisDataList = new ArrayList<>();
        while ((lineTxt = reader.readLine()) != null) {
            if (analysisDataList.size() > 0 && lineTxt.startsWith("namespace")) {
                mapperAnalysisList.add(analysisDataList);
                analysisDataList = new ArrayList<>();
            }
            if(lineTxt.startsWith("#")){
                continue;
            }
            for (int a = 0; a < lineTxt.length(); a++) {
                if(lineTxt.charAt(a)==' '||lineTxt.charAt(a)=='\n'){
                    continue;
                }
                if (stopNoteMap.isBeforeNote(lineTxt.charAt(a)) ||
                        stopNoteMap.isEndNote(lineTxt.charAt(a))) {
                    if(note.length()!=0) {
                        String value = note.toString();
                        LexicalType symbolType = LexicalType.getTypeByKeyWord(value);
                        analysisDataList.add(LexicalAnalysisData.create(value, a, symbolType));
                        note = new StringBuilder();
                    }
                    LexicalType symbolType = LexicalType.getTypeByKeyWord(String.valueOf(lineTxt.charAt(a)));
                    analysisDataList.add(LexicalAnalysisData.create(String.valueOf(lineTxt.charAt(a)), a, symbolType));
                } else {
                    note.append(lineTxt.charAt(a));
                }
            }
        }
        if(analysisDataList.size()>0){
            mapperAnalysisList.add(analysisDataList);
        }
        return mapperAnalysisList;
    }
}
