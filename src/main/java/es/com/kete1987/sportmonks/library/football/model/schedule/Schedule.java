package es.com.kete1987.sportmonks.library.football.model.schedule;

import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.stage.StageAggregate;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

/**
 * One stage of a schedule, as returned by {@code schedules/*}: the stage itself (id, name, type,
 * dates) with its {@link #getRounds() rounds} and their fixtures, or its {@link #getAggregates()
 * aggregates} for knockout stages.
 */
public class Schedule {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("league_id")
    private Long leagueId;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("type_id")
    private Long typeId;
    private String name;
    @SerializedName("sort_order")
    private Long sortOrder;
    private Boolean finished;
    @SerializedName("is_current")
    private Boolean isCurrent;
    @SerializedName("starting_at")
    private String startingAt;
    @SerializedName("ending_at")
    private String endingAt;
    @SerializedName("games_in_current_week")
    private Boolean gamesInCurrentWeek;
    @SerializedName("tie_breaker_rule_id")
    private Long tieBreakerRuleId;
    private List<StageAggregate> aggregates;
    private List<Round> rounds;

    public Schedule() {
    }

    /** Id of the stage. */
    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sportId;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    /** Stage type id (e.g. 223 for a regular season). */
    public Long getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public Boolean getFinished() {
        return finished;
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

    public Boolean getGamesInCurrentWeek() {
        return gamesInCurrentWeek;
    }

    public Long getTieBreakerRuleId() {
        return tieBreakerRuleId;
    }

    /** Aggregates of the stage (two-legged ties); empty or {@code null} for league stages. */
    public List<StageAggregate> getAggregates() {
        return ModelCollections.unmodifiable(aggregates);
    }

    public List<Round> getRounds() {
        return ModelCollections.unmodifiable(rounds);
    }
}
