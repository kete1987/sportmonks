package es.com.kete1987.sportmonks.library.v3.model.season;

import es.com.kete1987.sportmonks.library.v3.model.match.Match;
import es.com.kete1987.sportmonks.library.v3.model.stage.Stage;

import java.util.List;

public class SeasonData {
    private Long id;
    private Long sport_id;
    private Long league_id;
    private Long tie_breaker_rule_id;
    private String name;
    private Boolean finished;
    private Boolean pending;
    private Boolean is_current;
    private String starting_at;
    private String ending_at;
    private String standings_recalculated_at;
    private Boolean games_in_current_week;
    private List<Stage> stages;
    private List<Match> fixtures;

    public SeasonData() {}

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sport_id;
    }

    public Long getLeagueId() {
        return league_id;
    }

    public Long getTieBreakerRuleId() {
        return tie_breaker_rule_id;
    }

    public String getName() {
        return name;
    }

    public Boolean getFinished() {
        return finished;
    }

    public Boolean getPending() {
        return pending;
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

    public String getStandingsRecalculatedAt() {
        return standings_recalculated_at;
    }

    public Boolean getGamesInCurrentWeek() {
        return games_in_current_week;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public List<Match> getFixtures() {
        return fixtures;
    }
}
