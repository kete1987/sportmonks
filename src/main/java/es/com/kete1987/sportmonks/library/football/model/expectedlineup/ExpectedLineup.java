package es.com.kete1987.sportmonks.library.football.model.expectedlineup;

import es.com.kete1987.sportmonks.library.football.model.player.Player;

import java.util.List;

public class ExpectedLineup {
    private Long id;
    private Long fixture_id;
    private Long team_id;
    private String formation;
    private List<Player> players;

    public ExpectedLineup() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixture_id;
    }

    public Long getTeamId() {
        return team_id;
    }

    public String getFormation() {
        return formation;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
