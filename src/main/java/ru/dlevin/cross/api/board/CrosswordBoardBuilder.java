package ru.dlevin.cross.api.board;

public interface CrosswordBoardBuilder {

    CrosswordBoardBuilder addHorizontalContainer(int top, int left, int length);

    CrosswordBoardBuilder addVerticalContainer(int top, int left, int length);

    CrosswordBoard build();
}
