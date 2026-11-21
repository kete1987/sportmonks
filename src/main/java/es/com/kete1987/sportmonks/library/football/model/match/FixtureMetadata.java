package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

/**
 * Fixture metadata entry, as returned by the {@code metadata} include.
 * <p>The shape of {@code values} depends on {@link #getTypeId()} and is kept as raw JSON:
 * usually an object (type 35 {@code {"neutral":false}}, 36 {@code {"kickOff":"home"}},
 * 159 {@code {"home":"4-3-3","away":"4-2-3-1"}}, 578 {@code {"attendance":62065}}), but it can also
 * be a plain value, e.g. type 613 with {@code value_type} {@code "string"} returns {@code "#BARRMA"}.</p>
 * Every field may be absent or null in an API response.
 */
public class FixtureMetadata {
    @Nullable
    private Long id;
    @Nullable
    @SerializedName("metadatable_id")
    private Long metadatableId;
    @Nullable
    @SerializedName("type_id")
    private Long typeId;
    @Nullable
    @SerializedName("value_type")
    private String valueType;
    @Nullable
    private JsonElement values;

    /** @return the metadata record ID, or null when unavailable. */
    @Nullable
    public Long getId() {
        return id;
    }

    /** @return the ID of the entity the metadata belongs to (the fixture), or null when unavailable. */
    @Nullable
    public Long getMetadatableId() {
        return metadatableId;
    }

    /** @return the metadata type ID, or null when unavailable. */
    @Nullable
    public Long getTypeId() {
        return typeId;
    }

    /** @return the declared type of {@code values} (e.g. {@code "object"}, {@code "string"}), or null. */
    @Nullable
    public String getValueType() {
        return valueType;
    }

    /**
     * @return a copy of the raw {@code values} JSON (object or primitive), or null when absent
     *         or JSON null. Changes to the returned element do not affect this entry.
     */
    @Nullable
    public JsonElement getValues() {
        return values == null || values.isJsonNull() ? null : values.deepCopy();
    }

    /**
     * @param key a member of the {@code values} object (e.g. {@code "attendance"})
     * @return a copy of that member, or null when {@code values} is not an object, the member is
     *         missing or it is JSON null
     */
    @Nullable
    public JsonElement getValue(String key) {
        if (values == null || !values.isJsonObject()) {
            return null;
        }
        JsonElement value = ((JsonObject) values).get(key);
        return value == null || value.isJsonNull() ? null : value.deepCopy();
    }

    /**
     * @param key a member of the {@code values} object
     * @return that member as a string (numbers and booleans are converted), or null when it is
     *         missing, JSON null, or not a primitive
     */
    @Nullable
    public String getValueAsString(String key) {
        JsonElement value = getValue(key);
        return value != null && value.isJsonPrimitive() ? value.getAsString() : null;
    }
}
