package ru.dlevin.cross.api.word.dict;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.word.Word;
import ru.dlevin.cross.api.word.WordPattern;

import java.util.Iterator;

public interface ReadOnlyWordDictionary {

    int size();

    int size(int letterCount);

    boolean isCharAccepted(char ch);

    @NotNull
    Iterator<Word> search(@NotNull WordPattern pattern);
}
