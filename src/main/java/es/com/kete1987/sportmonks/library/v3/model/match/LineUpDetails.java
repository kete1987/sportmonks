package es.com.kete1987.sportmonks.library.v3.model.match;

import java.util.List;

public class LineUpDetails {
    private Long id;
    private Long fixture_id;
    private Long player_id;
    private Long team_id;
    private Long lineup_id;
    private Long type_id;
    private LineUpDetailsData data;

    public LineUpDetails() {}

    public Long getId() {
        return id;
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

    public Long getLineupId() {
        return lineup_id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public LineUpDetailsData getData() {
        return data;
    }
}
