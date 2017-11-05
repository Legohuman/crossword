package ru.dlevin.cross.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class JacksonUtils {

    @Nullable
    public static <T> T readValue(@NotNull ObjectMapper mapper, @Nullable byte[] bytes, @NotNull TypeReference<T> typeRef) throws IOException {
        return bytes == null ? null : mapper.readValue(bytes, typeRef);
    }
}
