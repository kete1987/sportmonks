package es.com.kete1987.sportmonks.library.football.model.standings;

import com.google.gson.annotations.SerializedName;

public class StandingsRule {
    private Long id;
    @SerializedName("model_type")
    private String modelType;
    @SerializedName("model_id")
    private Long modelId;
    @SerializedName("type_id")
    private Long typeId;
    private Long position;

    public StandingsRule() {
    }

    public Long getId() {
        return id;
    }

    public String getModelType() {
        return modelType;
    }

    public Long getModelId() {
        return modelId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Long getPosition() {
        return position;
    }
}
