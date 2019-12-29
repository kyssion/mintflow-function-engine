package org.mekweg.parsing;

import org.mekweg.parsing.mark.Word;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WordParticipleTool {
    public static List<Word> createWordParticipleList(String path) throws Exception {
        InputStream fileStream = WordParticipleTool.class.getClassLoader().getResourceAsStream(path);
        if (fileStream == null) {
            throw new Exception();
        }

        BufferedReader fileBufferReader = new BufferedReader(new InputStreamReader(fileStream));
        StringBuilder itemFile = new StringBuilder();
        String line = "";
        while ((line = fileBufferReader.readLine()) != null) {
            itemFile.append(line);
        }
        List<Word> wordList = new ArrayList<>();

        return wordList;
    }
}
