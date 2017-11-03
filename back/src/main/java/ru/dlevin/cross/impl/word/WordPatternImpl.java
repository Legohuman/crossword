package ru.dlevin.cross.impl.word;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.word.Word;
import ru.dlevin.cross.api.word.WordPattern;
import ru.dlevin.cross.api.word.WordPatternLetter;
import ru.dlevin.cross.utils.Validate;
import ru.dlevin.cross.utils.WordUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WordPatternImpl implements WordPattern {
    @NotNull
    private final List<WordPatternLetter> letters;
    @NotNull
    private final String pattern;

    public WordPatternImpl(@NotNull String pattern) {
        this.pattern = pattern;
        String processed = WordUtils.preprocessWordText(pattern);
        List<WordPatternLetter> letters = new ArrayList<>(processed.length());

        for (int i = 0; i < processed.length(); i++) {
            letters.add(new WordPatternLetterImpl(i, processed.charAt(i)));
        }
        this.letters = Collections.unmodifiableList(letters);
    }

    private WordPatternImpl(@NotNull List<WordPatternLetter> letters) {
        char[] chars = new char[letters.size()];
        for (int i = 0; i < letters.size(); i++) {
            chars[i] = letters.get(i).getValue();
        }
        this.pattern = new String(chars);
        this.letters = Collections.unmodifiableList(letters);
    }

    @Override
    public int getLength() {
        return letters.size();
    }

    @Override
    public boolean matchWord(@NotNull Word word) {
        int length = getLength();
        if (length == word.getLength()) {
            for (int i = 0; i < length; i++) {
                WordPatternLetter letter = letters.get(i);
                if (!letter.isWildcard() && word.getText().charAt(i) != letter.getValue()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @NotNull
    @Override
    public WordPattern withLetter(@NotNull WordPatternLetter letter) {
        Validate.argument(() -> letter.getIndex() < letters.size(), "Replaced letter index should be less than word pattern length");
        ArrayList<WordPatternLetter> lettersCopy = new ArrayList<>(letters);
        lettersCopy.set(letter.getIndex(), letter);
        return new WordPatternImpl(lettersCopy);
    }

    @NotNull
    @Override
    public List<WordPatternLetter> getKnownLetters() {
        return Collections.unmodifiableList(letters.stream().filter(letter -> !letter.isWildcard()).collect(Collectors.toList()));
    }

    @Override
    public boolean allLettersKnown() {
        return getKnownLetters().size() == getLength();
    }

    @Override
    public Word toWord() {
        Validate.state(this::allLettersKnown, "Pattern can not be converted to word. Not all letters are known.");
        return new WordImpl(pattern);
    }

    @Override
    public String toString() {
        return pattern;
    }
}
