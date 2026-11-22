package es.com.kete1987.sportmonks.library.common.util;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Handles numeric JSON fields that the Sportmonks API occasionally serializes as "" instead of
 * null. Originated in API v2; no evidence of this in v3 fixtures as of v3.0.0, but kept as a
 * defensive measure. The no-argument constructor reads {@code int}/{@code Integer} values and
 * {@link #forLong()} reads {@code long}/{@code Long} ones, the type of almost every id.
 */
public class EmptyStringToNumberTypeAdapter extends TypeAdapter<Number> {

    private final boolean asLong;

    /** Adapter for {@code int} and {@code Integer} fields. */
    public EmptyStringToNumberTypeAdapter() {
        this(false);
    }

    private EmptyStringToNumberTypeAdapter(boolean asLong) {
        this.asLong = asLong;
    }

    /** Adapter for {@code long} and {@code Long} fields. */
    public static EmptyStringToNumberTypeAdapter forLong() {
        return new EmptyStringToNumberTypeAdapter(true);
    }

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
            if (in.peek() != JsonToken.STRING) {
                return readNumber(in);
            }
            String result = in.nextString();
            if ("".equals(result)) {
                return null;
            }
            return parse(result);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    /** A JSON number keeps Gson's own reading (e.g. {@code 12.0} is accepted as 12). */
    private Number readNumber(JsonReader in) throws IOException {
        if (asLong) {
            return in.nextLong();
        }
        return in.nextInt();
    }

    private Number parse(String value) {
        if (asLong) {
            return Long.valueOf(value);
        }
        return Integer.valueOf(value);
    }
}
