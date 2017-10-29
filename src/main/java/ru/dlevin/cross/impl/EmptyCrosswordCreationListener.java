package ru.dlevin.cross.impl;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.CrosswordCreationContext;
import ru.dlevin.cross.api.CrosswordCreationListener;
import ru.dlevin.cross.api.WordPlacement;

public class EmptyCrosswordCreationListener implements CrosswordCreationListener {
    @Override
    public void onBeforeStart(@NotNull CrosswordCreationContext context) {

    }

    @Override
    public void onAfterStart(@NotNull CrosswordCreationContext context) {

    }

    @Override
    public void onPlacementModified(@NotNull CrosswordCreationContext context, @NotNull WordPlacement wordPlacement) {

    }

    @Override
    public void onSolutionFound(@NotNull CrosswordCreationContext context) {

    }

    @Override
    public void onFinish(@NotNull CrosswordCreationContext context) {

    }
}
