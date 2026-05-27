package es.com.kete1987.sportmonks.library.football.model.match;

import es.com.kete1987.sportmonks.library.football.util.MatchStatus;
import com.google.gson.annotations.SerializedName;

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

    @Override
    public int compareTo(Match aux) {
        if (getStateId() != null && aux.getStateId() != null) {
            Long status1 = getStateId();
            Long status2 = aux.getStateId();
            if (status1.intValue() == MatchStatus.NOT_STARTED) {
                if (status2.intValue() == MatchStatus.NOT_STARTED)
                    return (int) (getStartingAtTimestamp() - aux.getStartingAtTimestamp());
                else if (MatchStatus.isLiveMatch(status2.intValue()))
                    return 1;
                else
                    return -1;
            } else if (MatchStatus.isLiveMatch(status1.intValue())) {
                if (MatchStatus.isLiveMatch(status2.intValue()))
                    return 0;
                else
                    return -1;
            } else if (MatchStatus.isMatchFinished(status1.intValue())) {
                if (status2.intValue() == MatchStatus.NOT_STARTED || MatchStatus.isLiveMatch(status2.intValue()))
                    return 1;
                else if (MatchStatus.isMatchFinished(status2.intValue()))
                    return 0;
                else
                    return -1;
            } else {
                if (status2.intValue() == MatchStatus.NOT_STARTED || MatchStatus.isLiveMatch(status2.intValue()) || MatchStatus.isMatchFinished(status2.intValue()))
                    return 1;
                else
                    return 0;
            }
        }
        return 0;
    }
}
