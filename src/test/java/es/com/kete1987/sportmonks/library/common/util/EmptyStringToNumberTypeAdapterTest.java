package es.com.kete1987.sportmonks.library.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EmptyStringToNumberTypeAdapterTest {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(int.class, new EmptyStringToNumberTypeAdapter())
            .registerTypeAdapter(Integer.class, new EmptyStringToNumberTypeAdapter())
            .registerTypeAdapter(Long.class, EmptyStringToNumberTypeAdapter.forLong())
            .create();

    private static class IntHolder {
        Integer value;
    }

    private static class LongHolder {
        Long value;
    }

    static Stream<Arguments> integerParsingCases() {
        return Stream.of(
                Arguments.of("{\"value\": \"\"}", null),
                Arguments.of("{\"value\": null}", null),
                Arguments.of("{\"value\": 42}", 42),
                Arguments.of("{\"value\": \"42\"}", 42),
                Arguments.of("{}", null)
        );
    }

    @ParameterizedTest
    @MethodSource("integerParsingCases")
    void parsesIntegerField(String json, Integer expected) {
        IntHolder result = gson.fromJson(json, IntHolder.class);
        assertEquals(expected, result.value);
    }

    static Stream<Arguments> longParsingCases() {
        return Stream.of(
                Arguments.of("{\"value\": \"\"}", null),
                Arguments.of("{\"value\": null}", null),
                Arguments.of("{\"value\": 9000000000}", 9_000_000_000L),
                Arguments.of("{\"value\": \"9000000000\"}", 9_000_000_000L),
                Arguments.of("{\"value\": 12.0}", 12L)
        );
    }

    @ParameterizedTest
    @MethodSource("longParsingCases")
    void parsesLongField(String json, Long expected) {
        LongHolder result = gson.fromJson(json, LongHolder.class);
        assertEquals(expected, result.value);
    }
}
