package es.com.kete1987.sportmonks.library.football.model.totw;

import es.com.kete1987.sportmonks.library.football.model.player.Player;

import java.util.List;

public class TeamOfTheWeek {
    private Long id;
    private Long fixture_id;
    private Long round_id;
    private Long league_id;
    private List<Player> players;

    public TeamOfTheWeek() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixture_id;
    }

    public Long getRoundId() {
        return round_id;
    }

    public Long getLeagueId() {
        return league_id;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
