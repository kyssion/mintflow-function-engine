package org.mekweg.script.translater.analysis;

import org.mekweg.script.translater.data.workKeyData.LexicalAnalysisData;
import org.mekweg.script.translater.data.workKeyData.StopNoteMap;
import org.mekweg.script.translater.symbol.LexicalType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LexicalAnalysis {

    /**
     * 构建词法表
     * @param file
     * @return
     * @throws IOException
     */
    public List<LexicalAnalysisData> analysis(File file) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file))
        );
        StopNoteMap stopNoteMap = new StopNoteMap();
        String lineTxt;
        StringBuilder note = new StringBuilder();
        List<LexicalAnalysisData> analysisDataList = new ArrayList<>();
        int lineIndex = 0;
        while ((lineTxt = reader.readLine()) != null) {
            if (lineTxt.startsWith("#")) {
                continue;
            }
            for (int a = 0; a < lineTxt.length(); a++) {
                if (lineTxt.charAt(a) == ' ' || lineTxt.charAt(a) == '\n') {
                    continue;
                }
                if (stopNoteMap.isBeforeNote(lineTxt.charAt(a)) ||
                        stopNoteMap.isEndNote(lineTxt.charAt(a))) {
                    if (note.length() != 0) {
                        String value = note.toString();
                        LexicalType symbolType = LexicalType.getTypeByKeyWord(value);
                        analysisDataList.add(LexicalAnalysisData.create(value, symbolType, lineIndex + 1, file.getName()));
                        note = new StringBuilder();
                    }
                    LexicalType symbolType = LexicalType.getTypeByKeyWord(String.valueOf(lineTxt.charAt(a)));
                    analysisDataList.add(LexicalAnalysisData.create(String.valueOf(lineTxt.charAt(a)), symbolType, lineIndex + 1, file.getName()));
                } else {
                    note.append(lineTxt.charAt(a));
                }
            }
            lineIndex++;
        }
        int index = 0;
        for (LexicalAnalysisData data:analysisDataList){
            data.setIndex(index);
            index++;
        }
        return analysisDataList;
    }
}
