package ru.dlevin.cross.engine.api.board;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface WordContainer extends Comparable<WordContainer> {

    @NotNull
    ContainerCoordinate getStartCoordinate();

    @NotNull
    ContainerCoordinate getEndCoordinate();

    int getLength();

    @NotNull
    ContainerOrientation getOrientation();

    @Nullable
    ContainerCoordinate getIntersection(@NotNull WordContainer other);

    int toCharIndex(@NotNull ContainerCoordinate coordinate);
}
