package es.com.kete1987.sportmonks.library.football.model.seasonstats;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

/**
 * Statistic of a season, stage or round, as returned by the {@code statistics} include on those
 * entities. The value is polymorphic (object, array or scalar depending on the type), so it is exposed
 * as raw JSON.
 */
public class ModelStatistic {
    private Long id;
    @SerializedName("model_id")
    private Long modelId;
    @SerializedName("type_id")
    private Long typeId;
    @SerializedName("relation_id")
    private Long relationId;
    private JsonElement value;
    private String type;

    public ModelStatistic() {
    }

    public Long getId() {
        return id;
    }

    /** @return the id of the season, stage or round the statistic belongs to. */
    public Long getModelId() {
        return modelId;
    }

    public Long getTypeId() {
        return typeId;
    }

    /** @return the id of the related entity (for example a team), or null. */
    @Nullable
    public Long getRelationId() {
        return relationId;
    }

    /** @return the raw value, whose shape depends on {@link #getType()}; null if absent. */
    @Nullable
    public JsonElement getValue() {
        return value;
    }

    /** @return how the value is encoded ({@code "array"}, {@code "object"}, ...), or null. */
    @Nullable
    public String getType() {
        return type;
    }
}