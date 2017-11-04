package ru.dlevin.cross.utils;

import ru.dlevin.cross.api.board.WordContainer;

import java.util.function.BiFunction;

public class Functions {

    public static final BiFunction<WordContainer, Integer, Integer> incrementOrOne = (container, count) -> count == null ? 1 : count + 1;

    public static final BiFunction<WordContainer, Integer, Integer> sameOrZero = (container, count) -> count == null ? 0 : count;
}
