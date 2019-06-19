package com.kyssion.galaxy.script.translater.data.workKeyData;

import java.util.HashSet;
import java.util.Set;

public class StopNoteMap {

    private static Set<Character> beforeNoteSet = new HashSet<>();
    private static Set<Character> endNoteSet = new HashSet<>();
    static {
        beforeNoteSet.add('(');
        endNoteSet.add(')');
        beforeNoteSet.add('{');
        endNoteSet.add('}');
        beforeNoteSet.add('-');
        endNoteSet.add('>');
        endNoteSet.add(';');
        endNoteSet.add(':');
    }

    public static boolean isBeforeNote(Character c){
        return beforeNoteSet.contains(c);
    }

    public static boolean isEndNote(Character c){
        return endNoteSet.contains(c);
    }
}
