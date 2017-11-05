package ru.dlevin.cross.impl;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.CrosswordCreationContext;
import ru.dlevin.cross.api.CrosswordCreationListener;
import ru.dlevin.cross.api.WordPlacement;

import java.util.List;

public class EmptyCrosswordCreationListener implements CrosswordCreationListener {
    @Override
    public void onStart(@NotNull CrosswordCreationContext context) {

    }

    @Override
    public void onIteration(@NotNull CrosswordCreationContext context) {

    }

    @Override
    public void onSolutionFound(@NotNull List<WordPlacement> placements) {

    }

    @Override
    public void onFinish(@NotNull CrosswordCreationContext context) {

    }
}
