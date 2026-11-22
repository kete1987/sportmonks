package es.com.kete1987.sportmonks.library;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
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
import es.com.kete1987.sportmonks.library.football.model.statistic.SeasonStatistic;
import es.com.kete1987.sportmonks.library.football.model.statistic.SeasonStatisticEntity;
import es.com.kete1987.sportmonks.library.football.model.statistic.SeasonStatisticsResponse;
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
import es.com.kete1987.sportmonks.library.football.model.transferrumourconfig.TransferRumourConfig;
import es.com.kete1987.sportmonks.library.football.model.tvstation.TvStation;
import es.com.kete1987.sportmonks.library.football.model.tvstation.TvStationResponse;
import es.com.kete1987.sportmonks.library.football.model.tvstation.TvStationsResponse;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;
import es.com.kete1987.sportmonks.library.football.model.venue.VenueDetailResponse;
import es.com.kete1987.sportmonks.library.football.model.venue.VenueResponse;
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
        this(apiToken, locale, null);
    }

    public FootballApi(String apiToken, String locale, String timezone) {
        this(buildHttpClient(apiToken), Constants.BASE_URL_FOOTBALL, locale, timezone, new RateLimitTracker());
    }

    FootballApi(OkHttpClient client, String footballBase, String locale, String timezone, RateLimitTracker tracker) {
        super(client, locale, timezone, tracker);
        this.footballBase = footballBase;
    }

    private HttpUrl.Builder footballUrl(String path, Object... segments) {
        return apiUrl(footballBase, path, segments);
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

    private List<TopScoresPlayer> fetchTopScoresList(HttpUrl url) throws IOException, SportMonksException {
        return fetchTopScoresList(url, 0);
    }

    /** Like {@link #fetchTopScoresList(HttpUrl)} but caps the result at {@code limit} elements. */
    private List<TopScoresPlayer> fetchTopScoresList(HttpUrl url, int limit) throws IOException, SportMonksException {
        return fetchPaged(url, TopScorersResponse.class, TopScorersResponse::getData, TopScorersResponse::getPagination, limit);
    }

    /** {@code data} of a response, or an empty list when the body is empty or carries no {@code data}. */
    private static <R, T> List<T> dataOrEmpty(R resp, Function<R, List<T>> dataFn) {
        List<T> data = resp != null ? dataFn.apply(resp) : null;
        return data != null ? data : new ArrayList<>();
    }

    /**
     * Page envelope for list endpoints whose public {@code *Response} class has no
     * {@code pagination} field; only {@code data} and {@code pagination} are read.
     */
    private static class Page<T> {
        private List<T> data;
        private Pagination pagination;

        List<T> getData() {
            return data;
        }

        Pagination getPagination() {
            return pagination;
        }
    }

    private static final class VenuePage extends Page<Venue> {
    }

    private static final class RivalPage extends Page<Rival> {
    }

    private static final class CommentPage extends Page<Comment> {
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
        return getLivescores(includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getLivescores(String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("livescores"), includes), filters).build();
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
        return getLivescoresFiltered(matchIds, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getLivescoresFiltered(String[] matchIds, String[] includes, String filters) throws IOException, SportMonksException {
        if (matchIds != null && matchIds.length > 0) {
            HttpUrl url = withFilters(withIncludes(footballUrl("livescores/multi", String.join(",", matchIds)), includes), filters).build();
            return fetchMatchList(url);
        }
        return getLivescores(includes, filters);
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
        return getLiveMatches(includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getLiveMatches(String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("livescores/inplay"), includes), filters).build();
        return dataOrEmpty(get(url, MatchsResponse.class), MatchsResponse::getData);
    }

    /**
     * All fixtures ({@code GET /fixtures}), oldest first, capped at {@code limit}. The catalogue
     * holds every fixture Sportmonks knows, so always pass a positive limit or a filter: a
     * non-positive {@code limit} walks every page.
     */
    public List<MatchDetail> getAllFixtures(int limit, String... includes) throws IOException, SportMonksException {
        return getAllFixtures(limit, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param limit maximum number of fixtures; non-positive walks every page
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code fixtureLeagues:564});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getAllFixtures(int limit, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures"), includes), filters).build();
        return fetchMatchList(url, limit);
    }

    public List<MatchDetail> getMatchesByDate(String date, String... includes) throws IOException, SportMonksException {
        return getMatchesByDate(date, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getMatchesByDate(String date, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures/date", date), includes), filters).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getMatchesByDateRange(String beginDate, String endDate) throws IOException, SportMonksException {
        return getMatchesByDateRange(beginDate, endDate, "participants");
    }

    public List<MatchDetail> getMatchesByDateRange(String beginDate, String endDate, String... includes) throws IOException, SportMonksException {
        return getMatchesByDateRange(beginDate, endDate, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getMatchesByDateRange(String beginDate, String endDate, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures/between", beginDate, endDate), includes), filters).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getMatchesByDateRangeForTeam(String beginDate, String endDate, String teamId, String... includes) throws IOException, SportMonksException {
        return getMatchesByDateRangeForTeam(beginDate, endDate, teamId, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getMatchesByDateRangeForTeam(String beginDate, String endDate, String teamId, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures/between", beginDate, endDate, teamId), includes), filters).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getMatchesByMultipleIDs(String[] fixtureIds, String... includes) throws IOException, SportMonksException {
        return getMatchesByMultipleIDs(fixtureIds, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getMatchesByMultipleIDs(String[] fixtureIds, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures/multi", String.join(",", fixtureIds)), includes), filters).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getLatestUpdatedLivescores(String... includes) throws IOException, SportMonksException {
        return getLatestUpdatedLivescores(includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getLatestUpdatedLivescores(String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("livescores/latest"), includes), filters).build();
        return dataOrEmpty(get(url, MatchsResponse.class), MatchsResponse::getData);
    }

    public List<MatchDetail> getFixturesByHeadToHead(long team1Id, long team2Id, String... includes) throws IOException, SportMonksException {
        return getFixturesByHeadToHead(team1Id, team2Id, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getFixturesByHeadToHead(long team1Id, long team2Id, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures/head-to-head", team1Id, team2Id), includes), filters).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> searchFixtures(String name, String... includes) throws IOException, SportMonksException {
        return searchFixtures(name, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> searchFixtures(String name, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures/search", name), includes), filters).build();
        return fetchMatchList(url);
    }

    /** Fixture search capped at {@code limit} results (no cap when {@code limit <= 0}). */
    public List<MatchDetail> searchFixtures(String name, int limit, String... includes) throws IOException, SportMonksException {
        return searchFixtures(name, limit, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> searchFixtures(String name, int limit, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures/search", name), includes), filters).build();
        return fetchMatchList(url, limit);
    }

    public List<MatchDetail> getLatestUpdatedFixtures(String... includes) throws IOException, SportMonksException {
        return getLatestUpdatedFixtures(includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getLatestUpdatedFixtures(String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures/latest"), includes), filters).build();
        return dataOrEmpty(get(url, MatchsResponse.class), MatchsResponse::getData);
    }

    public List<MatchDetail> getUpcomingFixturesByMarket(long marketId, String... includes) throws IOException, SportMonksException {
        return getUpcomingFixturesByMarket(marketId, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getUpcomingFixturesByMarket(long marketId, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures/upcoming/markets", marketId), includes), filters).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getUpcomingFixturesByTvStation(long tvStationId, String... includes) throws IOException, SportMonksException {
        return getUpcomingFixturesByTvStation(tvStationId, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getUpcomingFixturesByTvStation(long tvStationId, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures/upcoming/tv-stations", tvStationId), includes), filters).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getPastFixturesByTvStation(long tvStationId, String... includes) throws IOException, SportMonksException {
        return getPastFixturesByTvStation(tvStationId, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<MatchDetail> getPastFixturesByTvStation(long tvStationId, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures/past/tv-stations", tvStationId), includes), filters).build();
        return fetchMatchList(url);
    }

    public MatchDetail getMatchDetail(String matchId) throws IOException, SportMonksException {
        return getMatchDetail(matchId, "venue", "state", "lineups", "events", "statistics", "periods", "participants", "scores");
    }

    public MatchDetail getMatchDetail(String matchId, String... includes) throws IOException, SportMonksException {
        return getMatchDetail(matchId, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public MatchDetail getMatchDetail(String matchId, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("fixtures", matchId), includes), filters).build();
        MatchData resp = get(url, MatchData.class);
        return resp != null ? resp.getMatchDetail() : null;
    }

    // -------------------------------------------------------------------------
    // Seasons
    // -------------------------------------------------------------------------

    public List<SeasonData> getSeasons() throws IOException, SportMonksException {
        HttpUrl base = footballUrl("seasons").build();
        return fetchPaged(base, SeasonsResponse.class, SeasonsResponse::getData, SeasonsResponse::getPagination, 0);
    }

    public SeasonData getSeasonById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("seasons", id), includes).build();
        return get(url, SeasonDataResponse.class).getSeasonData();
    }

    public List<SeasonData> getSeasonsByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("seasons/teams", teamId), includes).build();
        return fetchPaged(base, SeasonsResponse.class, SeasonsResponse::getData, SeasonsResponse::getPagination, 0);
    }

    public List<SeasonData> searchSeasons(String name, String... includes) throws IOException, SportMonksException {
        return searchSeasons(name, 0, includes);
    }

    /** Season search capped at {@code limit} results. */
    public List<SeasonData> searchSeasons(String name, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("seasons/search", name), includes).build();
        return fetchPaged(base, SeasonsResponse.class, SeasonsResponse::getData, SeasonsResponse::getPagination, limit);
    }

    public KnockoutBracket getBracketsBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("seasons", seasonId, "brackets"), includes).build();
        return get(url, BracketsResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Top Scorers
    // -------------------------------------------------------------------------

    private static final String TOPSCORERS_SEASONS = "topscorers/seasons";

    public List<TopScoresPlayer> getTopScores(String seasonId) throws IOException, SportMonksException {
        return getTopScores(seasonId, "season", "player", "type", "participant");
    }

    public List<TopScoresPlayer> getTopScores(String seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl(TOPSCORERS_SEASONS, seasonId), includes).build();
        return fetchTopScoresList(url);
    }

    /**
     * Top scorers of a season, capped at {@code limit} entries (e.g. {@code 25} for the top 25).
     * The endpoint returns players already ordered by tally, so this yields the leaders without
     * fetching every page. {@code limit <= 0} means no cap (all pages). Pass the includes you need
     * (e.g. {@code "player"}) — unlike {@link #getTopScores(String)} this overload has no defaults.
     */
    public List<TopScoresPlayer> getTopScores(String seasonId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl(TOPSCORERS_SEASONS, seasonId), includes).build();
        return fetchTopScoresList(url, limit);
    }

    public List<TopScoresPlayer> getTopScoresFiltered(String seasonId, int typeId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl(TOPSCORERS_SEASONS, seasonId), includes)
                .addQueryParameter("filters", "seasontopscorerTypes:" + typeId)
                .build();
        return fetchTopScoresList(url);
    }

    /** Filtered season top scorers, capped at {@code limit} entries. See {@link #getTopScores(String, int, String...)}. */
    public List<TopScoresPlayer> getTopScoresFiltered(String seasonId, int typeId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl(TOPSCORERS_SEASONS, seasonId), includes)
                .addQueryParameter("filters", "seasontopscorerTypes:" + typeId)
                .build();
        return fetchTopScoresList(url, limit);
    }

    public List<TopScoresPlayer> getTopScoresByStage(String stageId) throws IOException, SportMonksException {
        return getTopScoresByStage(stageId, "season", "player", "type");
    }

    public List<TopScoresPlayer> getTopScoresByStage(String stageId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("topscorers/stages", stageId), includes).build();
        return fetchTopScoresList(url);
    }

    /** Stage top scorers, capped at {@code limit} entries. See {@link #getTopScores(String, int, String...)}. */
    public List<TopScoresPlayer> getTopScoresByStage(String stageId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("topscorers/stages", stageId), includes).build();
        return fetchTopScoresList(url, limit);
    }

    // -------------------------------------------------------------------------
    // Standings
    // -------------------------------------------------------------------------

    public List<Standings> getStandings(String seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings/seasons", seasonId), includes).build();
        StandingsResponse resp = get(url, StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    /**
     * Groups standings by group name. Rows with no group (including when the
     * group include is omitted) or a null group name use the empty-string key
     * {@code ""}. Rows retain their response order within each group.
     */
    public TreeMap<String, List<Standings>> getStandingsCup(String seasonId, String... includes) throws IOException, SportMonksException {
        TreeMap<String, List<Standings>> map = new TreeMap<>();
        for (Standings s : getStandings(seasonId, includes)) {
            String groupName = s.getGroup() == null ? null : s.getGroup().getName();
            map.computeIfAbsent(groupName == null ? "" : groupName, k -> new ArrayList<>()).add(s);
        }
        return map;
    }

    /** Every standing row of every season. Huge: prefer {@link #getAllStandings(int, String...)}. */
    public List<Standings> getAllStandings(String... includes) throws IOException, SportMonksException {
        return getAllStandings(0, includes);
    }

    /** All standings capped at {@code limit} (no cap when {@code limit <= 0}). */
    public List<Standings> getAllStandings(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings"), includes).build();
        return fetchPaged(url, StandingsResponse.class, StandingsResponse::getData, StandingsResponse::getPagination, limit);
    }

    public List<Standings> getStandingsByRound(long roundId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings/rounds", roundId), includes).build();
        StandingsResponse resp = get(url, StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Standings> getStandingCorrectionsBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings/corrections/seasons", seasonId), includes).build();
        StandingsResponse resp = get(url, StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Standings> getLiveStandingsByLeague(long leagueId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings/live/leagues", leagueId), includes).build();
        StandingsResponse resp = get(url, StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Standings> getGroupedStandingsByRound(long roundId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings/rounds", roundId, "grouped"), includes).build();
        StandingsResponse resp = get(url, StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Statistics
    // -------------------------------------------------------------------------

    /**
     * Season statistics of a player, team, coach or referee: one {@link SeasonStatistic} per
     * season (and team, for players and coaches), each with its values in
     * {@link SeasonStatistic#getDetails()}. Calls {@code statistics/seasons/{entity}/{id}}.
     */
    public List<SeasonStatistic> getSeasonStatistics(SeasonStatisticEntity entity, long id, String... includes) throws IOException, SportMonksException {
        return getSeasonStatistics(entity, id, 0, includes);
    }

    /** Like {@link #getSeasonStatistics(SeasonStatisticEntity, long, String...)}, capped at {@code limit} seasons. */
    public List<SeasonStatistic> getSeasonStatistics(SeasonStatisticEntity entity, long id, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("statistics/seasons", entity.getPath(), id), includes).build();
        return fetchPaged(url, SeasonStatisticsResponse.class, SeasonStatisticsResponse::getData, SeasonStatisticsResponse::getPagination, limit);
    }

    public List<Statistic> getStatsByStage(long stageId, String... includes) throws IOException, SportMonksException {
        return getStatsByStage(stageId, 0, includes);
    }

    /** Stage statistics capped at {@code limit} (no cap when {@code limit <= 0}). */
    public List<Statistic> getStatsByStage(long stageId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("statistics/stages", stageId), includes).build();
        return fetchPaged(url, StatisticsResponse.class, StatisticsResponse::getData, StatisticsResponse::getPagination, limit);
    }

    public List<Statistic> getStatsByRound(long roundId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("statistics/rounds", roundId), includes).build();
        return fetchPaged(url, StatisticsResponse.class, StatisticsResponse::getData, StatisticsResponse::getPagination, 0);
    }

    // -------------------------------------------------------------------------
    // Schedules
    // -------------------------------------------------------------------------

    public List<Schedule> getSchedulesBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("schedules/seasons", seasonId), includes).build();
        SchedulesResponse resp = get(url, SchedulesResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Schedule> getSchedulesByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("schedules/teams", teamId), includes).build();
        SchedulesResponse resp = get(url, SchedulesResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Schedule> getSchedulesBySeasonAndTeam(long seasonId, long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("schedules/seasons", seasonId, "teams", teamId), includes).build();
        SchedulesResponse resp = get(url, SchedulesResponse.class);
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
        HttpUrl url = withIncludes(footballUrl("transfers", id), includes).build();
        TransferResponse resp = get(url, TransferResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Transfer> getLatestTransfers(String... includes) throws IOException, SportMonksException {
        return getLatestTransfers(0, includes);
    }

    /** Latest transfers capped at {@code limit} (no cap when {@code limit <= 0}). */
    public List<Transfer> getLatestTransfers(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfers/latest"), includes).build();
        return fetchPaged(url, TransfersResponse.class, TransfersResponse::getData, TransfersResponse::getPagination, limit);
    }

    /**
     * Transfers between two dates ({@code yyyy-MM-dd}). The API rejects ranges longer than
     * 31 days with a 422, surfaced as {@link SportMonksException}; split longer periods.
     */
    public List<Transfer> getTransfersByDateRange(String startDate, String endDate, String... includes) throws IOException, SportMonksException {
        return getTransfersByDateRange(startDate, endDate, 0, includes);
    }

    /**
     * Like {@link #getTransfersByDateRange(String, String, String...)} (31 days at most),
     * capped at {@code limit} (no cap when {@code limit <= 0}).
     */
    public List<Transfer> getTransfersByDateRange(String startDate, String endDate, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfers/between", startDate, endDate), includes).build();
        return fetchPaged(url, TransfersResponse.class, TransfersResponse::getData, TransfersResponse::getPagination, limit);
    }

    public List<Transfer> getTransfersByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        return getTransfersByTeam(teamId, 0, includes);
    }

    /** Transfers of a team capped at {@code limit}, without crawling every page. */
    public List<Transfer> getTransfersByTeam(long teamId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("transfers/teams", teamId), includes).build();
        return fetchPaged(base, TransfersResponse.class, TransfersResponse::getData, TransfersResponse::getPagination, limit);
    }

    public List<Transfer> getTransfersByPlayer(long playerId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfers/players", playerId), includes).build();
        TransfersResponse resp = get(url, TransfersResponse.class);
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
        HttpUrl url = withIncludes(footballUrl("transfer-rumours", id), includes).build();
        TransferRumourResponse resp = get(url, TransferRumourResponse.class);
        return resp != null ? resp.getData() : null;
    }

    /**
     * Catalogue of fee bands, transfer types and rumour statuses the transfer rumours refer to
     * ({@code transfer-rumours/config}), or {@code null} when the response is empty. Requires the
     * Transfer Rumours add-on.
     */
    public TransferRumourConfig getTransferRumoursConfig() throws IOException, SportMonksException {
        HttpUrl url = footballUrl("transfer-rumours/config").build();
        JsonElement root = get(url, JsonElement.class);
        if (root == null || !root.isJsonObject()) return null;
        // The documented example has the catalogue at the top level; accept a "data" wrapper too.
        JsonObject body = root.getAsJsonObject();
        JsonElement data = body.get("data");
        return convert(url, data != null && data.isJsonObject() ? data : body, TransferRumourConfig.class);
    }

    public List<TransferRumour> getTransferRumoursByDateRange(String startDate, String endDate, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfer-rumours/between", startDate, endDate), includes).build();
        TransferRumoursResponse resp = get(url, TransferRumoursResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<TransferRumour> getTransferRumoursByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfer-rumours/teams", teamId), includes).build();
        TransferRumoursResponse resp = get(url, TransferRumoursResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<TransferRumour> getTransferRumoursByPlayer(long playerId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("transfer-rumours/players", playerId), includes).build();
        TransferRumoursResponse resp = get(url, TransferRumoursResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Rivals
    // -------------------------------------------------------------------------

    public List<Rival> getAllRivals(String... includes) throws IOException, SportMonksException {
        return getAllRivals(0, includes);
    }

    /** All rivalries capped at {@code limit} (no cap when {@code limit <= 0}). */
    public List<Rival> getAllRivals(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rivals"), includes).build();
        return fetchPaged(url, RivalPage.class, RivalPage::getData, RivalPage::getPagination, limit);
    }

    public List<Rival> getRivalsByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rivals/teams", teamId), includes).build();
        RivalsResponse resp = get(url, RivalsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Commentaries
    // -------------------------------------------------------------------------

    /** Every commentary of every fixture. Huge: prefer {@link #getAllCommentaries(int, String...)}. */
    public List<Comment> getAllCommentaries(String... includes) throws IOException, SportMonksException {
        return getAllCommentaries(0, includes);
    }

    /** All commentaries capped at {@code limit} (no cap when {@code limit <= 0}). */
    public List<Comment> getAllCommentaries(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("commentaries"), includes).build();
        return fetchPaged(url, CommentPage.class, CommentPage::getData, CommentPage::getPagination, limit);
    }

    public List<Comment> getCommentaryByFixture(long fixtureId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("commentaries/fixtures", fixtureId), includes).build();
        CommentsResponse resp = get(url, CommentsResponse.class);
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
        HttpUrl url = withIncludes(footballUrl("tv-stations", id), includes).build();
        TvStationResponse resp = get(url, TvStationResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<TvStation> getTvStationsByFixture(long fixtureId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("tv-stations/fixtures", fixtureId), includes).build();
        TvStationsResponse resp = get(url, TvStationsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Venues
    // -------------------------------------------------------------------------

    public List<Venue> getVenues(String seasonId) throws IOException, SportMonksException {
        return getVenues(seasonId, new String[0]);
    }

    /** Venues of a season with the given includes (e.g. {@code "city"}, {@code "country"}). */
    public List<Venue> getVenues(String seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("venues/seasons", seasonId), includes).build();
        return dataOrEmpty(get(url, VenueResponse.class), VenueResponse::getData);
    }

    public List<Venue> getAllVenues(String... includes) throws IOException, SportMonksException {
        return getAllVenues(0, includes);
    }

    /** All venues capped at {@code limit} (no cap when {@code limit <= 0}). */
    public List<Venue> getAllVenues(int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("venues"), includes).build();
        return fetchPaged(url, VenuePage.class, VenuePage::getData, VenuePage::getPagination, limit);
    }

    public Venue getVenueById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("venues", id), includes).build();
        VenueDetailResponse resp = get(url, VenueDetailResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Venue> searchVenues(String name, String... includes) throws IOException, SportMonksException {
        return searchVenues(name, 0, includes);
    }

    /** Venue search capped at {@code limit} results (no cap when {@code limit <= 0}). */
    public List<Venue> searchVenues(String name, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("venues/search", name), includes).build();
        return fetchPaged(url, VenuePage.class, VenuePage::getData, VenuePage::getPagination, limit);
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

    /** Match facts of a fixture ({@code match-facts/{fixtureId}}). Requires the Match Facts add-on. */
    public List<MatchFact> getMatchFactsByFixture(long fixtureId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("match-facts", fixtureId), includes).build();
        return fetchPaged(url, MatchFactsResponse.class, MatchFactsResponse::getData, MatchFactsResponse::getPagination, 0);
    }

    /**
     * Match facts of the fixtures between two dates ({@code match-facts/fixtures/between/{start}/{end}}).
     * Requires the Match Facts add-on.
     */
    public List<MatchFact> getMatchFactsByDateRange(String startDate, String endDate, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("match-facts/fixtures/between", startDate, endDate), includes).build();
        return fetchPaged(url, MatchFactsResponse.class, MatchFactsResponse::getData, MatchFactsResponse::getPagination, 0);
    }

    public List<MatchFact> getMatchFactsByLeague(long leagueId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("match-facts/leagues", leagueId), includes).build();
        MatchFactsResponse resp = get(url, MatchFactsResponse.class);
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
        HttpUrl url = withIncludes(footballUrl("team-of-the-week/rounds", roundId), includes).build();
        TeamsOfTheWeekResponse resp = get(url, TeamsOfTheWeekResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<TeamOfTheWeek> getLatestTeamOfTheWeekByLeague(long leagueId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("team-of-the-week/leagues", leagueId, "latest"), includes).build();
        TeamsOfTheWeekResponse resp = get(url, TeamsOfTheWeekResponse.class);
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
        HttpUrl url = withIncludes(footballUrl("team-rankings/teams", teamId), includes).build();
        TeamRankingsResponse resp = get(url, TeamRankingsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<TeamRanking> getTeamRankingsByDate(String date, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("team-rankings/date", date), includes).build();
        TeamRankingsResponse resp = get(url, TeamRankingsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Expected Lineups (beta)
    // -------------------------------------------------------------------------

    public List<ExpectedLineup> getExpectedLineupByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("expected-lineups/teams", teamId), includes).build();
        ExpectedLineupsResponse resp = get(url, ExpectedLineupsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<ExpectedLineup> getExpectedLineupsByPlayer(long playerId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("expected-lineups/players", playerId), includes).build();
        ExpectedLineupsResponse resp = get(url, ExpectedLineupsResponse.class);
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
        HttpUrl url = withIncludes(footballUrl("teams/seasons", seasonId), includes).build();
        TeamsResponse resp = get(url, TeamsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Team> getTeamsByCountry(long countryId, String... includes) throws IOException, SportMonksException {
        return getTeamsByCountry(countryId, 0, includes);
    }

    /** Teams of a country capped at {@code limit} (no cap when {@code limit <= 0}). */
    public List<Team> getTeamsByCountry(long countryId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("teams/countries", countryId), includes).build();
        return fetchPaged(url, TeamsResponse.class, TeamsResponse::getData, TeamsResponse::getPagination, limit);
    }

    public Team getTeamById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("teams", id), includes).build();
        TeamResponse resp = get(url, TeamResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Team> searchTeams(String name, String... includes) throws IOException, SportMonksException {
        return searchTeams(name, 0, includes);
    }

    /** Team search capped at {@code limit} results. */
    public List<Team> searchTeams(String name, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("teams/search", name), includes).build();
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
        HttpUrl url = withIncludes(footballUrl("leagues", id), includes).build();
        LeagueResponse resp = get(url, LeagueResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<League> getLiveLeagues(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/live"), includes).build();
        return fetchPaged(base, LeaguesResponse.class, LeaguesResponse::getData, LeaguesResponse::getPagination, 0);
    }

    public List<League> getLeaguesByDate(String date, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/date", date), includes).build();
        return fetchPaged(base, LeaguesResponse.class, LeaguesResponse::getData, LeaguesResponse::getPagination, 0);
    }

    public List<League> getLeaguesByCountry(long countryId, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/countries", countryId), includes).build();
        return fetchPaged(base, LeaguesResponse.class, LeaguesResponse::getData, LeaguesResponse::getPagination, 0);
    }

    public List<League> searchLeagues(String name, String... includes) throws IOException, SportMonksException {
        return searchLeagues(name, 0, includes);
    }

    /** League search capped at {@code limit} results. */
    public List<League> searchLeagues(String name, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/search", name), includes).build();
        return fetchPaged(base, LeaguesResponse.class, LeaguesResponse::getData, LeaguesResponse::getPagination, limit);
    }

    public List<League> getLeaguesByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/teams", teamId), includes).build();
        return fetchPaged(base, LeaguesResponse.class, LeaguesResponse::getData, LeaguesResponse::getPagination, 0);
    }

    public List<League> getCurrentLeaguesByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("leagues/teams", teamId, "current"), includes).build();
        return fetchPaged(base, LeaguesResponse.class, LeaguesResponse::getData, LeaguesResponse::getPagination, 0);
    }

    // -------------------------------------------------------------------------
    // Stages
    // -------------------------------------------------------------------------

    public List<Stage> getAllStages(String... includes) throws IOException, SportMonksException {
        return getAllStages(includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<Stage> getAllStages(String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("stages"), includes), filters).build();
        return fetchPaged(url, StagesResponse.class, StagesResponse::getData, StagesResponse::getPagination, 0);
    }

    public List<Stage> getStagesBySeasonId(long seasonId, String... includes) throws IOException, SportMonksException {
        return getStagesBySeasonId(seasonId, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<Stage> getStagesBySeasonId(long seasonId, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("stages/seasons", seasonId), includes), filters).build();
        return fetchPaged(url, StagesResponse.class, StagesResponse::getData, StagesResponse::getPagination, 0);
    }

    public Stage getStageById(long id, String... includes) throws IOException, SportMonksException {
        return getStageById(id, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public Stage getStageById(long id, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("stages", id), includes), filters).build();
        StageResponse resp = get(url, StageResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Stage> searchStages(String name, String... includes) throws IOException, SportMonksException {
        return searchStages(name, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<Stage> searchStages(String name, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("stages/search", name), includes), filters).build();
        return fetchPaged(url, StagesResponse.class, StagesResponse::getData, StagesResponse::getPagination, 0);
    }

    // -------------------------------------------------------------------------
    // Rounds
    // -------------------------------------------------------------------------

    public List<Round> getAllRounds(String... includes) throws IOException, SportMonksException {
        return getAllRounds(includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<Round> getAllRounds(String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("rounds"), includes), filters).build();
        return fetchPaged(url, RoundsResponse.class, RoundsResponse::getData, RoundsResponse::getPagination, 0);
    }

    public List<Round> getRoundsBySeasonId(long seasonId, String... includes) throws IOException, SportMonksException {
        return getRoundsBySeasonId(seasonId, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<Round> getRoundsBySeasonId(long seasonId, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("rounds/seasons", seasonId), includes), filters).build();
        return fetchPaged(url, RoundsResponse.class, RoundsResponse::getData, RoundsResponse::getPagination, 0);
    }

    public Round getRoundById(long id, String... includes) throws IOException, SportMonksException {
        return getRoundById(id, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public Round getRoundById(long id, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("rounds", id), includes), filters).build();
        RoundResponse resp = get(url, RoundResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Round> searchRounds(String name, String... includes) throws IOException, SportMonksException {
        return searchRounds(name, includes, null);
    }

    /**
     * Applies Sportmonks filters to the requested resource and its includes.
     * @param includes explicit includes; null or empty omits the include parameter
     * @param filters unencoded filter expression (e.g. {@code lineupdetailTypes:40});
     *                separate multiple filters with semicolons; null or blank omits filters
     */
    public List<Round> searchRounds(String name, String[] includes, String filters) throws IOException, SportMonksException {
        HttpUrl url = withFilters(withIncludes(footballUrl("rounds/search", name), includes), filters).build();
        return fetchPaged(url, RoundsResponse.class, RoundsResponse::getData, RoundsResponse::getPagination, 0);
    }

    // -------------------------------------------------------------------------
    // States
    // -------------------------------------------------------------------------

    public List<State> getAllStates() throws IOException, SportMonksException {
        HttpUrl url = footballUrl("states").build();
        return dataOrEmpty(get(url, StatesResponse.class), StatesResponse::getData);
    }

    public State getStateById(long id) throws IOException, SportMonksException {
        HttpUrl url = footballUrl("states", id).build();
        StateResponse resp = get(url, StateResponse.class);
        return resp != null ? resp.getData() : null;
    }

    // -------------------------------------------------------------------------
    // Squads
    // -------------------------------------------------------------------------

    public List<SquadPlayer> getSquadByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("squads/teams", teamId), includes).build();
        SquadPlayersResponse resp = get(url, SquadPlayersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<SquadPlayer> getExtendedSquadByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("squads/teams", teamId, "extended"), includes).build();
        SquadPlayersResponse resp = get(url, SquadPlayersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<SquadPlayer> getSquadBySeasonAndTeam(long seasonId, long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("squads/seasons", seasonId, "teams", teamId), includes).build();
        SquadPlayersResponse resp = get(url, SquadPlayersResponse.class);
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
        HttpUrl url = withIncludes(footballUrl("players", id), includes).build();
        PlayerResponse resp = get(url, PlayerResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Player> getPlayersByCountry(long countryId, String... includes) throws IOException, SportMonksException {
        return getPlayersByCountry(countryId, 0, includes);
    }

    /** Players of a country capped at {@code limit}, without crawling every page. */
    public List<Player> getPlayersByCountry(long countryId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("players/countries", countryId), includes).build();
        return fetchPaged(base, PlayersResponse.class, PlayersResponse::getData, PlayersResponse::getPagination, limit);
    }

    public List<Player> searchPlayers(String name, String... includes) throws IOException, SportMonksException {
        return searchPlayers(name, 0, includes);
    }

    /** Player search capped at {@code limit} results. */
    public List<Player> searchPlayers(String name, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("players/search", name), includes).build();
        return fetchPaged(base, PlayersResponse.class, PlayersResponse::getData, PlayersResponse::getPagination, limit);
    }

    public List<Player> getLatestUpdatedPlayers(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("players/latest"), includes).build();
        PlayersResponse resp = get(url, PlayersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Coaches
    // -------------------------------------------------------------------------

    public List<Coach> getAllCoaches(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("coaches"), includes).build();
        return fetchPaged(base, CoachesResponse.class, CoachesResponse::getData, CoachesResponse::getPagination, 0);
    }

    public Coach getCoachById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("coaches", id), includes).build();
        CoachResponse resp = get(url, CoachResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Coach> getCoachesByCountry(long countryId, String... includes) throws IOException, SportMonksException {
        return getCoachesByCountry(countryId, 0, includes);
    }

    /** Coaches of a country capped at {@code limit} (no cap when {@code limit <= 0}). */
    public List<Coach> getCoachesByCountry(long countryId, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("coaches/countries", countryId), includes).build();
        return fetchPaged(url, CoachesResponse.class, CoachesResponse::getData, CoachesResponse::getPagination, limit);
    }

    public List<Coach> searchCoaches(String name, String... includes) throws IOException, SportMonksException {
        return searchCoaches(name, 0, includes);
    }

    /** Coach search capped at {@code limit} results (no cap when {@code limit <= 0}). */
    public List<Coach> searchCoaches(String name, int limit, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("coaches/search", name), includes).build();
        return fetchPaged(url, CoachesResponse.class, CoachesResponse::getData, CoachesResponse::getPagination, limit);
    }

    public List<Coach> getLatestUpdatedCoaches(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("coaches/latest"), includes).build();
        CoachesResponse resp = get(url, CoachesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Referees
    // -------------------------------------------------------------------------

    public List<Referee> getAllReferees(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(footballUrl("referees"), includes).build();
        return fetchPaged(base, RefereesResponse.class, RefereesResponse::getData, RefereesResponse::getPagination, 0);
    }

    public Referee getRefereeById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("referees", id), includes).build();
        RefereeResponse resp = get(url, RefereeResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Referee> getRefereesByCountry(long countryId, String... includes) throws IOException, SportMonksException {
        return getRefereesByCountry(countryId, 0, includes);
    }

    /** Referees of a country capped at {@code limit} (no cap when {@code limit <= 0}). */
    public List<Referee> getRefereesByCountry(long countryId, int limit, String... includes) throws IOException, SportMonksException {
        return fetchReferees(withIncludes(footballUrl("referees/countries", countryId), includes).build(), limit);
    }

    public List<Referee> getRefereesBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        return getRefereesBySeason(seasonId, 0, includes);
    }

    /** Referees of a season capped at {@code limit} (no cap when {@code limit <= 0}). */
    public List<Referee> getRefereesBySeason(long seasonId, int limit, String... includes) throws IOException, SportMonksException {
        return fetchReferees(withIncludes(footballUrl("referees/seasons", seasonId), includes).build(), limit);
    }

    public List<Referee> searchReferees(String name, String... includes) throws IOException, SportMonksException {
        return searchReferees(name, 0, includes);
    }

    /** Referee search capped at {@code limit} results (no cap when {@code limit <= 0}). */
    public List<Referee> searchReferees(String name, int limit, String... includes) throws IOException, SportMonksException {
        return fetchReferees(withIncludes(footballUrl("referees/search", name), includes).build(), limit);
    }

    private List<Referee> fetchReferees(HttpUrl url, int limit) throws IOException, SportMonksException {
        return fetchPaged(url, RefereesResponse.class, RefereesResponse::getData, RefereesResponse::getPagination, limit);
    }
}
