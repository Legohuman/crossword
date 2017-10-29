package ru.dlevin.cross.api.word;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface ReadOnlyWordDictionary {

    int size();

    int size(int letterCount);

    boolean isCharAccepted(char ch);

    @NotNull
    Iterator<Word> search(@NotNull WordPattern pattern);
}
