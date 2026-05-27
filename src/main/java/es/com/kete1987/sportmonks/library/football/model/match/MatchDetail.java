package es.com.kete1987.sportmonks.library.football.model.match;

import es.com.kete1987.sportmonks.library.football.model.comments.Comment;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;
import es.com.kete1987.sportmonks.library.football.util.LineUpType;
import es.com.kete1987.sportmonks.library.football.util.MatchStatus;

import java.util.ArrayList;
import java.util.Collections;
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
        return lineups == null ? null : Collections.unmodifiableList(lineups);
    }

    public List<LineUpData> getLineupLocalTeam() {
        if (lineups == null || getHomeTeam() == null) {
            return Collections.emptyList();
        }
        List<LineUpData> list = new ArrayList<>();
        int homeTeamId = getHomeTeam().getId().intValue();
        for (LineUpData lud : lineups) {
            if (lud.getTypeId().intValue() == LineUpType.LINEUP && lud.getTeamId().intValue() == homeTeamId) {
                list.add(lud);
            }
        }
        return list;
    }

    public List<LineUpData> getLineupVisitorTeam() {
        if (lineups == null || getAwayTeam() == null) {
            return Collections.emptyList();
        }
        List<LineUpData> list = new ArrayList<>();
        int awayTeamId = getAwayTeam().getId().intValue();
        for (LineUpData lud : lineups) {
            if (lud.getTypeId().intValue() == LineUpType.LINEUP && lud.getTeamId().intValue() == awayTeamId) {
                list.add(lud);
            }
        }
        return list;
    }

    public List<LineUpData> getBenchLocalTeam() {
        if (lineups == null || getHomeTeam() == null) {
            return Collections.emptyList();
        }
        List<LineUpData> list = new ArrayList<>();
        int homeTeamId = getHomeTeam().getId().intValue();
        for (LineUpData lud : lineups) {
            if (lud.getTypeId().intValue() == LineUpType.BENCH && lud.getTeamId().intValue() == homeTeamId) {
                list.add(lud);
            }
        }
        return list;
    }

    public List<LineUpData> getBenchVisitorTeam() {
        if (lineups == null || getAwayTeam() == null) {
            return Collections.emptyList();
        }
        List<LineUpData> list = new ArrayList<>();
        int awayTeamId = getAwayTeam().getId().intValue();
        for (LineUpData lud : lineups) {
            if (lud.getTypeId().intValue() == LineUpType.BENCH && lud.getTeamId().intValue() == awayTeamId) {
                list.add(lud);
            }
        }
        return list;
    }

    public List<EventData> getEvents() {
        return events == null ? null : Collections.unmodifiableList(events);
    }

    public List<StatisticsData> getStatistics() {
        return statistics == null ? null : Collections.unmodifiableList(statistics);
    }

    public StatisticsData getStatisticDataByTypeAndTeamId(int teamId, int type) {
        if (statistics == null) {
            return null;
        }
        for (StatisticsData sd : statistics) {
            if (sd.getParticipantId().intValue() == teamId && sd.getTypeId().intValue() == type)
                return sd;
        }
        return null;
    }

    public List<Period> getPeriods() {
        return periods == null ? null : Collections.unmodifiableList(periods);
    }

    public List<Team> getParticipants() {
        return participants == null ? null : Collections.unmodifiableList(participants);
    }

    public Team getHomeTeam() {
        if (participants == null) {
            return null;
        }
        for (Team team : participants) {
            if (team.getMeta() != null && team.getMeta().getLocation().equals("home"))
                return team;
        }
        return null;
    }

    public Team getAwayTeam() {
        if (participants == null) {
            return null;
        }
        for (Team team : participants) {
            if (team.getMeta() != null && team.getMeta().getLocation().equals("away"))
                return team;
        }
        return null;
    }

    public List<Scores> getScores() {
        return scores == null ? null : Collections.unmodifiableList(scores);
    }

    public List<Comment> getComments() {
        return comments == null ? null : Collections.unmodifiableList(comments);
    }

    public int getCurrentLocalTeamGoals() {
        if (scores != null) {
            for (Scores score : scores) {
                if (score.getDescription().equalsIgnoreCase("CURRENT") && score.getScore() != null && score.getScore().getParticipant().equalsIgnoreCase("home"))
                    return score.getScore().getGoals().intValue();
            }
        }
        return 0;
    }

    public int getCurrentVisitorTeamGoals() {
        if (scores != null) {
            for (Scores score : scores) {
                if (score.getDescription().equalsIgnoreCase("CURRENT") && score.getScore() != null && score.getScore().getParticipant().equalsIgnoreCase("away"))
                    return score.getScore().getGoals().intValue();
            }
        }
        return 0;
    }

    public boolean isLiveMatch() {
        return state != null && MatchStatus.isLiveMatch(state.getId().intValue());
    }

    public boolean isMatchFinished() {
        return state != null && MatchStatus.isMatchFinished(state.getId().intValue());
    }

    public Period getCurrentPeriod() {
        if (periods != null && !periods.isEmpty()) {
            for (Period period : periods) {
                if (period.getTypeId() != null && state != null && state.getType() != null && state.getType().getId() != null) {
                    if (period.getTypeId().equals(state.getType().getId())) {
                        return period;
                    }
                }
            }
        }
        return null;
    }
}
