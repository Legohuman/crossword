package ru.dlevin.cross.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtils {

    @NotNull
    public static <K, V extends Comparable<V>> List<K> sortedKeys(@NotNull Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(comparator);
        return list.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
