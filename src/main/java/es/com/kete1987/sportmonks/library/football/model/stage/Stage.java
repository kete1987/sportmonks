package es.com.kete1987.sportmonks.library.football.model.stage;

import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.standings.StandingsGroup;

import java.util.List;
import java.util.Objects;
import com.google.gson.annotations.SerializedName;
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

    @Override
    public int compareTo(Stage other) {
        // Null-safe comparison of sortOrder (nulls sorted last)
        Long thisSortOrder = this.getSortOrder();
        Long otherSortOrder = other.getSortOrder();
        if (thisSortOrder == null && otherSortOrder == null) {
            // fall through to endingAt comparison
        } else if (thisSortOrder == null) {
            return 1;
        } else if (otherSortOrder == null) {
            return -1;
        } else {
            int cmp = Long.compare(thisSortOrder, otherSortOrder);
            if (cmp != 0) return cmp;
        }

        // Desempatar por endingAt (nulls al final)
        String thisEnd = this.getEndingAt();
        String otherEnd = other.getEndingAt();

        if (thisEnd == null) return 1;
        if (otherEnd == null) return -1;
        return thisEnd.compareTo(otherEnd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stage)) return false;
        return Objects.equals(id, ((Stage) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
