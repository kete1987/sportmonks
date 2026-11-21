package es.com.kete1987.sportmonks.library.football.model.season;

import es.com.kete1987.sportmonks.library.football.model.match.Match;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.seasonstats.ModelStatistic;
import es.com.kete1987.sportmonks.library.football.model.sport.Sport;
import es.com.kete1987.sportmonks.library.football.model.standings.StandingsGroup;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.topscorers.TopScoresPlayer;
import org.jetbrains.annotations.Nullable;
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
    private Stage currentstage;
    private List<StandingsGroup> groups;
    private League league;
    private List<ModelStatistic> statistics;
    private List<Team> teams;
    private List<TopScoresPlayer> topscorers;
    private Sport sport;

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

    /** @return the current stage, or null if there is none or the {@code currentStage} include is absent. */
    @Nullable
    public Stage getCurrentStage() {
        return currentstage;
    }

    /** @return the groups of a cup season, or null if the {@code groups} include is absent. */
    @Nullable
    public List<StandingsGroup> getGroups() {
        return ModelCollections.unmodifiable(groups);
    }

    /** @return the league, or null if the {@code league} include is absent. */
    @Nullable
    public League getLeague() {
        return league;
    }

    /** @return the season statistics, or null if the {@code statistics} include is absent. */
    @Nullable
    public List<ModelStatistic> getStatistics() {
        return ModelCollections.unmodifiable(statistics);
    }

    /** @return the participating teams, or null if the {@code teams} include is absent. */
    @Nullable
    public List<Team> getTeams() {
        return ModelCollections.unmodifiable(teams);
    }

    /** @return the top scorers, or null if the {@code topscorers} include is absent. */
    @Nullable
    public List<TopScoresPlayer> getTopscorers() {
        return ModelCollections.unmodifiable(topscorers);
    }

    /** @return the sport, or null if the {@code sport} include is absent. */
    @Nullable
    public Sport getSport() {
        return sport;
    }
}
