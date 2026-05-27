package es.com.kete1987.sportmonks.library.football.model.season;

import es.com.kete1987.sportmonks.library.football.model.match.Match;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class SeasonData {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("league_id")
    private Long leagueId;
    @SerializedName("tie_breaker_rule_id")
    private Long tieBreakerRuleId;
    private String name;
    private Boolean finished;
    private Boolean pending;
    @SerializedName("is_current")
    private Boolean isCurrent;
    @SerializedName("starting_at")
    private String startingAt;
    @SerializedName("ending_at")
    private String endingAt;
    @SerializedName("standings_recalculated_at")
    private String standingsRecalculatedAt;
    @SerializedName("games_in_current_week")
    private Boolean gamesInCurrentWeek;
    private List<Stage> stages;
    private List<Match> fixtures;

    public SeasonData() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sportId;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public Long getTieBreakerRuleId() {
        return tieBreakerRuleId;
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
        return isCurrent;
    }

    public String getStartingAt() {
        return startingAt;
    }

    public String getEndingAt() {
        return endingAt;
    }

    public String getStandingsRecalculatedAt() {
        return standingsRecalculatedAt;
    }

    public Boolean getGamesInCurrentWeek() {
        return gamesInCurrentWeek;
    }

    public List<Stage> getStages() {
        return ModelCollections.unmodifiable(stages);
    }

    public List<Match> getFixtures() {
        return ModelCollections.unmodifiable(fixtures);
    }
}
