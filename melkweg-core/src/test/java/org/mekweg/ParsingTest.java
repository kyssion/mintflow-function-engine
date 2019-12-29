package org.mekweg;

import org.junit.jupiter.api.Test;
import org.mekweg.parsing.WordParticipleTool;
import org.mekweg.parsing.mark.Word;

import java.util.List;

public class ParsingTest {
    @Test
    public void readFileAndParsingTest() throws Exception {
        List<Word> list = WordParticipleTool.createWordParticipleList("p.galaxy");
        System.out.println();
    }
}
