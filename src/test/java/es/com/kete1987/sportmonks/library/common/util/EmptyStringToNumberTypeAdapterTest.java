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
            .create();

    private static class IntHolder {
        Integer value;
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
}
