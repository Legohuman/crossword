package ru.dlevin.cross.api.board;

import org.jetbrains.annotations.NotNull;

public interface ContainerCoordinate extends Comparable<ContainerCoordinate> {
    int getLeft();

    int getTop();

    @NotNull
    ContainerCoordinate withTop(int top);

    @NotNull
    ContainerCoordinate withLeft(int left);

    @NotNull
    ContainerCoordinate relativeTo(@NotNull ContainerCoordinate other);
}
