package ru.dlevin.cross.engine.api;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.engine.api.board.CrosswordBoard;
import ru.dlevin.cross.engine.api.word.dict.ReadOnlyWordDictionary;

public interface CrosswordCreationContext {

    @NotNull
    CrosswordBoard getBoard();

    @NotNull
    ReadOnlyWordDictionary getDictionary();
}
