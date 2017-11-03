package ru.dlevin.cross.api;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.board.WordContainer;
import ru.dlevin.cross.api.word.Word;

public interface WordPlacement {

    @NotNull
    Word getWord();

    @NotNull
    WordContainer getContainer();
}
