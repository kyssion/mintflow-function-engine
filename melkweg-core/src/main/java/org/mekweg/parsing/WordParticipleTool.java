package org.mekweg.parsing;

import org.mekweg.exception.ParsingRuntimeError;
import org.mekweg.exception.UserMekwegRuntimeError;
import org.mekweg.parsing.mark.KeyworkItem;
import org.mekweg.parsing.mark.SymbolItem;
import org.mekweg.parsing.mark.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordParticipleTool {
    public static List<Word> createWordParticipleListByFile(String path) throws Exception {
        InputStream fileStream = WordParticipleTool.class.getClassLoader().getResourceAsStream(path);
        if (fileStream == null) {
            throw new UserMekwegRuntimeError("");
        }
        return createWordParticipleList(fileStream);
    }

    public static List<Word> createWordParticipleList(String code) throws Exception {
        return createWordParticipleList(new ByteArrayInputStream(code.getBytes()));
    }

    public static List<Word> createWordParticipleList(InputStream codeInput) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(codeInput));
        String itemFile = "";
        int line = 0;
        List<Word> wordList = new ArrayList<>();
        while ((itemFile = bufferedReader.readLine()) != null) {
            StringBuilder keyWord = new StringBuilder();
            for (int a = 0; a < itemFile.length(); a++) {
                char i = itemFile.charAt(a);
                if (i == ' ' || i == '\n' || i == '\t') {
                    continue;
                }
                if (KeyworkItem.isNameStartKey(i)) {
                    int b = a + 1;
                    for (; b < itemFile.length(); b++) {
                        if (KeyworkItem.isNameEndKey(itemFile.charAt(b))) {
                            break;
                        }
                    }
                    wordList.add(Word.create(KeyworkItem.getKeyWorkItem("("), line, a));
                    wordList.add(new SymbolItem(itemFile.substring(a + 1, b), line, a + 1));
                    if (b != itemFile.length()) {
                        wordList.add(Word.create(KeyworkItem.getKeyWorkItem(")"), line, b));
                    }
                    keyWord = new StringBuilder();
                    a = b;
                } else {
                    keyWord.append(i);
                    Word word = KeyworkItem.getKeyWorkItem(keyWord.toString());
                    if (word != null) {
                        Word newWord = Word.create(word, line, a + 1 - word.getValue().length());
                        wordList.add(newWord);
                        keyWord = new StringBuilder();
                    }
                }
            }
            if(keyWord.length()!=0){
                Word word = new Word();
                word.setWordStartNum(itemFile.length()-keyWord.length());
                word.setLineNum(line);
                word.setValue(keyWord.toString());
                throw new ParsingRuntimeError("此处关键字出现异常，存在非法字符:{"+word.getValue()+"}",word);
            }
            line++;
        }
        return wordList;
    }
}
