package es.com.kete1987.sportmonks.library.common.util;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Handles JSON fields that the Sportmonks API occasionally serializes as "" instead of null for
 * int/Integer fields. Originated in API v2; no evidence of this in v3 fixtures as of v3.0.0, but
 * kept as a defensive measure until real API integration tests confirm it is no longer needed.
 * Safe to remove once task 86c9xz70u (integration tests) verifies no empty-string int fields appear.
 */
public class EmptyStringToNumberTypeAdapter extends TypeAdapter<Number> {
    @Override
    public void write(JsonWriter out, Number value)
            throws IOException {
        out.value(value);
    }

    @Override
    public Number read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            String result = in.nextString();
            if ("".equals(result)) {
                return null;
            }
            return Integer.parseInt(result);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }
}
