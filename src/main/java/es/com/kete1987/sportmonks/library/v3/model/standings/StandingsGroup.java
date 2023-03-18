package es.com.kete1987.sportmonks.library.v3.model.standings;

public class StandingsGroup {
    private Long id;
    private Long sport_id;
    private Long league_id;
    private Long season_id;
    private Long stage_id;
    private String name;
    private String starting_at;
    private String ending_at;
    private Boolean games_in_current_week;
    private Boolean is_current;
    private Boolean is_finished;
    private Boolean pending;

    public StandingsGroup() {
    }

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

    public String getStartingAt() {
        return starting_at;
    }

    public String getEndingAt() {
        return ending_at;
    }

    public Boolean getGamesInCurrentWeek() {
        return games_in_current_week;
    }

    public Boolean getIsCurrent() {
        return is_current;
    }

    public Boolean getIsFinished() {
        return is_finished;
    }

    public Boolean getPending() {
        return pending;
    }
}
