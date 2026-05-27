package es.com.kete1987.sportmonks.library.football.model.statistic;

import com.google.gson.annotations.SerializedName;

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
}
