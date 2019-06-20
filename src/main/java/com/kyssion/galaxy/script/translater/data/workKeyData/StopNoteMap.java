package com.kyssion.galaxy.script.translater.data.workKeyData;

import java.util.HashSet;
import java.util.Set;

public class StopNoteMap {

    private  Set<Character> beforeNoteSet = new HashSet<>();
    private  Set<Character> endNoteSet = new HashSet<>();
    public StopNoteMap(){
        beforeNoteSet.add('(');
        endNoteSet.add(')');
        beforeNoteSet.add('{');
        endNoteSet.add('}');
        beforeNoteSet.add('-');
        endNoteSet.add('>');
        endNoteSet.add(';');
        endNoteSet.add(':');
    }

    public  boolean isBeforeNote(Character c){
        return beforeNoteSet.contains(c);
    }

    public  boolean isEndNote(Character c){
        return endNoteSet.contains(c);
    }
}
