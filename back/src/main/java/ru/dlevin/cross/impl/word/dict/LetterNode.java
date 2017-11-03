package ru.dlevin.cross.impl.word.dict;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: Legohuman
 * Date: 19/10/15
 */
class LetterNode {
    @Nullable
    LetterNode next;
    @NotNull
    final WordNode word;
    final char letter;

    LetterNode(@NotNull WordNode word, char letter) {
        this.word = word;
        this.letter = letter;
    }
}
