package es.com.kete1987.sportmonks.library.football.model.statistic;

import com.google.gson.annotations.SerializedName;

/**
 * One statistic value: an entry of {@code statistics/stages|rounds/{id}}, or one of the
 * {@link SeasonStatistic#getDetails() details} of a season statistic.
 *
 * <p>{@link #getValue()} keeps the raw {@code value} object of the API, whose keys depend on the
 * type (for example {@code {"count":9,"average":9}} or {@code {"total":1}}); Gson exposes it as a
 * {@code Map<String, Object>} with numbers as {@code Double}.
 */
public class Statistic {
    private Long id;
    @SerializedName("type_id")
    private Long typeId;
    @SerializedName("participant_id")
    private Long participantId;
    @SerializedName("participant_type")
    private String participantType;
    private Object value;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("stage_id")
    private Long stageId;
    @SerializedName("model_id")
    private Long modelId;
    @SerializedName("relation_id")
    private Long relationId;

    public Statistic() {
    }

    public Long getId() {
        return id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public String getParticipantType() {
        return participantType;
    }

    public Object getValue() {
        return value;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public Long getStageId() {
        return stageId;
    }

    /** Id of the stage or round the statistic belongs to ({@code model_id}). */
    public Long getModelId() {
        return modelId;
    }

    /**
     * Id of the entity the value refers to ({@code relation_id}, e.g. the team with most corners),
     * or {@code null} when the statistic is about the stage or round as a whole.
     */
    public Long getRelationId() {
        return relationId;
    }
}
