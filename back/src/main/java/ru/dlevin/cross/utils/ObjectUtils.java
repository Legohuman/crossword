package ru.dlevin.cross.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ObjectUtils {

    @SuppressWarnings("ConstantConditions")
    @NotNull
    public static <T> T notNull(@Nullable T obj) {
        Validate.argument(() -> obj != null, "Object is expected to be not null");
        return obj;
    }
}
