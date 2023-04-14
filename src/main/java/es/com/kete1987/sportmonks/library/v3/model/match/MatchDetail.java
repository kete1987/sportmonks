package es.com.kete1987.sportmonks.library.v3.model.match;

import es.com.kete1987.sportmonks.library.v3.model.comments.Comment;
import es.com.kete1987.sportmonks.library.v3.model.team.Team;
import es.com.kete1987.sportmonks.library.v3.model.venue.Venue;
import es.com.kete1987.sportmonks.library.v3.util.LineUpType;
import es.com.kete1987.sportmonks.library.v3.util.MatchStatus;

import java.util.ArrayList;
import java.util.List;

public class MatchDetail extends Match {
    private Venue venue;
    private State state;
    private List<LineUpData> lineups;
    private List<EventData> events;
    private List<StatisticsData> statistics;
    private List<Period> periods;
    private List<Team> participants;
    private List<Scores> scores;

    private List<Comment> comments;

    public MatchDetail() {
    }

    public Venue getVenue() {
        return venue;
    }

    public State getState() {
        return state;
    }

    public List<LineUpData> getLineups() {
        return lineups;
    }

    public List<LineUpData> getLineupLocalTeam() {
        List<LineUpData> list = new ArrayList<>();
        int homeTeamId = getHomeTeam().getId().intValue();
        for (LineUpData lud : getLineups()) {
            if (lud.getTypeId().intValue() == LineUpType.LINEUP && lud.getTeamId().intValue() == homeTeamId) {
                list.add(lud);
            }
        }
        return list;
    }

    public List<LineUpData> getLineupVisitorTeam() {
        List<LineUpData> list = new ArrayList<>();
        int awayTeamId = getAwayTeam().getId().intValue();
        for (LineUpData lud : getLineups()) {
            if (lud.getTypeId().intValue() == LineUpType.LINEUP && lud.getTeamId().intValue() == awayTeamId) {
                list.add(lud);
            }
        }
        return list;
    }

    public List<LineUpData> getBenchLocalTeam() {
        List<LineUpData> list = new ArrayList<>();
        int homeTeamId = getHomeTeam().getId().intValue();
        for (LineUpData lud : getLineups()) {
            if (lud.getTypeId().intValue() == LineUpType.BENCH && lud.getTeamId().intValue() == homeTeamId) {
                list.add(lud);
            }
        }
        return list;
    }

    public List<LineUpData> getBenchVisitorTeam() {
        List<LineUpData> list = new ArrayList<>();
        int awayTeamId = getAwayTeam().getId().intValue();
        for (LineUpData lud : getLineups()) {
            if (lud.getTypeId().intValue() == LineUpType.BENCH && lud.getTeamId().intValue() == awayTeamId) {
                list.add(lud);
            }
        }
        return list;
    }

    public List<EventData> getEvents() {
        return events;
    }

    public List<StatisticsData> getStatistics() {
        return statistics;
    }

    public StatisticsData getStatisticDataByTypeAndTeamId(int teamId, int type) {
        for (StatisticsData sd : getStatistics()) {
            if (sd.getParticipantId().intValue() == teamId && sd.getTypeId().intValue() == type)
                return sd;
        }
        return null;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public List<Team> getParticipants() {
        return participants;
    }

    public Team getHomeTeam() {
        for (Team team : getParticipants()) {
            if (team.getMeta() != null && team.getMeta().getLocation().equals("home"))
                return team;
        }
        return null;
    }

    public Team getAwayTeam() {
        for (Team team : getParticipants()) {
            if (team.getMeta() != null && team.getMeta().getLocation().equals("away"))
                return team;
        }
        return null;
    }

    public List<Scores> getScores() {
        return scores;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public int getCurrentLocalTeamGoals() {
        if (getScores() != null) {
            for (Scores score : getScores()) {
                if (score.getDescription().equalsIgnoreCase("CURRENT") && score.getScore() != null && score.getScore().getParticipant().equalsIgnoreCase("home"))
                    return score.getScore().getGoals().intValue();
            }
        }
        return 0;
    }

    public int getCurrentVisitorTeamGoals() {
        if (getScores() != null) {
            for (Scores score : getScores()) {
                if (score.getDescription().equalsIgnoreCase("CURRENT") && score.getScore() != null && score.getScore().getParticipant().equalsIgnoreCase("away"))
                    return score.getScore().getGoals().intValue();
            }
        }
        return 0;
    }

    public boolean isLiveMatch() {
        return MatchStatus.isLiveMatch(getState().getId().intValue());
    }

    public boolean isMatchFinished() {
        return MatchStatus.isMatchFinished(getState().getId().intValue());
    }

    public Period getCurrentPeriod() {
        if (getPeriods() != null && getPeriods().size() > 0) {
            for (Period period : getPeriods()) {
                if (period.getTypeId() != null && state.getTypeId() != null) {
                    if (period.getTypeId().equals(state.getTypeId())) {
                        return period;
                    }
                } else if (period.getTypeId() != null && state.getType() != null && state.getType().getId() != null) {
                    if (period.getTypeId().equals(state.getType().getId())) {
                        return period;
                    }
                }
            }
        }
        return null;
    }
}
