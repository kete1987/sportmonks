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

    public Long getSport_id() {
        return sport_id;
    }

    public Long getLeague_id() {
        return league_id;
    }

    public Long getTie_breaker_rule_id() {
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

    public Boolean getIs_current() {
        return is_current;
    }

    public String getStarting_at() {
        return starting_at;
    }

    public String getEnding_at() {
        return ending_at;
    }

    public String getStandings_recalculated_at() {
        return standings_recalculated_at;
    }

    public Boolean getGames_in_current_week() {
        return games_in_current_week;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public List<Match> getFixtures() {
        return fixtures;
    }
}
