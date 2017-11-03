package ru.dlevin.cross.impl.board;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dlevin.cross.api.board.ContainerCoordinate;
import ru.dlevin.cross.api.board.ContainerOrientation;
import ru.dlevin.cross.api.board.WordContainer;

public class WordContainerImpl implements WordContainer {

    @NotNull
    private final ContainerCoordinate coordinate;
    private final int length;
    @NotNull
    private final ContainerOrientation orientation;

    public WordContainerImpl(@NotNull ContainerCoordinate coordinate, int length, @NotNull ContainerOrientation orientation) {
        this.coordinate = coordinate;
        this.length = length;
        this.orientation = orientation;
    }

    @NotNull
    @Override
    public ContainerCoordinate getStartCoordinate() {
        return coordinate;
    }

    @NotNull
    @Override
    public ContainerCoordinate getEndCoordinate() {
        return orientation.equals(ContainerOrientation.horizontal) ?
                coordinate.withLeft(coordinate.getLeft() + getLength() - 1) :
                coordinate.withTop(coordinate.getTop() + getLength() - 1);
    }

    @Override
    public int getLength() {
        return length;
    }

    @NotNull
    @Override
    public ContainerOrientation getOrientation() {
        return orientation;
    }

    @Nullable
    @Override
    public ContainerCoordinate getIntersection(@NotNull WordContainer other) {
        ContainerOrientation otherOrientation = other.getOrientation();
        ContainerCoordinate start = coordinate;
        ContainerCoordinate end = getEndCoordinate();
        ContainerCoordinate otherStart = other.getStartCoordinate();
        ContainerCoordinate otherEnd = other.getEndCoordinate();

        if (orientation.equals(ContainerOrientation.horizontal)) {
            if (otherOrientation == ContainerOrientation.vertical &&
                    otherStart.getLeft() >= start.getLeft() &&
                    otherStart.getLeft() <= end.getLeft() &&
                    otherStart.getTop() <= start.getTop() &&
                    otherEnd.getTop() >= start.getTop()) {
                return new ContainerCoordinateImpl(otherStart.getLeft(), start.getTop());
            }
            return null;
        } else {
            if (otherOrientation == ContainerOrientation.horizontal &&
                    otherStart.getTop() >= start.getTop() &&
                    otherStart.getTop() <= end.getTop() &&
                    otherStart.getLeft() <= start.getLeft() &&
                    otherEnd.getLeft() >= start.getLeft()) {
                return new ContainerCoordinateImpl(start.getLeft(), otherStart.getTop());
            }
            return null;
        }
    }

    @Override
    public int toCharIndex(@NotNull ContainerCoordinate coordinate) {
        ContainerCoordinate relativeCoordinate = coordinate.relativeTo(this.coordinate);
        return orientation == ContainerOrientation.horizontal ?
                relativeCoordinate.getLeft() :
                relativeCoordinate.getTop();
    }

    @Override
    public int compareTo(@NotNull WordContainer o) {
        int result = orientation.ordinal() - o.getOrientation().ordinal();

        if (result == 0) {
            result = coordinate.compareTo(o.getStartCoordinate());
        }
        return result;
    }

    @Override
    public String toString() {
        return coordinate +
                ", " + orientation + ", " +
                length + " chars";
    }
}
