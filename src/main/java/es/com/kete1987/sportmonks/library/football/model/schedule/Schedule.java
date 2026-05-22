package es.com.kete1987.sportmonks.library.football.model.schedule;

import es.com.kete1987.sportmonks.library.football.model.rounds.Round;

import java.util.List;

public class Schedule {
    private Long season_id;
    private Long team_id;
    private List<Round> rounds;

    public Schedule() {
    }

    public Long getSeasonId() {
        return season_id;
    }

    public Long getTeamId() {
        return team_id;
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
