package ru.dlevin.cross.api;

import org.jetbrains.annotations.NotNull;

public interface CrosswordCreationListener {

    void onBeforeStart(@NotNull CrosswordCreationContext context);

    void onAfterStart(@NotNull CrosswordCreationContext context);

    void onPlacementModified(@NotNull CrosswordCreationContext context, @NotNull WordPlacement wordPlacement);

    void onSolutionFound(@NotNull CrosswordCreationContext context);

    void onFinish(@NotNull CrosswordCreationContext context);
}
