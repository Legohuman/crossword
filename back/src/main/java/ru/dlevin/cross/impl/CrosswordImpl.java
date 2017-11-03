package ru.dlevin.cross.impl;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.Crossword;
import ru.dlevin.cross.api.WordPlacement;
import ru.dlevin.cross.api.board.ContainerOrientation;
import ru.dlevin.cross.api.board.CrosswordBoard;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class CrosswordImpl implements Crossword {
    @NotNull
    private final CrosswordBoard board;
    @NotNull
    private final SortedSet<WordPlacement> placements;

    public CrosswordImpl(@NotNull CrosswordBoard board, @NotNull SortedSet<WordPlacement> placements) {
        this.board = board;
        this.placements = placements;
    }

    @NotNull
    @Override
    public CrosswordBoard getBoard() {
        return board;
    }

    @Override
    public boolean isFinished() {
        return board.getContainers().size() == getPlacements().size();
    }

    @NotNull
    @Override
    public SortedSet<WordPlacement> getPlacements() {
        return Collections.unmodifiableSortedSet(placements);
    }

    @NotNull
    @Override
    public SortedSet<WordPlacement> getPlacements(@NotNull ContainerOrientation orientation) {
        SortedSet<WordPlacement> filtered = new TreeSet<>();
        for (WordPlacement placement : placements) {
            if (placement.getContainer().getOrientation().equals(orientation)) {
                filtered.add(placement);
            }
        }
        return filtered;
    }
}
