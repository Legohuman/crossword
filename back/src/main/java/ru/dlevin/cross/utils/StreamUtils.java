package ru.dlevin.cross.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

public class StreamUtils {
    @Nullable
    public static InputStream getResourceStream(@NotNull String name) {
        return StreamUtils.class.getClassLoader().getResourceAsStream(name);
    }
}
