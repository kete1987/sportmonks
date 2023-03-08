package es.com.kete1987.sportmonks.library.v3.model.match;

public class LineUpData {
    private Long id;
    private Long sport_id;
    private Long fixture_id;
    private Long player_id;
    private Long team_id;
    private Long position_id;
    private Long formation_field;
    private Long type_id;
    private String player_name;
    private Long jersey_number;

    public LineUpData() {}

    public Long getId() {
        return id;
    }

    public Long getSport_id() {
        return sport_id;
    }

    public Long getFixture_id() {
        return fixture_id;
    }

    public Long getPlayer_id() {
        return player_id;
    }

    public Long getTeam_id() {
        return team_id;
    }

    public Long getPosition_id() {
        return position_id;
    }

    public Long getFormation_field() {
        return formation_field;
    }

    public Long getType_id() {
        return type_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public Long getJersey_number() {
        return jersey_number;
    }
}
