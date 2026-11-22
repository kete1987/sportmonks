package es.com.kete1987.sportmonks.library.football.model.match;

import es.com.kete1987.sportmonks.library.football.util.MatchStatus;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;


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
    @Nullable
    @SerializedName("result_info")
    protected String resultInfo;
    protected String leg;
    @Nullable
    protected String details;
    protected Long length;
    @Nullable
    protected Boolean placeholder;
    @Nullable
    @SerializedName("has_odds")
    protected Boolean hasOdds;
    @Nullable
    @SerializedName("has_premium_odds")
    protected Boolean hasPremiumOdds;
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

    /** @return the aggregate ID, or null when the fixture has no aggregate. */
    @Nullable
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

    /** @return the leg, or null when not applicable. */
    @Nullable
    public String getLeg() {
        return leg;
    }

    /** @return the textual result, for example "Real Madrid won after full-time.", or null when there is none yet. */
    @Nullable
    public String getResultInfo() {
        return resultInfo;
    }

    /** @return extra information about the fixture, or null when the API sends none. */
    @Nullable
    public String getDetails() {
        return details;
    }

    public Long getLength() {
        return length;
    }

    /** @return true when the fixture is a placeholder (teams not known yet), or null when absent. */
    @Nullable
    public Boolean getPlaceholder() {
        return placeholder;
    }

    /** @return whether pre-match odds exist for this fixture, or null when absent. */
    @Nullable
    public Boolean getHasOdds() {
        return hasOdds;
    }

    /** @return whether premium odds exist for this fixture, or null when absent. */
    @Nullable
    public Boolean getHasPremiumOdds() {
        return hasPremiumOdds;
    }

    public Long getStartingAtTimestamp() {
        return startingAtTimestamp;
    }

    public MatchMeta getMeta() {
        return meta;
    }

    /**
     * Total order, consistent with {@link #equals(Object)} for matches that have an id:
     * <ol>
     *   <li>state group: live (0), not started (1), finished (2), anything else (3); a null
     *       {@code state_id} counts as "anything else";</li>
     *   <li>{@code starting_at_timestamp} ascending, null last;</li>
     *   <li>{@code id} ascending, null last.</li>
     * </ol>
     * Two matches compare as 0 only when they agree on all three keys, so two different matches
     * with an id never collide in a {@code TreeSet}. Matches without an id and with the same state
     * group and timestamp are indistinguishable for the order.
     */
    @Override
    public int compareTo(Match other) {
        int byState = Integer.compare(matchPriority(stateId), matchPriority(other.stateId));
        if (byState != 0) {
            return byState;
        }
        int byTimestamp = compareNullsLast(startingAtTimestamp, other.startingAtTimestamp);
        if (byTimestamp != 0) {
            return byTimestamp;
        }
        return compareNullsLast(id, other.id);
    }

    private static int compareNullsLast(Long a, Long b) {
        if (a == null || b == null) {
            return Boolean.compare(a == null, b == null);
        }
        return Long.compare(a, b);
    }

    private static int matchPriority(Long stateId) {
        if (stateId == null) return 3;
        int state = stateId.intValue();
        if (MatchStatus.isLiveMatch(state))     return 0;
        if (state == MatchStatus.NOT_STARTED)   return 1;
        if (MatchStatus.isMatchFinished(state)) return 2;
        return 3;
    }

    /** Matches are equal when they have the same non-null id; a match without id is only equal to itself. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match)) return false;
        Match match = (Match) o;
        return id != null && id.equals(match.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }
}
