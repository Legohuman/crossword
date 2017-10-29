package ru.dlevin.cross.impl.word;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.word.Word;
import ru.dlevin.cross.utils.Validate;
import ru.dlevin.cross.utils.WordUtils;

public class WordImpl implements Word {
    @NotNull
    private final String text;

    public WordImpl(@NotNull String text) {
        this.text = WordUtils.preprocessWordText(text);
    }

    @NotNull
    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getLength() {
        return text.length();
    }

    @Override
    public int getLetterScore(int letterIndex) {
        Validate.argument(() -> letterIndex >= 0 && letterIndex < text.length(), "Invalid letter index, value should be greater than zero and less than word length");
        return LetterScores.letterScore(text.charAt(letterIndex));
    }

    @Override
    public int compareTo(@NotNull Word o) {
        if (this == o) return 0;

        return text.compareTo(o.getText());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordImpl word = (WordImpl) o;

        return text.equals(word.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return text;
    }
}
