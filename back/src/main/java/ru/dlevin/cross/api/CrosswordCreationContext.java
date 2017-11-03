package ru.dlevin.cross.api;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.board.CrosswordBoard;
import ru.dlevin.cross.api.word.dict.ReadOnlyWordDictionary;

public interface CrosswordCreationContext {

    @NotNull
    CrosswordBoard getBoard();

    @NotNull
    ReadOnlyWordDictionary getDictionary();
}
