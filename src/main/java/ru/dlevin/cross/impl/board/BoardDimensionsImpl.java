package ru.dlevin.cross.impl.board;

import ru.dlevin.cross.api.board.BoardDimensions;

public class BoardDimensionsImpl implements BoardDimensions {

    private final int width;
    private final int height;

    public BoardDimensionsImpl(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
