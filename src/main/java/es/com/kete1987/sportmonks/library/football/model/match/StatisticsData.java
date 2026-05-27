package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.annotations.SerializedName;

public class StatisticsData {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("type_id")
    private Long typeId;
    @SerializedName("participant_id")
    private Long participantId;
    private StatisticsDataValue data;
    private String location;

    public StatisticsData() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public StatisticsDataValue getData() {
        return data;
    }

    public String getLocation() {
        return location;
    }
}
