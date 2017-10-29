package ru.dlevin.cross.impl.board;

import ru.dlevin.cross.api.board.ContainerOrientation;
import ru.dlevin.cross.api.board.CrosswordBoard;
import ru.dlevin.cross.api.board.CrosswordBoardBuilder;
import ru.dlevin.cross.api.board.WordContainer;
import ru.dlevin.cross.utils.Validate;

import java.util.ArrayList;
import java.util.List;

public class CrosswordBoardBuilderImpl implements CrosswordBoardBuilder {

    private final List<WordContainer> containers = new ArrayList<>();
    private boolean finished = false;

    @Override
    public CrosswordBoardBuilder addHorizontalContainer(int top, int left, int length) {
        containers.add(new WordContainerImpl(new ContainerCoordinateImpl(left, top), length, ContainerOrientation.horizontal));
        return this;
    }

    @Override
    public CrosswordBoardBuilder addVerticalContainer(int top, int left, int length) {
        containers.add(new WordContainerImpl(new ContainerCoordinateImpl(left, top), length, ContainerOrientation.vertical));
        return this;
    }

    @Override
    public CrosswordBoard build() {
        Validate.state(() -> !finished, "Builder build method was already invoked");
        finished = true;
        return new CrosswordBoardImpl(containers);
    }
}
