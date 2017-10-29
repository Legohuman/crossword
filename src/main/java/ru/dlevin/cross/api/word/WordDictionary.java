package ru.dlevin.cross.api.word;

import org.jetbrains.annotations.NotNull;

public interface WordDictionary extends ReadOnlyWordDictionary {

    boolean addWord(@NotNull Word word);

    boolean removeWord(@NotNull Word word);

}
