package es.com.kete1987.sportmonks.library.football.model.stage;

import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.standings.StandingsGroup;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import org.jetbrains.annotations.Nullable;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class Stage implements Comparable<Stage> {
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
    private String gamesInCurrentWeek;
    private List<MatchDetail> fixtures;
    private List<StageAggregate> aggregates;
    private List<StandingsGroup> groups;
    @SerializedName("tie_breaker_rule_id")
    private Long tieBreakerRuleId;
    private Type type;
    private List<Round> rounds;
    private Round currentround;
    private League league;
    private SeasonData season;

    public Stage() {
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

    public Long getSeasonId() {
        return seasonId;
    }

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

    public String getGamesInCurrentWeek() {
        return gamesInCurrentWeek;
    }

    public List<MatchDetail> getFixtures() {
        return ModelCollections.unmodifiable(fixtures);
    }

    public List<StageAggregate> getAggregates() {
        return ModelCollections.unmodifiable(aggregates);
    }

    public List<StandingsGroup> getGroups() {
        return ModelCollections.unmodifiable(groups);
    }

    /**
     * Total order: {@code sort_order} ascending, then {@code ending_at} ascending, then {@code id}
     * ascending; a null key always sorts last. Two stages with an id compare as 0 only when they are
     * equal, so a {@code TreeSet} does not drop distinct stages.
     */
    @Override
    public int compareTo(Stage other) {
        int bySortOrder = compareNullsLast(sortOrder, other.sortOrder);
        if (bySortOrder != 0) {
            return bySortOrder;
        }
        int byEnd = compareNullsLast(endingAt, other.endingAt);
        if (byEnd != 0) {
            return byEnd;
        }
        return compareNullsLast(id, other.id);
    }

    private static <T extends Comparable<T>> int compareNullsLast(T a, T b) {
        if (a == null || b == null) {
            return Boolean.compare(a == null, b == null);
        }
        return a.compareTo(b);
    }

    /** Stages are equal when they have the same non-null id; a stage without id is only equal to itself. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stage)) return false;
        return id != null && id.equals(((Stage) o).id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }

    /**
     * @return {@link #getGamesInCurrentWeek()} as the boolean the API actually sends, or null when
     *         the field is absent
     */
    @Nullable
    public Boolean isGamesInCurrentWeek() {
        return gamesInCurrentWeek == null ? null : Boolean.valueOf(gamesInCurrentWeek);
    }

    /** @return the id of the rule used to break ties in the standings, or null. */
    @Nullable
    public Long getTieBreakerRuleId() {
        return tieBreakerRuleId;
    }

    /** @return the stage type (group stage, knock-out, ...), or null if the {@code type} include is absent. */
    @Nullable
    public Type getType() {
        return type;
    }

    /** @return the rounds, or null if the {@code rounds} include is absent. */
    @Nullable
    public List<Round> getRounds() {
        return ModelCollections.unmodifiable(rounds);
    }

    /** @return the current round, or null if there is none or the {@code currentRound} include is absent. */
    @Nullable
    public Round getCurrentRound() {
        return currentround;
    }

    /** @return the league, or null if the {@code league} include is absent. */
    @Nullable
    public League getLeague() {
        return league;
    }

    /** @return the season, or null if the {@code season} include is absent. */
    @Nullable
    public SeasonData getSeason() {
        return season;
    }
}
