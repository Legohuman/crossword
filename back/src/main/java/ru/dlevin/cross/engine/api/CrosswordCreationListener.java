package ru.dlevin.cross.engine.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CrosswordCreationListener {

    void onStart();

    void onIteration();

    void onSolutionFound(@NotNull List<WordPlacement> placements);

    void onFinish();
}
