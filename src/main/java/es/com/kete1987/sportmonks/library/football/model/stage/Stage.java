package es.com.kete1987.sportmonks.library.football.model.stage;

import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.standings.StandingsGroup;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;

public class Stage implements Comparable<Object> {
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
        return fixtures == null ? null : Collections.unmodifiableList(fixtures);
    }

    public List<StageAggregate> getAggregates() {
        return aggregates == null ? null : Collections.unmodifiableList(aggregates);
    }

    public List<StandingsGroup> getGroups() {
        return groups == null ? null : Collections.unmodifiableList(groups);
    }

    @Override
    public int compareTo(Object o) {
        Stage other = (Stage) o;

        // Comparación segura de Longs
        int cmp = Long.compare(this.getSortOrder(), other.getSortOrder());
        if (cmp != 0) return cmp;

        // Desempatar por endingAt (nulls al final)
        String thisEnd = this.getEndingAt();
        String otherEnd = other.getEndingAt();

        if (thisEnd == null && otherEnd != null) return 1;
        if (thisEnd != null && otherEnd == null) return -1;
        if (thisEnd == null && otherEnd == null) return 0;

        return thisEnd.compareTo(otherEnd);
    }
}
