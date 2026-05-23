package es.com.kete1987.sportmonks.library.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyStringToNumberTypeAdapterTest {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(int.class, new EmptyStringToNumberTypeAdapter())
            .registerTypeAdapter(Integer.class, new EmptyStringToNumberTypeAdapter())
            .create();

    private static class IntHolder {
        Integer value;
        int primitive;
    }

    @Test
    void emptyString_parsedAsNull_forInteger() {
        IntHolder result = gson.fromJson("{\"value\": \"\"}", IntHolder.class);
        assertNull(result.value);
    }

    @Test
    void nullJson_parsedAsNull_forInteger() {
        IntHolder result = gson.fromJson("{\"value\": null}", IntHolder.class);
        assertNull(result.value);
    }

    @Test
    void validNumber_parsedCorrectly_forInteger() {
        IntHolder result = gson.fromJson("{\"value\": 42}", IntHolder.class);
        assertEquals(42, result.value);
    }

    @Test
    void validNumberAsString_parsedCorrectly_forInteger() {
        IntHolder result = gson.fromJson("{\"value\": \"42\"}", IntHolder.class);
        assertEquals(42, result.value);
    }

    @Test
    void missingField_remainsNull_forInteger() {
        IntHolder result = gson.fromJson("{}", IntHolder.class);
        assertNull(result.value);
    }
}
