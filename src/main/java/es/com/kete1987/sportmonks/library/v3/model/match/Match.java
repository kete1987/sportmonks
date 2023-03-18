package es.com.kete1987.sportmonks.library.v3.model.match;

import es.com.kete1987.sportmonks.library.v3.util.MatchStatus;

public class Match implements Comparable<Object> {
    protected Long id; //ID del partido
    protected Long sport_id;
    protected Long league_id;
    protected Long season_id;
    protected Long stage_id;
    protected Long group_id;
    protected Long aggregate_id;
    protected Long round_id;
    protected Long state_id;
    protected Long venue_id;
    protected String name;
    protected String starting_at;
    protected String leg;
    protected Long length;
    protected Long starting_at_timestamp;
    protected MatchMeta meta;

    public Match() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sport_id;
    }

    public Long getLeagueId() {
        return league_id;
    }

    public Long getSeasonId() {
        return season_id;
    }

    public Long getStageId() {
        return stage_id;
    }

    public Long getGroupId() {
        return group_id;
    }

    public Long getAggregateId() {
        return aggregate_id;
    }

    public Long getRoundId() {
        return round_id;
    }

    public Long getStateId() {
        return state_id;
    }

    public Long getVenueId() {
        return venue_id;
    }

    public String getName() {
        return name;
    }

    public String getStartingAt() {
        return starting_at;
    }

    public String getLeg() {
        return leg;
    }

    public Long getLength() {
        return length;
    }

    public Long getStartingAtTimestamp() {
        return starting_at_timestamp;
    }

    public MatchMeta getMeta() {
        return meta;
    }

    @Override
    public int compareTo(Object o) {
        Match aux = (Match) o;
        // getStarting_at_timestamp
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
