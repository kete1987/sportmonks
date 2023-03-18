package es.com.kete1987.sportmonks.library.v3.model.rounds;

import es.com.kete1987.sportmonks.library.v3.model.match.MatchDetail;

import java.util.List;

public class Round {
    private Long id;
    private Long sport_id;
    private Long league_id;
    private Long season_id;
    private Long stage_id;
    private String name;
    private Boolean finished;
    private Boolean is_current;
    private String starting_at;
    private String ending_at;
    private Boolean games_in_current_week;
    private List<MatchDetail> fixtures;

    public Round() {}

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sport_id;
    }

    public Long getLeagueId() {
        return league_id;
    }

    public Long getSeasonId() {
        return season_id;
    }

    public Long getStageId() {
        return stage_id;
    }

    public String getName() {
        return name;
    }

    public Boolean getFinished() {
        return finished;
    }

    public Boolean getIsCurrent() {
        return is_current;
    }

    public String getStartingAt() {
        return starting_at;
    }

    public String getEndingAt() {
        return ending_at;
    }

    public Boolean getGamesInCurrentWeek() {
        return games_in_current_week;
    }

    public List<MatchDetail> getFixtures() {
        return fixtures;
    }
}
