package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.football.model.coach.Coach;
import es.com.kete1987.sportmonks.library.football.model.comments.Comment;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.sport.Sport;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import es.com.kete1987.sportmonks.library.football.model.stage.StageAggregate;
import es.com.kete1987.sportmonks.library.football.model.standings.StandingsGroup;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import es.com.kete1987.sportmonks.library.football.util.LineUpType;
import es.com.kete1987.sportmonks.library.football.util.MatchStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;
import org.jetbrains.annotations.Nullable;

public class MatchDetail extends Match {
    private Venue venue;
    private State state;
    private List<LineUpData> lineups;
    @Nullable
    private List<Formation> formations;
    private List<EventData> events;
    private List<StatisticsData> statistics;
    private List<Period> periods;
    private List<Team> participants;
    private List<Scores> scores;

    private List<Comment> comments;

    @Nullable
    private List<FixtureMetadata> metadata;
    @Nullable
    private List<FixtureSidelined> sidelined;
    @Nullable
    private List<FixtureReferee> referees;
    @Nullable
    private List<Coach> coaches;
    @Nullable
    private List<EventData> timeline;
    @Nullable
    @SerializedName(value = "weatherreport", alternate = {"weatherReport", "weather_report"})
    private WeatherReport weatherReport;
    @Nullable
    private Sport sport;
    @Nullable
    private League league;
    @Nullable
    private SeasonData season;
    @Nullable
    private Stage stage;
    @Nullable
    private Round round;
    @Nullable
    private StandingsGroup group;
    @Nullable
    private StageAggregate aggregate;
    @Nullable
    @SerializedName(value = "tvstations", alternate = {"tvStations", "tv_stations"})
    private List<FixtureTvStation> tvStations;
    @Nullable
    private List<Odd> odds;

    public MatchDetail() {
    }

    // MatchDetail adds enrichment fields (lineups, scores, etc.) but identity
    // is still determined by the match id inherited from Match.
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Venue getVenue() {
        return venue;
    }

    public State getState() {
        return state;
    }

    public List<LineUpData> getLineups() {
        return ModelCollections.unmodifiable(lineups);
    }

    /**
     * @return unmodifiable formations in API order, null when absent or explicitly null,
     *         or an empty list when the API returns an empty relation.
     *         Match {@link Formation#getParticipantId()} to {@link LineUpData#getTeamId()}.
     */
    @Nullable
    public List<Formation> getFormations() {
        return ModelCollections.unmodifiable(formations);
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
        return ModelCollections.unmodifiable(events);
    }

    public List<StatisticsData> getStatistics() {
        return ModelCollections.unmodifiable(statistics);
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
        return ModelCollections.unmodifiable(periods);
    }

    public List<Team> getParticipants() {
        return ModelCollections.unmodifiable(participants);
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
        return ModelCollections.unmodifiable(scores);
    }

    public List<Comment> getComments() {
        return ModelCollections.unmodifiable(comments);
    }

    /**
     * @return unmodifiable metadata entries (include {@code metadata}), null when the include was
     *         not requested or is null, or an empty list when the API returns an empty relation.
     */
    @Nullable
    public List<FixtureMetadata> getMetadata() {
        return ModelCollections.unmodifiable(metadata);
    }

    /**
     * @param typeId metadata type ID (e.g. 159 for formations)
     * @return the first metadata entry of that type, or null when absent or not included
     */
    @Nullable
    public FixtureMetadata getMetadataByTypeId(long typeId) {
        if (metadata == null) {
            return null;
        }
        for (FixtureMetadata entry : metadata) {
            if (entry != null && entry.getTypeId() != null && entry.getTypeId() == typeId) {
                return entry;
            }
        }
        return null;
    }

    /**
     * @return unmodifiable sidelined players (include {@code sidelined}), null when the include was
     *         not requested or is null, or an empty list when the API returns an empty relation.
     */
    @Nullable
    public List<FixtureSidelined> getSidelined() {
        return ModelCollections.unmodifiable(sidelined);
    }

    /**
     * @return unmodifiable match officials (include {@code referees}), null when the include was
     *         not requested or is null, or an empty list when the API returns an empty relation.
     *         Use {@link FixtureReferee#getTypeId()} with
     *         {@link es.com.kete1987.sportmonks.library.football.util.RefereeType} to tell roles apart.
     */
    @Nullable
    public List<FixtureReferee> getReferees() {
        return ModelCollections.unmodifiable(referees);
    }

