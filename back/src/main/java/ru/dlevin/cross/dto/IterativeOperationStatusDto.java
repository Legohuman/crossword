package ru.dlevin.cross.dto;

import org.jetbrains.annotations.NotNull;

public class IterativeOperationStatusDto {
    @NotNull
    private final IterativeOperationStatus status;
    private final long iterations;

    public IterativeOperationStatusDto(@NotNull IterativeOperationStatus status, long iterations) {
        this.status = status;
        this.iterations = iterations;
    }

    @NotNull
    public IterativeOperationStatus getStatus() {
        return status;
    }

    public long getIterations() {
        return iterations;
    }
}
