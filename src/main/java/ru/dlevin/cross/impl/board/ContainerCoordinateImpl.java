package ru.dlevin.cross.impl.board;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.board.ContainerCoordinate;
import ru.dlevin.cross.utils.Validate;

public class ContainerCoordinateImpl implements ContainerCoordinate {

    private final int left;
    private final int top;

    public ContainerCoordinateImpl(int left, int top) {
//        Validate.argument(() -> left >= 0, "Coordinate 'left' value should be more than zero");
//        Validate.argument(() -> top >= 0, "Coordinate 'top' value should be more than zero");
        this.left = left;
        this.top = top;
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public int getTop() {
        return top;
    }

    @NotNull
    @Override
    public ContainerCoordinate withTop(int top) {
        return new ContainerCoordinateImpl(left, top);
    }

    @NotNull
    @Override
    public ContainerCoordinate withLeft(int left) {
        return new ContainerCoordinateImpl(left, top);
    }

    @NotNull
    @Override
    public ContainerCoordinate relativeTo(@NotNull ContainerCoordinate other) {
        return new ContainerCoordinateImpl(left - other.getLeft(), top - other.getTop());
    }

    @Override
    public int compareTo(@NotNull ContainerCoordinate o) {
        int result = top - o.getTop();

        if (result == 0) {
            result = left - o.getLeft();
        }
        return result;
    }

    @Override
    public String toString() {
        return "[" + left + ", " + top + "]";
    }
}
