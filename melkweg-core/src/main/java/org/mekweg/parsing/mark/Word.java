package org.mekweg.parsing.mark;

public class Word {
    private String name;
    private String value;
    private WordType type;
    private int lineNum;
    private int wordStartNum;

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getWordStartNum() {
        return wordStartNum;
    }

    public void setWordStartNum(int wordStartNum) {
        this.wordStartNum = wordStartNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public WordType getType() {
        return type;
    }

    public void setType(WordType type) {
        this.type = type;
    }

    public static Word create(Word word,int lineNum,int wordStartNum){
        Word newWord = new Word();
        newWord.setName(word.name);
        newWord.setValue(word.value);
        newWord.setType(word.type);
        newWord.setLineNum(lineNum);
        newWord.setWordStartNum(wordStartNum);
        return newWord;
    }
}
