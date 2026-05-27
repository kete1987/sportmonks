package es.com.kete1987.sportmonks.library.football.model.standings;

import com.google.gson.annotations.SerializedName;

public class StandingsDetail {
    private Long id;
    @SerializedName("standing_type")
    private String standingType;
    @SerializedName("standing_id")
    private Long standingId;
    @SerializedName("type_id")
    private Long typeId;
    private Long value;

    public StandingsDetail() {
    }

    public Long getId() {
        return id;
    }

    public String getStandingType() {
        return standingType;
    }

    public Long getStandingId() {
        return standingId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Long getValue() {
        return value;
    }
}
