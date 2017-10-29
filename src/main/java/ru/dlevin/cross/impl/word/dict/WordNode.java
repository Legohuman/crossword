package ru.dlevin.cross.impl.word.dict;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.word.Word;
import ru.dlevin.cross.impl.word.WordImpl;
import ru.dlevin.cross.utils.WordUtils;

/**
 * User: Legohuman
 * Date: 19/10/15
 */
public class WordNode implements Comparable<WordNode> {
    @NotNull
    private final Word word;
    @NotNull
    private final LetterNode[] letters;
    private final long letterSum;

    WordNode(@NotNull String word) {
        this.word = new WordImpl(word);
        this.letters = new LetterNode[word.length()];
        for (int i = 0; i < letters.length; i++) {
            LetterNode letterNode = new LetterNode(this, word.charAt(i));
            letters[i] = letterNode;
        }
        letterSum = calculateLetterSum();
    }

    /**
     * Package local for tests only
     */
    long calculateLetterSum() {
        String text = word.getText();
        long sum = 0;
        long base = 1;
        for (int i = 0; i < text.length(); i++) {
            sum += base * WordUtils.relativeCharIndex(text.charAt(text.length() - 1 - i));
            base *= WordDictionaryImpl.dictionarySpan;
        }
        return sum;
    }

    long letterSum() {
        return letterSum;
    }

    Word word() {
        return word;
    }

    LetterNode letter(int i) {
        return letters[i];
    }

    @Override
    public int compareTo(@NotNull WordNode o) {
        if (this == o) return 0;

        return word.compareTo(o.word);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordNode wordNode = (WordNode) o;

        return word.equals(wordNode.word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }
}
