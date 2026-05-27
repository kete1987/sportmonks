package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.annotations.SerializedName;

public class LineUpDetails {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("team_id")
    private Long teamId;
    @SerializedName("lineup_id")
    private Long lineupId;
    @SerializedName("type_id")
    private Long typeId;
    private LineUpDetailsData data;

    public LineUpDetails() {}

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getLineupId() {
        return lineupId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public LineUpDetailsData getData() {
        return data;
    }
}
