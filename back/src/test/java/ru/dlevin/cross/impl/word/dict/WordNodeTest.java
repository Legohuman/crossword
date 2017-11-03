package ru.dlevin.cross.impl.word.dict;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WordNodeTest {

    @Test
    public void testLetterSum() {
        assertLetterSum("аа", 0);
        assertLetterSum("аб", 1);
        assertLetterSum("ая", 31);
        assertLetterSum("ба", 32);
        assertLetterSum("бб", 33);
        assertLetterSum("бя", 63);
        assertLetterSum("абя", 63);
        assertLetterSum("баа", 1024);
        assertLetterSum("бааа", 32768);
        assertLetterSum("баааа", 1048576);
        assertLetterSum("бааааа", 33554432);
        assertLetterSum("баааааа", 1073741824);
        assertLetterSum("бааааааа", 34359738368L);
    }

    private void assertLetterSum(@NotNull String word, long expectedSum) {
        long sum = new WordNode(word).calculateLetterSum();
        System.out.println(sum);
        assertEquals(expectedSum, sum);
    }
}
