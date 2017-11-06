package ru.dlevin.cross.engine.impl.board;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.engine.api.board.ContainerOrientation;
import ru.dlevin.cross.engine.api.board.CrosswordBoard;
import ru.dlevin.cross.engine.api.board.CrosswordBoardBuilder;
import ru.dlevin.cross.engine.api.board.WordContainer;
import ru.dlevin.cross.utils.Validate;

import java.util.ArrayList;
import java.util.List;

public class CrosswordBoardBuilderImpl implements CrosswordBoardBuilder {

    private final List<WordContainer> containers = new ArrayList<>();
    private boolean finished = false;

    @NotNull
    @Override
    public CrosswordBoardBuilder addHorizontalContainer(int left, int top, int length) {
        validateNotFinished();
        containers.add(new WordContainerImpl(new ContainerCoordinateImpl(left, top), length, ContainerOrientation.horizontal));
        return this;
    }

    @NotNull
    @Override
    public CrosswordBoardBuilder addVerticalContainer(int left, int top, int length) {
        validateNotFinished();
        containers.add(new WordContainerImpl(new ContainerCoordinateImpl(left, top), length, ContainerOrientation.vertical));
        return this;
    }

    @NotNull
    @Override
    public CrosswordBoard build() {
        validateNotFinished();
        finished = true;
        return new CrosswordBoardImpl(containers);
    }

    private void validateNotFinished() {
        Validate.state(() -> !finished, "Builder build method was already invoked");
    }
}
