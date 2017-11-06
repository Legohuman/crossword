package ru.dlevin.cross.engine.impl;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.engine.api.CrosswordCreationListener;
import ru.dlevin.cross.engine.api.WordPlacement;

import java.util.List;

public class EmptyCrosswordCreationListener implements CrosswordCreationListener {
    @Override
    public void onStart() {

    }

    @Override
    public void onIteration() {

    }

    @Override
    public void onSolutionFound(@NotNull List<WordPlacement> placements) {

    }

    @Override
    public void onFinish() {

    }
}
