package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
import es.com.kete1987.sportmonks.library.common.util.Constants;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.comments.Comment;
import es.com.kete1987.sportmonks.library.football.model.comments.CommentsResponse;
import es.com.kete1987.sportmonks.library.football.model.expectedlineup.ExpectedLineup;
import es.com.kete1987.sportmonks.library.football.model.expectedlineup.ExpectedLineupsResponse;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.league.LeagueResponse;
import es.com.kete1987.sportmonks.library.football.model.league.LeaguesResponse;
import es.com.kete1987.sportmonks.library.football.model.match.MatchData;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.match.MatchsResponse;
import es.com.kete1987.sportmonks.library.football.model.match.State;
import es.com.kete1987.sportmonks.library.football.model.match.StateResponse;
import es.com.kete1987.sportmonks.library.football.model.match.StatesResponse;
import es.com.kete1987.sportmonks.library.football.model.matchfact.MatchFact;
import es.com.kete1987.sportmonks.library.football.model.matchfact.MatchFactsResponse;
import es.com.kete1987.sportmonks.library.football.model.coach.Coach;
import es.com.kete1987.sportmonks.library.football.model.coach.CoachResponse;
import es.com.kete1987.sportmonks.library.football.model.coach.CoachesResponse;
import es.com.kete1987.sportmonks.library.football.model.player.Player;
import es.com.kete1987.sportmonks.library.football.model.player.PlayerResponse;
import es.com.kete1987.sportmonks.library.football.model.player.PlayersResponse;
import es.com.kete1987.sportmonks.library.football.model.referee.Referee;
import es.com.kete1987.sportmonks.library.football.model.referee.RefereeResponse;
import es.com.kete1987.sportmonks.library.football.model.referee.RefereesResponse;
import es.com.kete1987.sportmonks.library.football.model.rival.Rival;
import es.com.kete1987.sportmonks.library.football.model.rival.RivalsResponse;
import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.rounds.RoundResponse;
import es.com.kete1987.sportmonks.library.football.model.rounds.RoundsResponse;
import es.com.kete1987.sportmonks.library.football.model.schedule.Schedule;
import es.com.kete1987.sportmonks.library.football.model.schedule.SchedulesResponse;
import es.com.kete1987.sportmonks.library.football.model.season.BracketsResponse;
import es.com.kete1987.sportmonks.library.football.model.season.KnockoutBracket;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonDataResponse;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonsResponse;
import es.com.kete1987.sportmonks.library.football.model.squad.SquadPlayer;
import es.com.kete1987.sportmonks.library.football.model.squad.SquadPlayersResponse;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import es.com.kete1987.sportmonks.library.football.model.stage.StageResponse;
import es.com.kete1987.sportmonks.library.football.model.stage.StagesResponse;
import es.com.kete1987.sportmonks.library.football.model.standings.Standings;
import es.com.kete1987.sportmonks.library.football.model.standings.StandingsResponse;
import es.com.kete1987.sportmonks.library.football.model.statistic.Statistic;
import es.com.kete1987.sportmonks.library.football.model.statistic.StatisticsResponse;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.team.TeamResponse;
import es.com.kete1987.sportmonks.library.football.model.team.TeamsResponse;
import es.com.kete1987.sportmonks.library.football.model.teamranking.TeamRanking;
import es.com.kete1987.sportmonks.library.football.model.teamranking.TeamRankingsResponse;
import es.com.kete1987.sportmonks.library.football.model.topscorers.TopScoresPlayer;
import es.com.kete1987.sportmonks.library.football.model.topscorers.TopScorersResponse;
import es.com.kete1987.sportmonks.library.football.model.totw.TeamOfTheWeek;
import es.com.kete1987.sportmonks.library.football.model.totw.TeamsOfTheWeekResponse;
import es.com.kete1987.sportmonks.library.football.model.transfer.Transfer;
import es.com.kete1987.sportmonks.library.football.model.transfer.TransferResponse;
import es.com.kete1987.sportmonks.library.football.model.transfer.TransferRumour;
import es.com.kete1987.sportmonks.library.football.model.transfer.TransferRumourResponse;
import es.com.kete1987.sportmonks.library.football.model.transfer.TransferRumoursResponse;
import es.com.kete1987.sportmonks.library.football.model.transfer.TransfersResponse;
import es.com.kete1987.sportmonks.library.football.model.tvstation.TvStation;
import es.com.kete1987.sportmonks.library.football.model.tvstation.TvStationResponse;
import es.com.kete1987.sportmonks.library.football.model.tvstation.TvStationsResponse;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;
import es.com.kete1987.sportmonks.library.football.model.venue.VenueDetailResponse;
import es.com.kete1987.sportmonks.library.football.model.venue.VenueResponse;
import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Football sub-API: fixtures, leagues, seasons, teams, standings, statistics, transfers,
 * venues, predictions, and all other football-domain endpoints.
 *
 * <p>Can be used standalone ({@code new FootballApi("apiKey")}) or via the
 * {@link SportMonksAPI} facade ({@code api.getFootball()}).
 */
