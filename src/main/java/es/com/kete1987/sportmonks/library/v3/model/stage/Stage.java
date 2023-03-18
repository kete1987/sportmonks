package es.com.kete1987.sportmonks.library.v3.model.stage;

import es.com.kete1987.sportmonks.library.v3.model.match.MatchDetail;

import java.util.List;

public class Stage {
    private Long id;
    private Long sport_id;
    private Long league_id;
    private Long season_id;
    private Long type_id;
    private String name;
    private Long sort_order;
    private Boolean finished;
    private Boolean is_current;
    private String starting_at;
    private String ending_at;
    private String games_in_current_week;
    private List<MatchDetail> fixtures;
    private List<StageAggregate> aggregates;

    public Stage() {}

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

    public Long getTypeId() {
        return type_id;
    }

    public String getName() {
        return name;
    }

    public Long getSortOrder() {
        return sort_order;
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

    public String getGamesInCurrentWeek() {
        return games_in_current_week;
    }

    public List<MatchDetail> getFixtures() {
        return fixtures;
    }

    public List<StageAggregate> getAggregates() {
        return aggregates;
    }
}
