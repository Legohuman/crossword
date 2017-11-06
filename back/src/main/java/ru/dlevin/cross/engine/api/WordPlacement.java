package ru.dlevin.cross.engine.api;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.engine.api.board.WordContainer;
import ru.dlevin.cross.engine.api.word.Word;

public interface WordPlacement {

    @NotNull
    Word getWord();

    @NotNull
    WordContainer getContainer();
}
