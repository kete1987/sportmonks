package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.util.Constants;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.city.City;
import es.com.kete1987.sportmonks.library.core.model.continent.Continent;
import es.com.kete1987.sportmonks.library.core.model.country.Country;
import es.com.kete1987.sportmonks.library.core.model.my.MyApi;
import es.com.kete1987.sportmonks.library.core.model.my.MyLeague;
import es.com.kete1987.sportmonks.library.core.model.my.MyResource;
import es.com.kete1987.sportmonks.library.core.model.my.MyUsage;
import es.com.kete1987.sportmonks.library.core.model.region.Region;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import es.com.kete1987.sportmonks.library.football.model.comments.Comment;
import es.com.kete1987.sportmonks.library.football.model.expectedgoal.ExpectedGoal;
import es.com.kete1987.sportmonks.library.football.model.expectedlineup.ExpectedLineup;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.match.State;
import es.com.kete1987.sportmonks.library.football.model.matchfact.MatchFact;
import es.com.kete1987.sportmonks.library.football.model.news.NewsArticle;
import es.com.kete1987.sportmonks.library.football.model.coach.Coach;
import es.com.kete1987.sportmonks.library.football.model.player.Player;
import es.com.kete1987.sportmonks.library.football.model.prediction.Predictability;
import es.com.kete1987.sportmonks.library.football.model.prediction.Probability;
import es.com.kete1987.sportmonks.library.football.model.prediction.ValueBet;
import es.com.kete1987.sportmonks.library.football.model.referee.Referee;
import es.com.kete1987.sportmonks.library.football.model.rival.Rival;
import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.schedule.Schedule;
import es.com.kete1987.sportmonks.library.football.model.season.KnockoutBracket;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.squad.SquadPlayer;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import es.com.kete1987.sportmonks.library.football.model.standings.Standings;
import es.com.kete1987.sportmonks.library.football.model.statistic.Statistic;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.teamranking.TeamRanking;
import es.com.kete1987.sportmonks.library.football.model.topscorers.TopScoresPlayer;
import es.com.kete1987.sportmonks.library.football.model.totw.TeamOfTheWeek;
import es.com.kete1987.sportmonks.library.football.model.transfer.Transfer;
import es.com.kete1987.sportmonks.library.football.model.transfer.TransferRumour;
import es.com.kete1987.sportmonks.library.football.model.tvstation.TvStation;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;
import es.com.kete1987.sportmonks.library.odds.model.Bookmaker;
import es.com.kete1987.sportmonks.library.odds.model.BookmakerMapping;
import es.com.kete1987.sportmonks.library.odds.model.Market;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Main entry point for the Sportmonks v3 API.
 *
 * <p>Create one instance per API key and reuse it — each instance owns an OkHttp connection
 * pool and a shared {@link RateLimitTracker}. Rate-limit state is updated automatically after every
 * request: the global {@code X-RateLimit-*} headers via {@link #getRemainingRequests()} and
 * {@link #getMaximumRequests()}, and the per-entity {@code rate_limit} from the response body via
 * {@link #getLastRateLimit()} and {@link #getRateLimitsByEntity()}.
 *
 * <p>The facade delegates to four specialised sub-APIs accessible via {@link #getFootball()},
 * {@link #getOdds()}, {@link #getPredictions()}, and {@link #getCore()}.
 */
public class SportMonksAPI {

    private final FootballApi football;
    private final OddsApi odds;
    private final PredictionsApi predictions;
    private final CoreApi core;
    private final RateLimitTracker tracker;

    /**
     * Creates an API client with the default (English) locale.
     *
     * @param apiToken your Sportmonks API key
     */
    public SportMonksAPI(String apiToken) {
        this(apiToken, null);
    }

    /**
     * Creates an API client that requests data in the specified locale.
     *
     * @param apiToken your Sportmonks API key
     * @param locale   BCP 47 language tag accepted by Sportmonks (e.g. {@code "en"}, {@code "es"});
     *                 pass {@code null} for the default locale
     */
    public SportMonksAPI(String apiToken, String locale) {
        this(apiToken, locale, null);
    }

    /**
     * Creates an API client that requests data in the specified locale and timezone.
     *
     * <p>When a timezone is supplied it is sent as the {@code timezone} query parameter on every
     * request, so date/time fields in the response are expressed in that zone instead of the
     * default UTC. Accepted values are the identifiers returned by {@link CoreApi#getAllTimezones()}
     * (e.g. {@code "Europe/Amsterdam"}).
     *
     * @param apiToken your Sportmonks API key
     * @param locale   BCP 47 language tag accepted by Sportmonks (e.g. {@code "en"}, {@code "es"});
     *                 pass {@code null} for the default locale
     * @param timezone IANA timezone identifier accepted by Sportmonks (e.g. {@code "Europe/Amsterdam"});
     *                 pass {@code null} for the default (UTC)
     */
    public SportMonksAPI(String apiToken, String locale, String timezone) {
        OkHttpClient client = SportMonksApiBase.buildHttpClient(apiToken);
        this.tracker = new RateLimitTracker();
        this.football = new FootballApi(client, Constants.BASE_URL_FOOTBALL, locale, timezone, tracker);
        this.odds = new OddsApi(client, Constants.BASE_URL_ODDS, locale, timezone, tracker);
        this.predictions = new PredictionsApi(client, Constants.BASE_URL_FOOTBALL, locale, timezone, tracker);
        this.core = new CoreApi(client, Constants.BASE_URL_CORE, Constants.BASE_URL_MY, locale, timezone, tracker);
    }

    SportMonksAPI(OkHttpClient client, String footballBase, String oddsBase, String coreBase, String myBase) {
        this(client, footballBase, oddsBase, coreBase, myBase, null, null);
    }

    SportMonksAPI(OkHttpClient client, String footballBase, String oddsBase, String coreBase, String myBase,
                  String locale, String timezone) {
        this.tracker = new RateLimitTracker();
        this.football = new FootballApi(client, footballBase, locale, timezone, tracker);
        this.odds = new OddsApi(client, oddsBase, locale, timezone, tracker);
        this.predictions = new PredictionsApi(client, footballBase, locale, timezone, tracker);
        this.core = new CoreApi(client, coreBase, myBase, locale, timezone, tracker);
    }

    public FootballApi getFootball() { return football; }
    public OddsApi getOdds() { return odds; }
    public PredictionsApi getPredictions() { return predictions; }
    public CoreApi getCore() { return core; }

    /** Returns the value of the {@code X-RateLimit-Remaining} header from the last response, or {@code null} if no request has been made yet. */
    public String getRemainingRequests() { return tracker.remaining; }

    /** Returns the value of the {@code X-RateLimit-Limit} header from the last response, or {@code null} if no request has been made yet. */
    public String getMaximumRequests() { return tracker.total; }

    /**
     * Returns the {@code rate_limit} object from the body of the most recent response (scoped to
     * that response's {@code requested_entity}), or {@code null} if no request has been made yet.
     */
    public RateLimit getLastRateLimit() { return tracker.last(); }

    /**
     * Returns an immutable snapshot of the latest {@code rate_limit} seen per {@code requested_entity}
     * (e.g. {@code "league"}, {@code "team"}), accumulated across every request. Empty until the first request.
     */
    public Map<String, RateLimit> getRateLimitsByEntity() { return tracker.byEntity(); }

    // -------------------------------------------------------------------------
    // Matches / Fixtures
    // -------------------------------------------------------------------------

    /** Livescores ({@code /livescores}): ~15 min window around kickoff, not the whole day. */
    public List<MatchDetail> getLivescores(String... includes) throws IOException, SportMonksException { return football.getLivescores(includes); }
    public List<MatchDetail> getLivescoresFiltered(String[] matchIds, String... includes) throws IOException, SportMonksException { return football.getLivescoresFiltered(matchIds, includes); }
    /** @deprecated misleading name, calls {@code /livescores}. Use {@link #getLivescores(String...)}. */
    @Deprecated(since = "3.1.0", forRemoval = true)
    public List<MatchDetail> getTodayMatches(String... includes) throws IOException, SportMonksException { return football.getTodayMatches(includes); }
    /** @deprecated misleading name, calls {@code /livescores/multi}. Use {@link #getLivescoresFiltered(String[], String...)}. */
    @Deprecated(since = "3.1.0", forRemoval = true)
    public List<MatchDetail> getTodayMatchesFiltered(String[] matchIds, String... includes) throws IOException, SportMonksException { return football.getTodayMatchesFiltered(matchIds, includes); }
    public List<MatchDetail> getLiveMatches(String... includes) throws IOException, SportMonksException { return football.getLiveMatches(includes); }
    public List<MatchDetail> getMatchesByDate(String date, String... includes) throws IOException, SportMonksException { return football.getMatchesByDate(date, includes); }
    /** Fetches fixtures in a date range with default include: {@code participants}. */
    public List<MatchDetail> getMatchesByDateRange(String beginDate, String endDate) throws IOException, SportMonksException { return football.getMatchesByDateRange(beginDate, endDate); }
    public List<MatchDetail> getMatchesByDateRange(String beginDate, String endDate, String... includes) throws IOException, SportMonksException { return football.getMatchesByDateRange(beginDate, endDate, includes); }
    public List<MatchDetail> getMatchesByDateRangeForTeam(String beginDate, String endDate, String teamId, String... includes) throws IOException, SportMonksException { return football.getMatchesByDateRangeForTeam(beginDate, endDate, teamId, includes); }
    public List<MatchDetail> getMatchesByMultipleIDs(String[] fixtureIds, String... includes) throws IOException, SportMonksException { return football.getMatchesByMultipleIDs(fixtureIds, includes); }
    public List<MatchDetail> getLatestUpdatedLivescores(String... includes) throws IOException, SportMonksException { return football.getLatestUpdatedLivescores(includes); }
    public List<MatchDetail> getFixturesByHeadToHead(long team1Id, long team2Id, String... includes) throws IOException, SportMonksException { return football.getFixturesByHeadToHead(team1Id, team2Id, includes); }
    public List<MatchDetail> searchFixtures(String name, String... includes) throws IOException, SportMonksException { return football.searchFixtures(name, includes); }
    /** Fixture search capped at {@code limit} results. */
    public List<MatchDetail> searchFixtures(String name, int limit, String... includes) throws IOException, SportMonksException { return football.searchFixtures(name, limit, includes); }
    public List<MatchDetail> getLatestUpdatedFixtures(String... includes) throws IOException, SportMonksException { return football.getLatestUpdatedFixtures(includes); }
    public List<MatchDetail> getUpcomingFixturesByMarket(long marketId, String... includes) throws IOException, SportMonksException { return football.getUpcomingFixturesByMarket(marketId, includes); }
    public List<MatchDetail> getUpcomingFixturesByTvStation(long tvStationId, String... includes) throws IOException, SportMonksException { return football.getUpcomingFixturesByTvStation(tvStationId, includes); }
    public List<MatchDetail> getPastFixturesByTvStation(long tvStationId, String... includes) throws IOException, SportMonksException { return football.getPastFixturesByTvStation(tvStationId, includes); }
    /** Fetches a fixture with default includes: {@code venue, state, lineups, events, statistics, periods, participants, scores}. */
    public MatchDetail getMatchDetail(String matchId) throws IOException, SportMonksException { return football.getMatchDetail(matchId); }
    public MatchDetail getMatchDetail(String matchId, String... includes) throws IOException, SportMonksException { return football.getMatchDetail(matchId, includes); }

    // -------------------------------------------------------------------------
    // Seasons
    // -------------------------------------------------------------------------

    public List<SeasonData> getSeasons() throws IOException, SportMonksException { return football.getSeasons(); }
    public SeasonData getSeasonById(long id, String... includes) throws IOException, SportMonksException { return football.getSeasonById(id, includes); }
    public List<SeasonData> getSeasonsByTeam(long teamId, String... includes) throws IOException, SportMonksException { return football.getSeasonsByTeam(teamId, includes); }
    public List<SeasonData> searchSeasons(String name, String... includes) throws IOException, SportMonksException { return football.searchSeasons(name, includes); }
    /** Season search capped at {@code limit} results. */
    public List<SeasonData> searchSeasons(String name, int limit, String... includes) throws IOException, SportMonksException { return football.searchSeasons(name, limit, includes); }
    public KnockoutBracket getBracketsBySeason(long seasonId, String... includes) throws IOException, SportMonksException { return football.getBracketsBySeason(seasonId, includes); }

    // -------------------------------------------------------------------------
    // Odds
    // -------------------------------------------------------------------------

    public List<Odd> getMatchOdds(String matchId, String... includes) throws IOException, SportMonksException { return odds.getMatchOdds(matchId, includes); }
    public List<Odd> getMatchOddsByBookmaker(String matchId, String bookmakerId) throws IOException, SportMonksException { return odds.getMatchOddsByBookmaker(matchId, bookmakerId); }
    public List<Odd> getMatchOddsByMarket(String matchId, String marketId) throws IOException, SportMonksException { return odds.getMatchOddsByMarket(matchId, marketId); }

    // -------------------------------------------------------------------------
    // Odds: Markets
    // -------------------------------------------------------------------------

    public List<Market> getAllMarkets() throws IOException, SportMonksException { return odds.getAllMarkets(); }
    public Market getMarketById(long marketId) throws IOException, SportMonksException { return odds.getMarketById(marketId); }
    public List<Market> searchMarkets(String query) throws IOException, SportMonksException { return odds.searchMarkets(query); }

    // -------------------------------------------------------------------------
    // Odds: Bookmakers
    // -------------------------------------------------------------------------

    public List<Bookmaker> getAllBookmakers() throws IOException, SportMonksException { return odds.getAllBookmakers(); }
    public Bookmaker getBookmakerById(long bookmakerId) throws IOException, SportMonksException { return odds.getBookmakerById(bookmakerId); }
    public List<Bookmaker> searchBookmakers(String query) throws IOException, SportMonksException { return odds.searchBookmakers(query); }
    public List<BookmakerMapping> getBookmakerMappingsByFixture(long fixtureId) throws IOException, SportMonksException { return odds.getBookmakerMappingsByFixture(fixtureId); }

    // -------------------------------------------------------------------------
    // Odds: Inplay
    // -------------------------------------------------------------------------

    public List<Odd> getInplayOddsByFixture(long fixtureId) throws IOException, SportMonksException { return odds.getInplayOddsByFixture(fixtureId); }
    public List<Odd> getInplayOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException { return odds.getInplayOddsByFixtureAndBookmaker(fixtureId, bookmakerId); }
    public List<Odd> getInplayOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException { return odds.getInplayOddsByFixtureAndMarket(fixtureId, marketId); }
    public List<Odd> getLastUpdatedInplayOdds() throws IOException, SportMonksException { return odds.getLastUpdatedInplayOdds(); }

    // -------------------------------------------------------------------------
    // Odds: Premium Pre-match
    // -------------------------------------------------------------------------

    public List<Odd> getPremiumPreMatchOddsByFixture(long fixtureId) throws IOException, SportMonksException { return odds.getPremiumPreMatchOddsByFixture(fixtureId); }
    public List<Odd> getPremiumPreMatchOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException { return odds.getPremiumPreMatchOddsByFixtureAndBookmaker(fixtureId, bookmakerId); }
    public List<Odd> getPremiumPreMatchOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException { return odds.getPremiumPreMatchOddsByFixtureAndMarket(fixtureId, marketId); }
    public List<Odd> getLastUpdatedPremiumPreMatchOdds() throws IOException, SportMonksException { return odds.getLastUpdatedPremiumPreMatchOdds(); }

    // -------------------------------------------------------------------------
    // Odds: Premium Inplay
    // -------------------------------------------------------------------------

    public List<Odd> getPremiumInplayOddsByFixture(long fixtureId) throws IOException, SportMonksException { return odds.getPremiumInplayOddsByFixture(fixtureId); }
    public List<Odd> getPremiumInplayOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException { return odds.getPremiumInplayOddsByFixtureAndBookmaker(fixtureId, bookmakerId); }
    public List<Odd> getPremiumInplayOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException { return odds.getPremiumInplayOddsByFixtureAndMarket(fixtureId, marketId); }
    public List<Odd> getLastUpdatedPremiumInplayOdds() throws IOException, SportMonksException { return odds.getLastUpdatedPremiumInplayOdds(); }

    // -------------------------------------------------------------------------
    // Predictions
    // -------------------------------------------------------------------------

    public List<Probability> getAllProbabilities(String... includes) throws IOException, SportMonksException { return predictions.getAllProbabilities(includes); }
    public List<Predictability> getPredictabilityByLeague(long leagueId, String... includes) throws IOException, SportMonksException { return predictions.getPredictabilityByLeague(leagueId, includes); }
    public List<Probability> getProbabilitiesByFixture(long fixtureId, String... includes) throws IOException, SportMonksException { return predictions.getProbabilitiesByFixture(fixtureId, includes); }
    public List<ValueBet> getAllValueBets(String... includes) throws IOException, SportMonksException { return predictions.getAllValueBets(includes); }
    public List<ValueBet> getValueBetsByFixture(long fixtureId, String... includes) throws IOException, SportMonksException { return predictions.getValueBetsByFixture(fixtureId, includes); }

    // -------------------------------------------------------------------------
    // Expected Goals (XG)
    // -------------------------------------------------------------------------

    public List<ExpectedGoal> getExpectedGoalsByTeam(String... includes) throws IOException, SportMonksException { return predictions.getExpectedGoalsByTeam(includes); }
    public List<ExpectedGoal> getExpectedGoalsByPlayer(String... includes) throws IOException, SportMonksException { return predictions.getExpectedGoalsByPlayer(includes); }

    // -------------------------------------------------------------------------
    // News
    // -------------------------------------------------------------------------

    public List<NewsArticle> getAllPreMatchNews(String... includes) throws IOException, SportMonksException { return predictions.getAllPreMatchNews(includes); }
    public List<NewsArticle> getPreMatchNewsBySeason(long seasonId, String... includes) throws IOException, SportMonksException { return predictions.getPreMatchNewsBySeason(seasonId, includes); }
    public List<NewsArticle> getPreMatchNewsForUpcoming(String... includes) throws IOException, SportMonksException { return predictions.getPreMatchNewsForUpcoming(includes); }
    public List<NewsArticle> getAllPostMatchNews(String... includes) throws IOException, SportMonksException { return predictions.getAllPostMatchNews(includes); }
    public List<NewsArticle> getPostMatchNewsBySeason(long seasonId, String... includes) throws IOException, SportMonksException { return predictions.getPostMatchNewsBySeason(seasonId, includes); }

    // -------------------------------------------------------------------------
    // Top Scorers
    // -------------------------------------------------------------------------

    /** Fetches top scorers with default includes: {@code season, stage, player, type, participant}. */
    public List<TopScoresPlayer> getTopScores(String seasonId) throws IOException, SportMonksException { return football.getTopScores(seasonId); }
    public List<TopScoresPlayer> getTopScores(String seasonId, String... includes) throws IOException, SportMonksException { return football.getTopScores(seasonId, includes); }
    /** Top scorers of a season capped at {@code limit} (e.g. top 25), without fetching every page. */
    public List<TopScoresPlayer> getTopScores(String seasonId, int limit, String... includes) throws IOException, SportMonksException { return football.getTopScores(seasonId, limit, includes); }
    public List<TopScoresPlayer> getTopScoresFiltered(String seasonId, int typeId, String... includes) throws IOException, SportMonksException { return football.getTopScoresFiltered(seasonId, typeId, includes); }
    /** Filtered season top scorers capped at {@code limit}. */
    public List<TopScoresPlayer> getTopScoresFiltered(String seasonId, int typeId, int limit, String... includes) throws IOException, SportMonksException { return football.getTopScoresFiltered(seasonId, typeId, limit, includes); }
    /** Fetches top scorers by stage with default includes: {@code season, stage, player, type}. */
    public List<TopScoresPlayer> getTopScoresByStage(String stageId) throws IOException, SportMonksException { return football.getTopScoresByStage(stageId); }
    public List<TopScoresPlayer> getTopScoresByStage(String stageId, String... includes) throws IOException, SportMonksException { return football.getTopScoresByStage(stageId, includes); }
    /** Stage top scorers capped at {@code limit}. */
    public List<TopScoresPlayer> getTopScoresByStage(String stageId, int limit, String... includes) throws IOException, SportMonksException { return football.getTopScoresByStage(stageId, limit, includes); }

    // -------------------------------------------------------------------------
    // Standings
    // -------------------------------------------------------------------------

    public List<Standings> getStandings(String seasonId, String... includes) throws IOException, SportMonksException { return football.getStandings(seasonId, includes); }
    public TreeMap<String, List<Standings>> getStandingsCup(String seasonId, String... includes) throws IOException, SportMonksException { return football.getStandingsCup(seasonId, includes); }
    public List<Standings> getAllStandings(String... includes) throws IOException, SportMonksException { return football.getAllStandings(includes); }
    public List<Standings> getStandingsByRound(long roundId, String... includes) throws IOException, SportMonksException { return football.getStandingsByRound(roundId, includes); }
    public List<Standings> getStandingCorrectionsBySeason(long seasonId, String... includes) throws IOException, SportMonksException { return football.getStandingCorrectionsBySeason(seasonId, includes); }
    public List<Standings> getLiveStandingsByLeague(long leagueId, String... includes) throws IOException, SportMonksException { return football.getLiveStandingsByLeague(leagueId, includes); }
    public List<Standings> getGroupedStandingsByRound(long roundId, String... includes) throws IOException, SportMonksException { return football.getGroupedStandingsByRound(roundId, includes); }

    // -------------------------------------------------------------------------
    // Statistics
    // -------------------------------------------------------------------------

    public List<Statistic> getSeasonStatsByParticipant(long participantId, String... includes) throws IOException, SportMonksException { return football.getSeasonStatsByParticipant(participantId, includes); }
    public List<Statistic> getStatsByStage(long stageId, String... includes) throws IOException, SportMonksException { return football.getStatsByStage(stageId, includes); }
    public List<Statistic> getStatsByRound(long roundId, String... includes) throws IOException, SportMonksException { return football.getStatsByRound(roundId, includes); }

    // -------------------------------------------------------------------------
    // Schedules
    // -------------------------------------------------------------------------

    public List<Schedule> getSchedulesBySeason(long seasonId, String... includes) throws IOException, SportMonksException { return football.getSchedulesBySeason(seasonId, includes); }
    public List<Schedule> getSchedulesByTeam(long teamId, String... includes) throws IOException, SportMonksException { return football.getSchedulesByTeam(teamId, includes); }
    public List<Schedule> getSchedulesBySeasonAndTeam(long seasonId, long teamId, String... includes) throws IOException, SportMonksException { return football.getSchedulesBySeasonAndTeam(seasonId, teamId, includes); }

    // -------------------------------------------------------------------------
    // Transfers
    // -------------------------------------------------------------------------

    public List<Transfer> getAllTransfers(String... includes) throws IOException, SportMonksException { return football.getAllTransfers(includes); }
    /** All transfers capped at {@code limit} (e.g. the latest N). */
    public List<Transfer> getAllTransfers(int limit, String... includes) throws IOException, SportMonksException { return football.getAllTransfers(limit, includes); }
    public Transfer getTransferById(long id, String... includes) throws IOException, SportMonksException { return football.getTransferById(id, includes); }
    public List<Transfer> getLatestTransfers(String... includes) throws IOException, SportMonksException { return football.getLatestTransfers(includes); }
    public List<Transfer> getTransfersByDateRange(String startDate, String endDate, String... includes) throws IOException, SportMonksException { return football.getTransfersByDateRange(startDate, endDate, includes); }
    public List<Transfer> getTransfersByTeam(long teamId, String... includes) throws IOException, SportMonksException { return football.getTransfersByTeam(teamId, includes); }
    /** Transfers of a team capped at {@code limit}. */
    public List<Transfer> getTransfersByTeam(long teamId, int limit, String... includes) throws IOException, SportMonksException { return football.getTransfersByTeam(teamId, limit, includes); }
    public List<Transfer> getTransfersByPlayer(long playerId, String... includes) throws IOException, SportMonksException { return football.getTransfersByPlayer(playerId, includes); }

    // -------------------------------------------------------------------------
    // Transfer Rumours
    // -------------------------------------------------------------------------

    public List<TransferRumour> getAllTransferRumours(String... includes) throws IOException, SportMonksException { return football.getAllTransferRumours(includes); }
    /** All transfer rumours capped at {@code limit}. */
    public List<TransferRumour> getAllTransferRumours(int limit, String... includes) throws IOException, SportMonksException { return football.getAllTransferRumours(limit, includes); }
    public TransferRumour getTransferRumourById(long id, String... includes) throws IOException, SportMonksException { return football.getTransferRumourById(id, includes); }
    public List<TransferRumour> getTransferRumoursByDateRange(String startDate, String endDate, String... includes) throws IOException, SportMonksException { return football.getTransferRumoursByDateRange(startDate, endDate, includes); }
    public List<TransferRumour> getTransferRumoursByTeam(long teamId, String... includes) throws IOException, SportMonksException { return football.getTransferRumoursByTeam(teamId, includes); }
    public List<TransferRumour> getTransferRumoursByPlayer(long playerId, String... includes) throws IOException, SportMonksException { return football.getTransferRumoursByPlayer(playerId, includes); }

    // -------------------------------------------------------------------------
    // Rivals
    // -------------------------------------------------------------------------

    public List<Rival> getAllRivals(String... includes) throws IOException, SportMonksException { return football.getAllRivals(includes); }
    public List<Rival> getRivalsByTeam(long teamId, String... includes) throws IOException, SportMonksException { return football.getRivalsByTeam(teamId, includes); }

    // -------------------------------------------------------------------------
    // Commentaries
    // -------------------------------------------------------------------------

    public List<Comment> getAllCommentaries(String... includes) throws IOException, SportMonksException { return football.getAllCommentaries(includes); }
    public List<Comment> getCommentaryByFixture(long fixtureId, String... includes) throws IOException, SportMonksException { return football.getCommentaryByFixture(fixtureId, includes); }

    // -------------------------------------------------------------------------
    // TV Stations
    // -------------------------------------------------------------------------

    public List<TvStation> getAllTvStations(String... includes) throws IOException, SportMonksException { return football.getAllTvStations(includes); }
    /** All TV stations capped at {@code limit}. */
    public List<TvStation> getAllTvStations(int limit, String... includes) throws IOException, SportMonksException { return football.getAllTvStations(limit, includes); }
    public TvStation getTvStationById(long id, String... includes) throws IOException, SportMonksException { return football.getTvStationById(id, includes); }
    public List<TvStation> getTvStationsByFixture(long fixtureId, String... includes) throws IOException, SportMonksException { return football.getTvStationsByFixture(fixtureId, includes); }

    // -------------------------------------------------------------------------
    // Venues
    // -------------------------------------------------------------------------

    public List<Venue> getVenues(String seasonId) throws IOException, SportMonksException { return football.getVenues(seasonId); }
    public List<Venue> getAllVenues(String... includes) throws IOException, SportMonksException { return football.getAllVenues(includes); }
    public Venue getVenueById(long id, String... includes) throws IOException, SportMonksException { return football.getVenueById(id, includes); }
    public List<Venue> searchVenues(String name, String... includes) throws IOException, SportMonksException { return football.searchVenues(name, includes); }

    // -------------------------------------------------------------------------
    // Match Facts (beta)
    // -------------------------------------------------------------------------

    public List<MatchFact> getAllMatchFacts(String... includes) throws IOException, SportMonksException { return football.getAllMatchFacts(includes); }
    /** All match facts capped at {@code limit}. */
    public List<MatchFact> getAllMatchFacts(int limit, String... includes) throws IOException, SportMonksException { return football.getAllMatchFacts(limit, includes); }
    public List<MatchFact> getMatchFactsByFixture(long fixtureId, String... includes) throws IOException, SportMonksException { return football.getMatchFactsByFixture(fixtureId, includes); }
    public List<MatchFact> getMatchFactsByDateRange(String startDate, String endDate, String... includes) throws IOException, SportMonksException { return football.getMatchFactsByDateRange(startDate, endDate, includes); }
    public List<MatchFact> getMatchFactsByLeague(long leagueId, String... includes) throws IOException, SportMonksException { return football.getMatchFactsByLeague(leagueId, includes); }

    // -------------------------------------------------------------------------
    // Team of the Week (beta)
    // -------------------------------------------------------------------------

    public List<TeamOfTheWeek> getAllTeamsOfTheWeek(String... includes) throws IOException, SportMonksException { return football.getAllTeamsOfTheWeek(includes); }
    /** All teams of the week capped at {@code limit}. */
    public List<TeamOfTheWeek> getAllTeamsOfTheWeek(int limit, String... includes) throws IOException, SportMonksException { return football.getAllTeamsOfTheWeek(limit, includes); }
    public List<TeamOfTheWeek> getTeamOfTheWeekByRound(long roundId, String... includes) throws IOException, SportMonksException { return football.getTeamOfTheWeekByRound(roundId, includes); }
    public List<TeamOfTheWeek> getLatestTeamOfTheWeekByLeague(long leagueId, String... includes) throws IOException, SportMonksException { return football.getLatestTeamOfTheWeekByLeague(leagueId, includes); }

    // -------------------------------------------------------------------------
    // Team Rankings (beta)
    // -------------------------------------------------------------------------

    public List<TeamRanking> getAllTeamRankings(String... includes) throws IOException, SportMonksException { return football.getAllTeamRankings(includes); }
    /** Team rankings capped at {@code limit} (e.g. top 20). */
    public List<TeamRanking> getAllTeamRankings(int limit, String... includes) throws IOException, SportMonksException { return football.getAllTeamRankings(limit, includes); }
    public List<TeamRanking> getTeamRankingsByTeam(long teamId, String... includes) throws IOException, SportMonksException { return football.getTeamRankingsByTeam(teamId, includes); }
    public List<TeamRanking> getTeamRankingsByDate(String date, String... includes) throws IOException, SportMonksException { return football.getTeamRankingsByDate(date, includes); }

    // -------------------------------------------------------------------------
    // Expected Lineups (beta)
    // -------------------------------------------------------------------------

    public List<ExpectedLineup> getExpectedLineupByTeam(long teamId, String... includes) throws IOException, SportMonksException { return football.getExpectedLineupByTeam(teamId, includes); }
    public List<ExpectedLineup> getExpectedLineupsByPlayer(long playerId, String... includes) throws IOException, SportMonksException { return football.getExpectedLineupsByPlayer(playerId, includes); }

    // -------------------------------------------------------------------------
    // Teams
    // -------------------------------------------------------------------------

    public List<Team> getAllTeams(String... includes) throws IOException, SportMonksException { return football.getAllTeams(includes); }
    /** All teams capped at {@code limit}. */
    public List<Team> getAllTeams(int limit, String... includes) throws IOException, SportMonksException { return football.getAllTeams(limit, includes); }
    public List<Team> getTeamsBySeasonId(long seasonId, String... includes) throws IOException, SportMonksException { return football.getTeamsBySeasonId(seasonId, includes); }
    public List<Team> getTeamsByCountry(long countryId, String... includes) throws IOException, SportMonksException { return football.getTeamsByCountry(countryId, includes); }
    public Team getTeamById(long id, String... includes) throws IOException, SportMonksException { return football.getTeamById(id, includes); }
    public List<Team> searchTeams(String name, String... includes) throws IOException, SportMonksException { return football.searchTeams(name, includes); }
    /** Team search capped at {@code limit} results. */
    public List<Team> searchTeams(String name, int limit, String... includes) throws IOException, SportMonksException { return football.searchTeams(name, limit, includes); }

    // -------------------------------------------------------------------------
    // Leagues
    // -------------------------------------------------------------------------

    public List<League> getAllLeagues(String... includes) throws IOException, SportMonksException { return football.getAllLeagues(includes); }
    /** All leagues capped at {@code limit}. */
    public List<League> getAllLeagues(int limit, String... includes) throws IOException, SportMonksException { return football.getAllLeagues(limit, includes); }
    public League getLeagueById(long id, String... includes) throws IOException, SportMonksException { return football.getLeagueById(id, includes); }
    public List<League> getLiveLeagues(String... includes) throws IOException, SportMonksException { return football.getLiveLeagues(includes); }
    public List<League> getLeaguesByDate(String date, String... includes) throws IOException, SportMonksException { return football.getLeaguesByDate(date, includes); }
    public List<League> getLeaguesByCountry(long countryId, String... includes) throws IOException, SportMonksException { return football.getLeaguesByCountry(countryId, includes); }
    public List<League> searchLeagues(String name, String... includes) throws IOException, SportMonksException { return football.searchLeagues(name, includes); }
    /** League search capped at {@code limit} results. */
    public List<League> searchLeagues(String name, int limit, String... includes) throws IOException, SportMonksException { return football.searchLeagues(name, limit, includes); }
    public List<League> getLeaguesByTeam(long teamId, String... includes) throws IOException, SportMonksException { return football.getLeaguesByTeam(teamId, includes); }
    public List<League> getCurrentLeaguesByTeam(long teamId, String... includes) throws IOException, SportMonksException { return football.getCurrentLeaguesByTeam(teamId, includes); }

    // -------------------------------------------------------------------------
    // Stages
    // -------------------------------------------------------------------------

    public List<Stage> getAllStages(String... includes) throws IOException, SportMonksException { return football.getAllStages(includes); }
    public List<Stage> getStagesBySeasonId(long seasonId, String... includes) throws IOException, SportMonksException { return football.getStagesBySeasonId(seasonId, includes); }
    public Stage getStageById(long id, String... includes) throws IOException, SportMonksException { return football.getStageById(id, includes); }
    public List<Stage> searchStages(String name, String... includes) throws IOException, SportMonksException { return football.searchStages(name, includes); }

    // -------------------------------------------------------------------------
    // Rounds
    // -------------------------------------------------------------------------

    public List<Round> getAllRounds(String... includes) throws IOException, SportMonksException { return football.getAllRounds(includes); }
    public List<Round> getRoundsBySeasonId(long seasonId, String... includes) throws IOException, SportMonksException { return football.getRoundsBySeasonId(seasonId, includes); }
    public Round getRoundById(long id, String... includes) throws IOException, SportMonksException { return football.getRoundById(id, includes); }
    public List<Round> searchRounds(String name, String... includes) throws IOException, SportMonksException { return football.searchRounds(name, includes); }

    // -------------------------------------------------------------------------
    // States
    // -------------------------------------------------------------------------

    public List<State> getAllStates() throws IOException, SportMonksException { return football.getAllStates(); }
    public State getStateById(long id) throws IOException, SportMonksException { return football.getStateById(id); }

    // -------------------------------------------------------------------------
    // Squads
    // -------------------------------------------------------------------------

    public List<SquadPlayer> getSquadByTeam(long teamId, String... includes) throws IOException, SportMonksException { return football.getSquadByTeam(teamId, includes); }
    public List<SquadPlayer> getExtendedSquadByTeam(long teamId, String... includes) throws IOException, SportMonksException { return football.getExtendedSquadByTeam(teamId, includes); }
    public List<SquadPlayer> getSquadBySeasonAndTeam(long seasonId, long teamId, String... includes) throws IOException, SportMonksException { return football.getSquadBySeasonAndTeam(seasonId, teamId, includes); }

    // -------------------------------------------------------------------------
    // Players
    // -------------------------------------------------------------------------

    public List<Player> getAllPlayers(String... includes) throws IOException, SportMonksException { return football.getAllPlayers(includes); }
    /** All players capped at {@code limit} (recommended: the full catalogue is huge). */
    public List<Player> getAllPlayers(int limit, String... includes) throws IOException, SportMonksException { return football.getAllPlayers(limit, includes); }
    public Player getPlayerById(long id, String... includes) throws IOException, SportMonksException { return football.getPlayerById(id, includes); }
    public List<Player> getPlayersByCountry(long countryId, String... includes) throws IOException, SportMonksException { return football.getPlayersByCountry(countryId, includes); }
    /** Players of a country capped at {@code limit}. */
    public List<Player> getPlayersByCountry(long countryId, int limit, String... includes) throws IOException, SportMonksException { return football.getPlayersByCountry(countryId, limit, includes); }
    public List<Player> searchPlayers(String name, String... includes) throws IOException, SportMonksException { return football.searchPlayers(name, includes); }
    /** Player search capped at {@code limit} results. */
    public List<Player> searchPlayers(String name, int limit, String... includes) throws IOException, SportMonksException { return football.searchPlayers(name, limit, includes); }
    public List<Player> getLatestUpdatedPlayers(String... includes) throws IOException, SportMonksException { return football.getLatestUpdatedPlayers(includes); }

    // -------------------------------------------------------------------------
    // Coaches
    // -------------------------------------------------------------------------

    public List<Coach> getAllCoaches(String... includes) throws IOException, SportMonksException { return football.getAllCoaches(includes); }
    public Coach getCoachById(long id, String... includes) throws IOException, SportMonksException { return football.getCoachById(id, includes); }
    public List<Coach> getCoachesByCountry(long countryId, String... includes) throws IOException, SportMonksException { return football.getCoachesByCountry(countryId, includes); }
    public List<Coach> searchCoaches(String name, String... includes) throws IOException, SportMonksException { return football.searchCoaches(name, includes); }
    public List<Coach> getLatestUpdatedCoaches(String... includes) throws IOException, SportMonksException { return football.getLatestUpdatedCoaches(includes); }

    // -------------------------------------------------------------------------
    // Referees
    // -------------------------------------------------------------------------

    public List<Referee> getAllReferees(String... includes) throws IOException, SportMonksException { return football.getAllReferees(includes); }
    public Referee getRefereeById(long id, String... includes) throws IOException, SportMonksException { return football.getRefereeById(id, includes); }
    public List<Referee> getRefereesByCountry(long countryId, String... includes) throws IOException, SportMonksException { return football.getRefereesByCountry(countryId, includes); }
    public List<Referee> getRefereesBySeason(long seasonId, String... includes) throws IOException, SportMonksException { return football.getRefereesBySeason(seasonId, includes); }
    public List<Referee> searchReferees(String name, String... includes) throws IOException, SportMonksException { return football.searchReferees(name, includes); }

    // -------------------------------------------------------------------------
    // Core — Continents
    // -------------------------------------------------------------------------

    public List<Continent> getAllContinents(String... includes) throws IOException, SportMonksException { return core.getAllContinents(includes); }
    public Continent getContinentById(long id, String... includes) throws IOException, SportMonksException { return core.getContinentById(id, includes); }

    // -------------------------------------------------------------------------
    // Core — Countries
    // -------------------------------------------------------------------------

    public List<Country> getAllCountries(String... includes) throws IOException, SportMonksException { return core.getAllCountries(includes); }
    public Country getCountryById(long id, String... includes) throws IOException, SportMonksException { return core.getCountryById(id, includes); }
    public List<Country> searchCountries(String name, String... includes) throws IOException, SportMonksException { return core.searchCountries(name, includes); }

    // -------------------------------------------------------------------------
    // Core — Regions
    // -------------------------------------------------------------------------

    public List<Region> getAllRegions(String... includes) throws IOException, SportMonksException { return core.getAllRegions(includes); }
    public Region getRegionById(long id, String... includes) throws IOException, SportMonksException { return core.getRegionById(id, includes); }
    public List<Region> searchRegions(String name, String... includes) throws IOException, SportMonksException { return core.searchRegions(name, includes); }

    // -------------------------------------------------------------------------
    // Core — Cities
    // -------------------------------------------------------------------------

    public List<City> getAllCities(String... includes) throws IOException, SportMonksException { return core.getAllCities(includes); }
    public City getCityById(long id, String... includes) throws IOException, SportMonksException { return core.getCityById(id, includes); }
    public List<City> searchCities(String name, String... includes) throws IOException, SportMonksException { return core.searchCities(name, includes); }

    // -------------------------------------------------------------------------
    // Core — Types
    // -------------------------------------------------------------------------

    public List<Type> getAllTypes(String... includes) throws IOException, SportMonksException { return core.getAllTypes(includes); }
    public Type getTypeById(long id) throws IOException, SportMonksException { return core.getTypeById(id); }
    public List<Type> getTypesByEntity(String entity) throws IOException, SportMonksException { return core.getTypesByEntity(entity); }

    // -------------------------------------------------------------------------
    // Core — Timezones
    // -------------------------------------------------------------------------

    public List<String> getAllTimezones() throws IOException, SportMonksException { return core.getAllTimezones(); }

    // -------------------------------------------------------------------------
    // Core — Filters
    // -------------------------------------------------------------------------

    public Map<String, List<String>> getAllEntityFilters() throws IOException, SportMonksException { return core.getAllEntityFilters(); }

    // -------------------------------------------------------------------------
    // MySportmonks
    // -------------------------------------------------------------------------

    public MyApi getMyApi() throws IOException, SportMonksException { return core.getMyApi(); }
    public List<MyLeague> getMyLeagues(String... includes) throws IOException, SportMonksException { return core.getMyLeagues(includes); }
    public List<String> getMyEnrichments() throws IOException, SportMonksException { return core.getMyEnrichments(); }
    public List<MyResource> getMyResources() throws IOException, SportMonksException { return core.getMyResources(); }
    public List<MyUsage> getMyUsage() throws IOException, SportMonksException { return core.getMyUsage(); }
}
