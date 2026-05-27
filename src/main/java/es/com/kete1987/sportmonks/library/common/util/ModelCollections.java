package es.com.kete1987.sportmonks.library.common.util;

import java.util.Collections;
import java.util.List;

/**
 * Utility helpers for safely exposing internal List fields from model classes.
 * Avoids repeated null-check ternaries and prevents callers from mutating internal state.
 */
public final class ModelCollections {

    private ModelCollections() {
        // utility class
    }

    /**
     * Returns an unmodifiable view of the given list, or {@code null} if the list itself is null.
     * <p>Use in model getters: {@code return ModelCollections.unmodifiable(myList);}</p>
     *
     * @param list the internal list (may be null when the API field was absent)
     * @param <T>  element type
     * @return an unmodifiable view, or null
     */
    public static <T> List<T> unmodifiable(List<T> list) {
        return list == null ? null : Collections.unmodifiableList(list);
    }
}
