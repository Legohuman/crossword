package ru.dlevin.cross.api.word.dict;

import org.jetbrains.annotations.NotNull;

public interface WordDictionaryFactory {

    @NotNull
    WordDictionary create();
}
