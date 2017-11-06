package ru.dlevin.cross.engine.api.word.dict;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.engine.api.word.Word;
import ru.dlevin.cross.engine.api.word.WordPattern;

import java.util.Iterator;

public interface ReadOnlyWordDictionary {

    int size();

    int size(int letterCount);

    @NotNull
    Iterator<Word> search(@NotNull WordPattern pattern);
}
