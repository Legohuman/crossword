package ru.dlevin.cross.impl.word;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LetterScores {

    private static final Map<Character, Integer> letterScores = initLetterScores();

    private static Map<Character, Integer> initLetterScores() {
        Map<Character, Integer> letterScores = new HashMap<>();

        letterScores.put('а', 1);
        letterScores.put('и', 1);
        letterScores.put('р', 2);
        letterScores.put('ш', 10);
        letterScores.put('б', 3);
        letterScores.put('й', 2);
        letterScores.put('с', 2);
        letterScores.put('щ', 10);
        letterScores.put('в', 2);
        letterScores.put('к', 2);
        letterScores.put('т', 2);
        letterScores.put('ъ', 10);
        letterScores.put('г', 3);
        letterScores.put('л', 2);
        letterScores.put('у', 3);
        letterScores.put('ы', 5);
        letterScores.put('д', 2);
        letterScores.put('м', 2);
        letterScores.put('ф', 10);
        letterScores.put('ь', 5);
        letterScores.put('е', 1);
        letterScores.put('н', 1);
        letterScores.put('х', 5);
        letterScores.put('э', 10);
        letterScores.put('ж', 5);
        letterScores.put('о', 1);
        letterScores.put('ц', 10);
        letterScores.put('ю', 10);
        letterScores.put('з', 5);
        letterScores.put('п', 2);
        letterScores.put('ч', 5);
        letterScores.put('я', 3);
        return Collections.unmodifiableMap(letterScores);
    }

    public static int letterScore(char ch) {
        return letterScores.get(ch);
    }
}



