package es.com.kete1987.sportmonks.library.football.model.match;

import es.com.kete1987.sportmonks.library.football.util.MatchStatus;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Match implements Comparable<Match> {
    protected Long id; //ID del partido
    @SerializedName("sport_id")
    protected Long sportId;
    @SerializedName("league_id")
    protected Long leagueId;
    @SerializedName("season_id")
    protected Long seasonId;
    @SerializedName("stage_id")
    protected Long stageId;
    @SerializedName("group_id")
    protected Long groupId;
    @SerializedName("aggregate_id")
    protected Long aggregateId;
    @SerializedName("round_id")
    protected Long roundId;
    @SerializedName("state_id")
    protected Long stateId;
    @SerializedName("venue_id")
    protected Long venueId;
    protected String name;
    @SerializedName("starting_at")
    protected String startingAt;
    protected String leg;
    protected Long length;
    @SerializedName("starting_at_timestamp")
    protected Long startingAtTimestamp;
    protected MatchMeta meta;

    public Match() {
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

    public Long getStageId() {
        return stageId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Long getAggregateId() {
        return aggregateId;
    }

    public Long getRoundId() {
        return roundId;
    }

    public Long getStateId() {
        return stateId;
    }

    public Long getVenueId() {
        return venueId;
    }

    public String getName() {
        return name;
    }

    public String getStartingAt() {
        return startingAt;
    }

    public String getLeg() {
        return leg;
    }

    public Long getLength() {
        return length;
    }

    public Long getStartingAtTimestamp() {
        return startingAtTimestamp;
    }

    public MatchMeta getMeta() {
        return meta;
    }

    // ---- Ordering: Live (0) → Not Started (1) → Finished (2) → Other (3) ----

    @Override
    public int compareTo(Match other) {
        if (getStateId() == null || other.getStateId() == null) return 0;
        int p1 = matchPriority(getStateId().intValue());
        int p2 = matchPriority(other.getStateId().intValue());
        if (p1 != p2) {
            return Integer.compare(p1, p2);
        }
        // Both not-started: sort ascending by kick-off timestamp
        if (p1 == 1 && getStartingAtTimestamp() != null && other.getStartingAtTimestamp() != null) {
            return Long.compare(getStartingAtTimestamp(), other.getStartingAtTimestamp());
        }
        return 0;
    }

    private static int matchPriority(int stateId) {
        if (MatchStatus.isLiveMatch(stateId))     return 0;
        if (stateId == MatchStatus.NOT_STARTED)   return 1;
        if (MatchStatus.isMatchFinished(stateId)) return 2;
        return 3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match)) return false;
        Match match = (Match) o;
        return Objects.equals(id, match.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
