package es.com.kete1987.sportmonks.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Compares the raw JSON of a real API response with a model class and reports the JSON keys that the
 * class does not declare. Gson silently drops unknown fields, so this is how a missing field or a
 * misspelt {@code @SerializedName} becomes visible. The API token only travels in the
 * {@code Authorization} header.
 */
final class JsonCoverage {
    private static final String BASE = "https://api.sportmonks.com/v3/";
    private static final String[] SKIPPED_PACKAGES = {
            "es.com.kete1987.sportmonks.library.football.model.match",
            "es.com.kete1987.sportmonks.library.football.model.statistic",
            "es.com.kete1987.sportmonks.library.football.model.schedule"
    };

    private JsonCoverage() {
    }

    /** Fetches {@code path} (relative to {@code /v3/}) and returns the {@code data} element. */
    static JsonElement fetchData(String key, String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE + path))
                .header("Authorization", key)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("HTTP " + response.statusCode() + " for " + path);
        }
        return JsonParser.parseString(response.body()).getAsJsonObject().get("data");
    }

    /** Returns the JSON paths present in {@code json} that {@code model} (or a nested class) lacks. */
    static List<String> missing(JsonElement json, Class<?> model) {
        List<String> out = new ArrayList<>();
        walk(json, model, model.getSimpleName(), out);
        return out;
    }

    private static void walk(JsonElement json, Type type, String path, List<String> out) {
        if (json == null || json.isJsonNull()) {
            return;
        }
        if (json.isJsonArray()) {
            Type element = elementType(type);
            Set<String> seen = new HashSet<>();
            JsonArray array = json.getAsJsonArray();
            for (JsonElement item : array) {
                List<String> found = new ArrayList<>();
                walk(item, element, path + "[]", found);
                for (String f : found) {
                    if (seen.add(f)) {
                        out.add(f);
                    }
                }
            }
            return;
        }
        if (!json.isJsonObject() || !(type instanceof Class || type instanceof ParameterizedType)) {
            return;
        }
        Class<?> cls = type instanceof Class ? (Class<?>) type : (Class<?>) ((ParameterizedType) type).getRawType();
        if (skip(cls)) {
            return;
        }
        JsonObject object = json.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            Field field = find(cls, entry.getKey());
            if (field == null) {
                out.add(path + "." + entry.getKey());
            } else {
                walk(entry.getValue(), field.getGenericType(), path + "." + entry.getKey(), out);
            }
        }
    }

    private static boolean skip(Class<?> cls) {
        if (cls == Object.class || cls.isPrimitive() || cls.getName().startsWith("java.")
                || cls.getName().startsWith("com.google.gson")) {
            return true;
        }
        return Arrays.stream(SKIPPED_PACKAGES).anyMatch(p -> cls.getName().startsWith(p));
    }

    private static Type elementType(Type type) {
        if (type instanceof ParameterizedType) {
            Type[] args = ((ParameterizedType) type).getActualTypeArguments();
            return args.length == 1 ? args[0] : Object.class;
        }
        if (type instanceof Class && ((Class<?>) type).isArray()) {
            return ((Class<?>) type).getComponentType();
        }
        // top-level list endpoint: the model describes each element
        return type instanceof Class && !java.util.Collection.class.isAssignableFrom((Class<?>) type)
                ? type : Object.class;
    }

    private static Field find(Class<?> cls, String jsonName) {
        for (Class<?> c = cls; c != null && c != Object.class; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers()) && names(f).contains(jsonName)) {
                    return f;
                }
            }
        }
        return null;
    }

    private static List<String> names(Field f) {
        SerializedName annotation = f.getAnnotation(SerializedName.class);
        List<String> names = new ArrayList<>();
        if (annotation == null) {
            names.add(f.getName());
        } else {
            names.add(annotation.value());
            names.addAll(Arrays.asList(annotation.alternate()));
        }
        return names;
    }
}
