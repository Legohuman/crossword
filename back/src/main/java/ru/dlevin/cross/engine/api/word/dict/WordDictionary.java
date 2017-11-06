package ru.dlevin.cross.engine.api.word.dict;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.engine.api.word.Word;

public interface WordDictionary extends ReadOnlyWordDictionary {

    boolean addWord(@NotNull Word word);

    boolean removeWord(@NotNull Word word);

}