    /**
     * @return the main referee assignment, or null when absent or {@code referees} was not included
     */
    @Nullable
    public FixtureReferee getMainReferee() {
        if (referees == null) {
            return null;
        }
        for (FixtureReferee referee : referees) {
            if (referee != null && referee.isMainReferee()) {
                return referee;
            }
        }
        return null;
    }

    /**
     * @return unmodifiable coaches (include {@code coaches}), null when the include was not
     *         requested or is null, or an empty list when the API returns an empty relation.
     *         {@link Coach#getMeta()} links each coach to its team.
     */
    @Nullable
    public List<Coach> getCoaches() {
        return ModelCollections.unmodifiable(coaches);
    }

    /**
     * @return unmodifiable timeline events in API order (include {@code timeline}), null when the
     *         include was not requested or is null, or an empty list when the API returns an empty
     *         relation. Same shape as {@link #getEvents()}, with {@code section} {@code "timeline"}.
     */
    @Nullable
    public List<EventData> getTimeline() {
        return ModelCollections.unmodifiable(timeline);
    }

    /**
     * @return the weather report (include {@code weatherReport}), or null when the include was not
     *         requested or the API has no report for this fixture. See {@link WeatherReport} for
     *         the verification status of its shape.
     */
    @Nullable
    public WeatherReport getWeatherReport() {
        return weatherReport;
    }

    /** @return the sport (include {@code sport}), or null when the include was not requested. */
    @Nullable
    public Sport getSport() {
        return sport;
    }

    /** @return the league (include {@code league}), or null when the include was not requested. */
    @Nullable
    public League getLeague() {
        return league;
    }

    /** @return the season (include {@code season}), or null when the include was not requested. */
    @Nullable
    public SeasonData getSeason() {
        return season;
    }

    /** @return the stage (include {@code stage}), or null when the include was not requested. */
    @Nullable
    public Stage getStage() {
        return stage;
    }

    /** @return the round (include {@code round}), or null when absent or the fixture has no round. */
    @Nullable
    public Round getRound() {
        return round;
    }

    /** @return the cup group (include {@code group}), or null when the fixture belongs to no group. */
    @Nullable
    public StandingsGroup getGroup() {
        return group;
    }

    /** @return the two-leg aggregate (include {@code aggregate}), or null when the fixture has none. */
    @Nullable
    public StageAggregate getAggregate() {
        return aggregate;
    }

    /**
     * @return unmodifiable TV broadcasts (include {@code tvStations}), null when the include was not
     *         requested or is null, or an empty list when the API returns an empty relation. Each
     *         row is a pivot with the station id and the country id; the API names the JSON key
     *         {@code tvstations}.
     */
    @Nullable
    public List<FixtureTvStation> getTvStations() {
        return ModelCollections.unmodifiable(tvStations);
    }

    /**
     * @return unmodifiable pre-match odds (include {@code odds}), null when the include was not
     *         requested or is null, or an empty list when the API returns an empty relation. A
     *         top-flight fixture carries thousands of rows, so request it only when needed.
     */
    @Nullable
    public List<Odd> getOdds() {
        return ModelCollections.unmodifiable(odds);
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

    public int getPenaltyLocalTeamGoals() {
        return getPenaltyTeamGoals("home");
    }

    public int getPenaltyVisitorTeamGoals() {
        return getPenaltyTeamGoals("away");
    }

    private int getPenaltyTeamGoals(String participant) {
        if (scores != null) {
            for (Scores score : scores) {
                if ("PENALTY_SHOOTOUT".equalsIgnoreCase(score.getDescription())
                        && score.getScore() != null
                        && participant.equalsIgnoreCase(score.getScore().getParticipant()))
                    return score.getScore().getGoals().intValue();
            }
        }
        return 0;
    }

    public boolean hasPenaltyShootout() {
        if (scores != null) {
            for (Scores score : scores) {
                if ("PENALTY_SHOOTOUT".equalsIgnoreCase(score.getDescription()))
                    return true;
            }
        }
        return false;
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
