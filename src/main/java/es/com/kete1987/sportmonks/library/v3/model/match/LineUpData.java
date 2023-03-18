package es.com.kete1987.sportmonks.library.v3.model.match;

public class LineUpData {
    private Long id;
    private Long sport_id;
    private Long fixture_id;
    private Long player_id;
    private Long team_id;
    private Long position_id;
    private Long type_id;
    private String player_name;
    private Long jersey_number;

    public LineUpData() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sport_id;
    }

    public Long getFixtureId() {
        return fixture_id;
    }

    public Long getPlayerId() {
        return player_id;
    }

    public Long getTeamId() {
        return team_id;
    }

    public Long getPositionId() {
        return position_id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public String getPlayerName() {
        return player_name;
    }

    public Long getJerseyNumber() {
        return jersey_number;
    }
}