public class FootballApi extends SportMonksApiBase {

    private final String footballBase;

    public FootballApi(String apiToken) {
        this(apiToken, null);
    }

    public FootballApi(String apiToken, String locale) {
        this(buildHttpClient(apiToken), Constants.BASE_URL_FOOTBALL, locale, new RateLimitTracker());
    }

    FootballApi(OkHttpClient client, String footballBase, String locale, RateLimitTracker tracker) {
        super(client, locale, tracker);
        this.footballBase = footballBase;
    }

    private HttpUrl.Builder footballUrl(String path) {
        return localeUrl(HttpUrl.parse(footballBase + path).newBuilder());
    }

    // -------------------------------------------------------------------------
    // Paginated fetch helpers
    // -------------------------------------------------------------------------

    private List<MatchDetail> fetchMatchList(HttpUrl url) throws IOException, SportMonksException {
        return fetchMatchList(url, 0);
    }

    private List<MatchDetail> fetchMatchList(HttpUrl url, int limit) throws IOException, SportMonksException {
        return fetchPaged(url, MatchsResponse.class, MatchsResponse::getData, MatchsResponse::getPagination, limit);
    }

    /**
     * Generic limit-aware paginator shared by the list endpoints. When {@code limit > 0} it shrinks
     * {@code per_page} (capped at 50, the API page size) so the first page already carries enough
     * rows, stops paginating as soon as {@code limit} is reached, and trims any overflow — avoiding
     * crawling every page just to keep the first N. A non-positive {@code limit} means "no limit"
     * (fetch all pages). The {@code dataFn}/{@code pagFn} extract the {@code data}/{@code pagination}
     * from each response so the same loop serves every {@code *Response} type.
     */
    private <T, R> List<T> fetchPaged(HttpUrl base, Class<R> type,
                                      Function<R, List<T>> dataFn, Function<R, Pagination> pagFn,
                                      int limit) throws IOException, SportMonksException {
        Gson g = gson();
        HttpUrl first = limit > 0
                ? base.newBuilder().addQueryParameter("per_page", String.valueOf(Math.min(limit, 50))).build()
                : base;
        R resp = g.fromJson(execute(first), type);
        List<T> data = resp != null ? dataFn.apply(resp) : null;
        if (data == null) return new ArrayList<>();
        List<T> all = new ArrayList<>(data);
        int page = 1;
        Pagination pg = pagFn.apply(resp);
        while ((limit <= 0 || all.size() < limit) && pg != null && pg.hasMore()) {
            page++;
            HttpUrl paged = first.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), type);
            if (resp == null) break;
            List<T> more = dataFn.apply(resp);
            if (more != null) all.addAll(more);
            pg = pagFn.apply(resp);
        }
        if (limit > 0 && all.size() > limit) {
            return new ArrayList<>(all.subList(0, limit));
        }
        return all;
    }

    private List<TopScoresPlayer> fetchTopScoresList(HttpUrl url) throws IOException, SportMonksException {
        return fetchTopScoresList(url, 0);
    }

    /**
     * Like {@link #fetchTopScoresList(HttpUrl)} but caps the result at {@code limit} elements.
     * When {@code limit > 0} it shrinks {@code per_page} (max 50, the API page size) so the first
     * page already carries enough rows, stops paginating as soon as {@code limit} is reached, and
     * trims any overflow — avoiding fetching every page just to keep the top N. A non-positive
     * {@code limit} means "no limit" (fetch all pages, original behaviour).
     */
    private List<TopScoresPlayer> fetchTopScoresList(HttpUrl url, int limit) throws IOException, SportMonksException {
        Gson g = gson();
        HttpUrl first = limit > 0
                ? url.newBuilder().addQueryParameter("per_page", String.valueOf(Math.min(limit, 50))).build()
                : url;
        TopScorersResponse resp = g.fromJson(execute(first), TopScorersResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<TopScoresPlayer> list = new ArrayList<>(resp.getData());
        int page = 1;
        while ((limit <= 0 || list.size() < limit)
                && resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = first.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), TopScorersResponse.class);
            if (resp.getData() != null) list.addAll(resp.getData());
        }
        if (limit > 0 && list.size() > limit) {
            return new ArrayList<>(list.subList(0, limit));
        }
        return list;
    }

    // -------------------------------------------------------------------------
    // Matches / Fixtures
    // -------------------------------------------------------------------------

    /**
     * Livescores from the {@code /livescores} endpoint: fixtures within a ~15 minute
     * window before and after kickoff (about to start, in play, or just finished),
     * <em>not</em> every fixture scheduled for the calendar day. For the full day's
     * fixtures use {@link #getMatchesByDate(String, String...)} with today's date.
     */
    public List<MatchDetail> getLivescores(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("livescores"), includes).build();
        return fetchMatchList(url);
    }

    /**
     * @deprecated misleading name: this calls {@code /livescores}, which returns the
     * ~15 minute live window, not the matches of the current day. Use
     * {@link #getLivescores(String...)} (or {@link #getMatchesByDate(String, String...)}
     * for an actual day). Still delegates for now, but scheduled for removal in a future
     * major release.
     */
    @Deprecated(since = "3.1.0", forRemoval = true)
    public List<MatchDetail> getTodayMatches(String... includes) throws IOException, SportMonksException {
        return getLivescores(includes);
    }

    public List<MatchDetail> getLivescoresFiltered(String[] matchIds, String... includes) throws IOException, SportMonksException {
        if (matchIds != null && matchIds.length > 0) {
            HttpUrl url = withIncludes(footballUrl("livescores/multi/" + String.join(",", matchIds)), includes).build();
            return fetchMatchList(url);
        }
        return getLivescores(includes);
    }

    /**
     * @deprecated misleading name, see {@link #getTodayMatches(String...)}. Use
     * {@link #getLivescoresFiltered(String[], String...)}.
     */
    @Deprecated(since = "3.1.0", forRemoval = true)
    public List<MatchDetail> getTodayMatchesFiltered(String[] matchIds, String... includes) throws IOException, SportMonksException {
        return getLivescoresFiltered(matchIds, includes);
    }

    public List<MatchDetail> getLiveMatches(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("livescores/inplay"), includes).build();
        return gson().fromJson(execute(url), MatchsResponse.class).getData();
    }

    public List<MatchDetail> getMatchesByDate(String date, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/date/" + date), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getMatchesByDateRange(String beginDate, String endDate) throws IOException, SportMonksException {
        return getMatchesByDateRange(beginDate, endDate, "participants");
    }

    public List<MatchDetail> getMatchesByDateRange(String beginDate, String endDate, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/between/" + beginDate + "/" + endDate), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getMatchesByDateRangeForTeam(String beginDate, String endDate, String teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/between/" + beginDate + "/" + endDate + "/" + teamId), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getMatchesByMultipleIDs(String[] fixtureIds, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/multi/" + String.join(",", fixtureIds)), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getLatestUpdatedLivescores(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("livescores/latest"), includes).build();
        return gson().fromJson(execute(url), MatchsResponse.class).getData();
    }

    public List<MatchDetail> getFixturesByHeadToHead(long team1Id, long team2Id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/head-to-head/" + team1Id + "/" + team2Id), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> searchFixtures(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/search/" + name), includes).build();
        return fetchMatchList(url);
    }

    /** Fixture search capped at {@code limit} results (no cap when {@code limit <= 0}). */
    public List<MatchDetail> searchFixtures(String name, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/search/" + name), includes).build();
        return fetchMatchList(url, limit);
    }

    public List<MatchDetail> getLatestUpdatedFixtures(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/latest"), includes).build();
        return gson().fromJson(execute(url), MatchsResponse.class).getData();
    }

    public List<MatchDetail> getUpcomingFixturesByMarket(long marketId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/upcoming/markets/" + marketId), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getUpcomingFixturesByTvStation(long tvStationId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/upcoming/tv-stations/" + tvStationId), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getPastFixturesByTvStation(long tvStationId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/past/tv-stations/" + tvStationId), includes).build();
        return fetchMatchList(url);
    }

    public MatchDetail getMatchDetail(String matchId) throws IOException, SportMonksException {
        return getMatchDetail(matchId, "venue", "state", "lineups", "events", "statistics", "periods", "participants", "scores");
    }

    public MatchDetail getMatchDetail(String matchId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/" + matchId), includes).build();
        return gson().fromJson(execute(url), MatchData.class).getMatchDetail();
    }

    // -------------------------------------------------------------------------
    // Seasons
    // -------------------------------------------------------------------------

    public List<SeasonData> getSeasons() throws IOException, SportMonksException {
        HttpUrl base = footballUrl("seasons").build();
        Gson g = gson();
        SeasonsResponse resp = g.fromJson(execute(base), SeasonsResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<SeasonData> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), SeasonsResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public SeasonData getSeasonById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("seasons/" + id), includes).build();
        return gson().fromJson(execute(url), SeasonDataResponse.class).getSeasonData();
    }

    public List<SeasonData> getSeasonsByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("seasons/teams/" + teamId), includes).build();
        Gson g = gson();
        SeasonsResponse resp = g.fromJson(execute(base), SeasonsResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<SeasonData> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), SeasonsResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public List<SeasonData> searchSeasons(String name, String... includes) throws IOException, SportMonksException {
        return searchSeasons(name, 0, includes);
    }

    /** Season search capped at {@code limit} results. */
    public List<SeasonData> searchSeasons(String name, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("seasons/search/" + name), includes).build();
        return fetchPaged(base, SeasonsResponse.class, SeasonsResponse::getData, SeasonsResponse::getPagination, limit);
    }

    public KnockoutBracket getBracketsBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("seasons/" + seasonId + "/brackets"), includes).build();
        return gson().fromJson(execute(url), BracketsResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Top Scorers
    // -------------------------------------------------------------------------

    private static final String TOPSCORERS_SEASONS = "topscorers/seasons/";

    public List<TopScoresPlayer> getTopScores(String seasonId) throws IOException, SportMonksException {
        return getTopScores(seasonId, "season", "stage", "player", "type", "participant");
    }

    public List<TopScoresPlayer> getTopScores(String seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl(TOPSCORERS_SEASONS + seasonId), includes).build();
        return fetchTopScoresList(url);
    }

    /**
     * Top scorers of a season, capped at {@code limit} entries (e.g. {@code 25} for the top 25).
     * The endpoint returns players already ordered by tally, so this yields the leaders without
     * fetching every page. {@code limit <= 0} means no cap (all pages). Pass the includes you need
     * (e.g. {@code "player"}) — unlike {@link #getTopScores(String)} this overload has no defaults.
     */
    public List<TopScoresPlayer> getTopScores(String seasonId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl(TOPSCORERS_SEASONS + seasonId), includes).build();
        return fetchTopScoresList(url, limit);
    }

    public List<TopScoresPlayer> getTopScoresFiltered(String seasonId, int typeId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl(TOPSCORERS_SEASONS + seasonId), includes)
                .addQueryParameter("filters", "seasontopscorerTypes:" + typeId)
                .build();
        return fetchTopScoresList(url);
    }

    /** Filtered season top scorers, capped at {@code limit} entries. See {@link #getTopScores(String, int, String...)}. */
    public List<TopScoresPlayer> getTopScoresFiltered(String seasonId, int typeId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl(TOPSCORERS_SEASONS + seasonId), includes)
                .addQueryParameter("filters", "seasontopscorerTypes:" + typeId)
                .build();
        return fetchTopScoresList(url, limit);
    }

    public List<TopScoresPlayer> getTopScoresByStage(String stageId) throws IOException, SportMonksException {
        return getTopScoresByStage(stageId, "season", "stage", "player", "type");
    }

    public List<TopScoresPlayer> getTopScoresByStage(String stageId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("topscorers/stages/" + stageId), includes).build();
        return fetchTopScoresList(url);
    }

    /** Stage top scorers, capped at {@code limit} entries. See {@link #getTopScores(String, int, String...)}. */
    public List<TopScoresPlayer> getTopScoresByStage(String stageId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("topscorers/stages/" + stageId), includes).build();
        return fetchTopScoresList(url, limit);
    }

    // -------------------------------------------------------------------------
    // Standings
    // -------------------------------------------------------------------------

    public List<Standings> getStandings(String seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings/seasons/" + seasonId), includes).build();
        StandingsResponse resp = gson().fromJson(execute(url), StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public TreeMap<String, List<Standings>> getStandingsCup(String seasonId, String... includes) throws IOException, SportMonksException {
        TreeMap<String, List<Standings>> map = new TreeMap<>();
        for (Standings s : getStandings(seasonId, includes)) {
            map.computeIfAbsent(s.getGroup().getName(), k -> new ArrayList<>()).add(s);
        }
        return map;
    }

    public List<Standings> getAllStandings(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings"), includes).build();
        StandingsResponse resp = gson().fromJson(execute(url), StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Standings> getStandingsByRound(long roundId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings/rounds/" + roundId), includes).build();
        StandingsResponse resp = gson().fromJson(execute(url), StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Standings> getStandingCorrectionsBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings/corrections/seasons/" + seasonId), includes).build();
        StandingsResponse resp = gson().fromJson(execute(url), StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Standings> getLiveStandingsByLeague(long leagueId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings/live/leagues/" + leagueId), includes).build();
        StandingsResponse resp = gson().fromJson(execute(url), StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Standings> getGroupedStandingsByRound(long roundId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings/rounds/" + roundId + "/grouped"), includes).build();
        StandingsResponse resp = gson().fromJson(execute(url), StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Statistics
    // -------------------------------------------------------------------------

    public List<Statistic> getSeasonStatsByParticipant(long participantId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("statistics/seasons/participants/" + participantId), includes).build();
        StatisticsResponse resp = gson().fromJson(execute(url), StatisticsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Statistic> getStatsByStage(long stageId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("statistics/stages/" + stageId), includes).build();
        StatisticsResponse resp = gson().fromJson(execute(url), StatisticsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Statistic> getStatsByRound(long roundId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("statistics/rounds/" + roundId), includes).build();
        StatisticsResponse resp = gson().fromJson(execute(url), StatisticsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Schedules
    // -------------------------------------------------------------------------

    public List<Schedule> getSchedulesBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("schedules/seasons/" + seasonId), includes).build();
        SchedulesResponse resp = gson().fromJson(execute(url), SchedulesResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Schedule> getSchedulesByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("schedules/teams/" + teamId), includes).build();
        SchedulesResponse resp = gson().fromJson(execute(url), SchedulesResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Schedule> getSchedulesBySeasonAndTeam(long seasonId, long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("schedules/seasons/" + seasonId + "/teams/" + teamId), includes).build();
        SchedulesResponse resp = gson().fromJson(execute(url), SchedulesResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Transfers
    // -------------------------------------------------------------------------

    public List<Transfer> getAllTransfers(String... includes) throws IOException, SportMonksException {
        return getAllTransfers(0, includes);
    }

    /** All transfers capped at {@code limit} (e.g. the latest N), without crawling every page. */
    public List<Transfer> getAllTransfers(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("transfers"), includes).build();
        return fetchPaged(base, TransfersResponse.class, TransfersResponse::getData, TransfersResponse::getPagination, limit);
    }

    public Transfer getTransferById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfers/" + id), includes).build();
        TransferResponse resp = gson().fromJson(execute(url), TransferResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Transfer> getLatestTransfers(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfers/latest"), includes).build();
        TransfersResponse resp = gson().fromJson(execute(url), TransfersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Transfer> getTransfersByDateRange(String startDate, String endDate, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfers/between/" + startDate + "/" + endDate), includes).build();
        TransfersResponse resp = gson().fromJson(execute(url), TransfersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Transfer> getTransfersByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        return getTransfersByTeam(teamId, 0, includes);
    }

    /** Transfers of a team capped at {@code limit}, without crawling every page. */
    public List<Transfer> getTransfersByTeam(long teamId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("transfers/teams/" + teamId), includes).build();
        return fetchPaged(base, TransfersResponse.class, TransfersResponse::getData, TransfersResponse::getPagination, limit);
    }

    public List<Transfer> getTransfersByPlayer(long playerId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfers/players/" + playerId), includes).build();
        TransfersResponse resp = gson().fromJson(execute(url), TransfersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Transfer Rumours
    // -------------------------------------------------------------------------

    public List<TransferRumour> getAllTransferRumours(String... includes) throws IOException, SportMonksException {
        return getAllTransferRumours(0, includes);
    }

    /** All transfer rumours capped at {@code limit}, without crawling every page. */
    public List<TransferRumour> getAllTransferRumours(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("transfer-rumours"), includes).build();
        return fetchPaged(base, TransferRumoursResponse.class, TransferRumoursResponse::getData, TransferRumoursResponse::getPagination, limit);
    }

    public TransferRumour getTransferRumourById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfer-rumours/" + id), includes).build();
        TransferRumourResponse resp = gson().fromJson(execute(url), TransferRumourResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<TransferRumour> getTransferRumoursByDateRange(String startDate, String endDate, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfer-rumours/between/" + startDate + "/" + endDate), includes).build();
        TransferRumoursResponse resp = gson().fromJson(execute(url), TransferRumoursResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<TransferRumour> getTransferRumoursByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfer-rumours/teams/" + teamId), includes).build();
        TransferRumoursResponse resp = gson().fromJson(execute(url), TransferRumoursResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<TransferRumour> getTransferRumoursByPlayer(long playerId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfer-rumours/players/" + playerId), includes).build();
        TransferRumoursResponse resp = gson().fromJson(execute(url), TransferRumoursResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Rivals
    // -------------------------------------------------------------------------

    public List<Rival> getAllRivals(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rivals"), includes).build();
        RivalsResponse resp = gson().fromJson(execute(url), RivalsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Rival> getRivalsByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rivals/teams/" + teamId), includes).build();
        RivalsResponse resp = gson().fromJson(execute(url), RivalsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Commentaries
    // -------------------------------------------------------------------------

    public List<Comment> getAllCommentaries(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("commentaries"), includes).build();
        CommentsResponse resp = gson().fromJson(execute(url), CommentsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Comment> getCommentaryByFixture(long fixtureId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("commentaries/fixtures/" + fixtureId), includes).build();
        CommentsResponse resp = gson().fromJson(execute(url), CommentsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // TV Stations
    // -------------------------------------------------------------------------

    public List<TvStation> getAllTvStations(String... includes) throws IOException, SportMonksException {
        return getAllTvStations(0, includes);
    }

    /** All TV stations capped at {@code limit}, without crawling the whole catalogue. */
    public List<TvStation> getAllTvStations(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("tv-stations"), includes).build();
        return fetchPaged(base, TvStationsResponse.class, TvStationsResponse::getData, TvStationsResponse::getPagination, limit);
    }

    public TvStation getTvStationById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("tv-stations/" + id), includes).build();
        TvStationResponse resp = gson().fromJson(execute(url), TvStationResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<TvStation> getTvStationsByFixture(long fixtureId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("tv-stations/fixtures/" + fixtureId), includes).build();
        TvStationsResponse resp = gson().fromJson(execute(url), TvStationsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Venues
    // -------------------------------------------------------------------------

    public List<Venue> getVenues(String seasonId) throws IOException, SportMonksException {
        HttpUrl url = footballUrl("venues/seasons/" + seasonId).build();
        VenueResponse resp = gson().fromJson(execute(url), VenueResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Venue> getAllVenues(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("venues"), includes).build();
        VenueResponse resp = gson().fromJson(execute(url), VenueResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public Venue getVenueById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("venues/" + id), includes).build();
        VenueDetailResponse resp = gson().fromJson(execute(url), VenueDetailResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Venue> searchVenues(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("venues/search/" + name), includes).build();
        VenueResponse resp = gson().fromJson(execute(url), VenueResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Match Facts (beta)
    // -------------------------------------------------------------------------

    public List<MatchFact> getAllMatchFacts(String... includes) throws IOException, SportMonksException {
        return getAllMatchFacts(0, includes);
    }

    /** All match facts capped at {@code limit}, without crawling every page. */
    public List<MatchFact> getAllMatchFacts(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("match-facts"), includes).build();
        return fetchPaged(base, MatchFactsResponse.class, MatchFactsResponse::getData, MatchFactsResponse::getPagination, limit);
    }

    public List<MatchFact> getMatchFactsByFixture(long fixtureId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("match-facts/fixtures/" + fixtureId), includes).build();
        MatchFactsResponse resp = gson().fromJson(execute(url), MatchFactsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<MatchFact> getMatchFactsByDateRange(String startDate, String endDate, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("match-facts/between/" + startDate + "/" + endDate), includes).build();
        MatchFactsResponse resp = gson().fromJson(execute(url), MatchFactsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<MatchFact> getMatchFactsByLeague(long leagueId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("match-facts/leagues/" + leagueId), includes).build();
        MatchFactsResponse resp = gson().fromJson(execute(url), MatchFactsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Team of the Week (beta)
    // -------------------------------------------------------------------------

    public List<TeamOfTheWeek> getAllTeamsOfTheWeek(String... includes) throws IOException, SportMonksException {
        return getAllTeamsOfTheWeek(0, includes);
    }

    /** All teams of the week capped at {@code limit}, without crawling every page. */
    public List<TeamOfTheWeek> getAllTeamsOfTheWeek(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("team-of-the-week"), includes).build();
        return fetchPaged(base, TeamsOfTheWeekResponse.class, TeamsOfTheWeekResponse::getData, TeamsOfTheWeekResponse::getPagination, limit);
    }

    public List<TeamOfTheWeek> getTeamOfTheWeekByRound(long roundId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("team-of-the-week/rounds/" + roundId), includes).build();
        TeamsOfTheWeekResponse resp = gson().fromJson(execute(url), TeamsOfTheWeekResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<TeamOfTheWeek> getLatestTeamOfTheWeekByLeague(long leagueId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("team-of-the-week/leagues/" + leagueId + "/latest"), includes).build();
        TeamsOfTheWeekResponse resp = gson().fromJson(execute(url), TeamsOfTheWeekResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Team Rankings (beta)
    // -------------------------------------------------------------------------

    public List<TeamRanking> getAllTeamRankings(String... includes) throws IOException, SportMonksException {
        return getAllTeamRankings(0, includes);
    }

    /** Team rankings capped at {@code limit} (e.g. top 20); the endpoint is already ordered by rank. */
    public List<TeamRanking> getAllTeamRankings(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("team-rankings"), includes).build();
        return fetchPaged(base, TeamRankingsResponse.class, TeamRankingsResponse::getData, TeamRankingsResponse::getPagination, limit);
    }

    public List<TeamRanking> getTeamRankingsByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("team-rankings/teams/" + teamId), includes).build();
        TeamRankingsResponse resp = gson().fromJson(execute(url), TeamRankingsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<TeamRanking> getTeamRankingsByDate(String date, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("team-rankings/date/" + date), includes).build();
        TeamRankingsResponse resp = gson().fromJson(execute(url), TeamRankingsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Expected Lineups (beta)
    // -------------------------------------------------------------------------

    public List<ExpectedLineup> getExpectedLineupByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("expected-lineups/teams/" + teamId), includes).build();
        ExpectedLineupsResponse resp = gson().fromJson(execute(url), ExpectedLineupsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<ExpectedLineup> getExpectedLineupsByPlayer(long playerId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("expected-lineups/players/" + playerId), includes).build();
        ExpectedLineupsResponse resp = gson().fromJson(execute(url), ExpectedLineupsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Teams
    // -------------------------------------------------------------------------

    public List<Team> getAllTeams(String... includes) throws IOException, SportMonksException {
        return getAllTeams(0, includes);
    }

    /** All teams capped at {@code limit}, without crawling the whole catalogue. */
    public List<Team> getAllTeams(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("teams"), includes).build();
        return fetchPaged(base, TeamsResponse.class, TeamsResponse::getData, TeamsResponse::getPagination, limit);
    }

    public List<Team> getTeamsBySeasonId(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("teams/seasons/" + seasonId), includes).build();
        TeamsResponse resp = gson().fromJson(execute(url), TeamsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Team> getTeamsByCountry(long countryId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("teams/countries/" + countryId), includes).build();
        TeamsResponse resp = gson().fromJson(execute(url), TeamsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public Team getTeamById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("teams/" + id), includes).build();
        TeamResponse resp = gson().fromJson(execute(url), TeamResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Team> searchTeams(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("teams/search/" + name), includes).build();
        TeamsResponse resp = gson().fromJson(execute(url), TeamsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    /** Team search capped at {@code limit} results. */
    public List<Team> searchTeams(String name, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("teams/search/" + name), includes).build();
        return fetchPaged(base, TeamsResponse.class, TeamsResponse::getData, TeamsResponse::getPagination, limit);
    }

    // -------------------------------------------------------------------------
    // Leagues
    // -------------------------------------------------------------------------

    public List<League> getAllLeagues(String... includes) throws IOException, SportMonksException {
        return getAllLeagues(0, includes);
    }

    /** All leagues capped at {@code limit}, without crawling the whole catalogue. */
    public List<League> getAllLeagues(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues"), includes).build();
        return fetchPaged(base, LeaguesResponse.class, LeaguesResponse::getData, LeaguesResponse::getPagination, limit);
    }

    public League getLeagueById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("leagues/" + id), includes).build();
        LeagueResponse resp = gson().fromJson(execute(url), LeagueResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<League> getLiveLeagues(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/live"), includes).build();
        Gson g = gson();
        LeaguesResponse resp = g.fromJson(execute(base), LeaguesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<League> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), LeaguesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public List<League> getLeaguesByDate(String date, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/date/" + date), includes).build();
        Gson g = gson();
        LeaguesResponse resp = g.fromJson(execute(base), LeaguesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<League> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), LeaguesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public List<League> getLeaguesByCountry(long countryId, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/countries/" + countryId), includes).build();
        Gson g = gson();
        LeaguesResponse resp = g.fromJson(execute(base), LeaguesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<League> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), LeaguesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public List<League> searchLeagues(String name, String... includes) throws IOException, SportMonksException {
        return searchLeagues(name, 0, includes);
    }

    /** League search capped at {@code limit} results. */
    public List<League> searchLeagues(String name, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/search/" + name), includes).build();
        return fetchPaged(base, LeaguesResponse.class, LeaguesResponse::getData, LeaguesResponse::getPagination, limit);
    }

    public List<League> getLeaguesByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/teams/" + teamId), includes).build();
        Gson g = gson();
        LeaguesResponse resp = g.fromJson(execute(base), LeaguesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<League> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), LeaguesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public List<League> getCurrentLeaguesByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/teams/" + teamId + "/current"), includes).build();
        Gson g = gson();
        LeaguesResponse resp = g.fromJson(execute(base), LeaguesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<League> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), LeaguesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    // -------------------------------------------------------------------------
    // Stages
    // -------------------------------------------------------------------------

    public List<Stage> getAllStages(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("stages"), includes).build();
        StagesResponse resp = gson().fromJson(execute(url), StagesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Stage> getStagesBySeasonId(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("stages/seasons/" + seasonId), includes).build();
        StagesResponse resp = gson().fromJson(execute(url), StagesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public Stage getStageById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("stages/" + id), includes).build();
        StageResponse resp = gson().fromJson(execute(url), StageResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Stage> searchStages(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("stages/search/" + name), includes).build();
        StagesResponse resp = gson().fromJson(execute(url), StagesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Rounds
    // -------------------------------------------------------------------------

    public List<Round> getAllRounds(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rounds"), includes).build();
        RoundsResponse resp = gson().fromJson(execute(url), RoundsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Round> getRoundsBySeasonId(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rounds/seasons/" + seasonId), includes).build();
        RoundsResponse resp = gson().fromJson(execute(url), RoundsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public Round getRoundById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rounds/" + id), includes).build();
        RoundResponse resp = gson().fromJson(execute(url), RoundResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Round> searchRounds(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rounds/search/" + name), includes).build();
        RoundsResponse resp = gson().fromJson(execute(url), RoundsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // States
    // -------------------------------------------------------------------------

    public List<State> getAllStates() throws IOException, SportMonksException {
        HttpUrl url = footballUrl("states").build();
        return gson().fromJson(execute(url), StatesResponse.class).getData();
    }

    public State getStateById(long id) throws IOException, SportMonksException {
        HttpUrl url = footballUrl("states/" + id).build();
        return gson().fromJson(execute(url), StateResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Squads
    // -------------------------------------------------------------------------

    public List<SquadPlayer> getSquadByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("squads/teams/" + teamId), includes).build();
        SquadPlayersResponse resp = gson().fromJson(execute(url), SquadPlayersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<SquadPlayer> getExtendedSquadByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("squads/teams/" + teamId + "/extended"), includes).build();
        SquadPlayersResponse resp = gson().fromJson(execute(url), SquadPlayersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<SquadPlayer> getSquadBySeasonAndTeam(long seasonId, long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("squads/seasons/" + seasonId + "/teams/" + teamId), includes).build();
        SquadPlayersResponse resp = gson().fromJson(execute(url), SquadPlayersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Players
    // -------------------------------------------------------------------------

    public List<Player> getAllPlayers(String... includes) throws IOException, SportMonksException {
        return getAllPlayers(0, includes);
    }

    /** All players capped at {@code limit}. Strongly recommended: the full catalogue is huge. */
    public List<Player> getAllPlayers(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("players"), includes).build();
        return fetchPaged(base, PlayersResponse.class, PlayersResponse::getData, PlayersResponse::getPagination, limit);
    }

    public Player getPlayerById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("players/" + id), includes).build();
        PlayerResponse resp = gson().fromJson(execute(url), PlayerResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Player> getPlayersByCountry(long countryId, String... includes) throws IOException, SportMonksException {
        return getPlayersByCountry(countryId, 0, includes);
    }

    /** Players of a country capped at {@code limit}, without crawling every page. */
    public List<Player> getPlayersByCountry(long countryId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("players/countries/" + countryId), includes).build();
        return fetchPaged(base, PlayersResponse.class, PlayersResponse::getData, PlayersResponse::getPagination, limit);
    }

    public List<Player> searchPlayers(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("players/search/" + name), includes).build();
        PlayersResponse resp = gson().fromJson(execute(url), PlayersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    /** Player search capped at {@code limit} results. */
    public List<Player> searchPlayers(String name, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("players/search/" + name), includes).build();
        return fetchPaged(base, PlayersResponse.class, PlayersResponse::getData, PlayersResponse::getPagination, limit);
    }

    public List<Player> getLatestUpdatedPlayers(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("players/latest"), includes).build();
        PlayersResponse resp = gson().fromJson(execute(url), PlayersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Coaches
    // -------------------------------------------------------------------------

    public List<Coach> getAllCoaches(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("coaches"), includes).build();
        Gson g = gson();
        CoachesResponse resp = g.fromJson(execute(base), CoachesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<Coach> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), CoachesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public Coach getCoachById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("coaches/" + id), includes).build();
        CoachResponse resp = gson().fromJson(execute(url), CoachResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Coach> getCoachesByCountry(long countryId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("coaches/countries/" + countryId), includes).build();
        CoachesResponse resp = gson().fromJson(execute(url), CoachesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Coach> searchCoaches(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("coaches/search/" + name), includes).build();
        CoachesResponse resp = gson().fromJson(execute(url), CoachesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Coach> getLatestUpdatedCoaches(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("coaches/latest"), includes).build();
        CoachesResponse resp = gson().fromJson(execute(url), CoachesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Referees
    // -------------------------------------------------------------------------

    public List<Referee> getAllReferees(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("referees"), includes).build();
        Gson g = gson();
        RefereesResponse resp = g.fromJson(execute(base), RefereesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<Referee> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), RefereesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public Referee getRefereeById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("referees/" + id), includes).build();
        RefereeResponse resp = gson().fromJson(execute(url), RefereeResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Referee> getRefereesByCountry(long countryId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("referees/countries/" + countryId), includes).build();
        RefereesResponse resp = gson().fromJson(execute(url), RefereesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Referee> getRefereesBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("referees/seasons/" + seasonId), includes).build();
        RefereesResponse resp = gson().fromJson(execute(url), RefereesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Referee> searchReferees(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("referees/search/" + name), includes).build();
        RefereesResponse resp = gson().fromJson(execute(url), RefereesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }
}
