package ru.dlevin.cross.impl.word;

import ru.dlevin.cross.api.word.WordPattern;
import ru.dlevin.cross.api.word.WordPatternLetter;
import ru.dlevin.cross.utils.Validate;

public class WordPatternLetterImpl implements WordPatternLetter {

    private final int index;
    private final char value;

    WordPatternLetterImpl(int index, char value) {
        Validate.argument(() -> index >= 0, "Letter index should be greater than zero");
        this.index = index;
        this.value = value;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public boolean isWildcard() {
        return value == WordPattern.wildcard;
    }

    @Override
    public char getValue() {
        return value;
    }
}
