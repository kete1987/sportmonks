package es.com.kete1987.sportmonks.library.common.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelCollectionsTest {

    @Test
    void unmodifiable_returnsNullWhenListIsNull() {
        assertNull(ModelCollections.unmodifiable(null));
    }

    @Test
    void unmodifiable_returnsUnmodifiableViewWhenListIsNonNull() {
        List<String> source = Arrays.asList("a", "b", "c");

        List<String> result = ModelCollections.unmodifiable(source);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("a", result.get(0));
        assertThrows(UnsupportedOperationException.class, () -> result.add("d"),
                "Result should be unmodifiable");
    }

    @Test
    void unmodifiable_emptyListReturnsEmptyUnmodifiableView() {
        List<String> result = ModelCollections.unmodifiable(List.of());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
