package ru.dlevin.cross.api;

import org.jetbrains.annotations.NotNull;

public interface CrosswordCreator {

    void create(@NotNull CrosswordCreationContext context, @NotNull CrosswordCreationListener listener);
}
