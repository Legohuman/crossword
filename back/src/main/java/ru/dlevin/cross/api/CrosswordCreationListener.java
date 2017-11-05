package ru.dlevin.cross.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CrosswordCreationListener {

    void onStart(@NotNull CrosswordCreationContext context);

    void onIteration(@NotNull CrosswordCreationContext context);

    void onSolutionFound(@NotNull List<WordPlacement> placements);

    void onFinish(@NotNull CrosswordCreationContext context);
}
