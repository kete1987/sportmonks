package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.Constants;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.Bookmaker;
import es.com.kete1987.sportmonks.library.odds.model.BookmakerMapping;
import es.com.kete1987.sportmonks.library.odds.model.BookmakerMappingsResponse;
import es.com.kete1987.sportmonks.library.odds.model.BookmakerResponse;
import es.com.kete1987.sportmonks.library.odds.model.BookmakersResponse;
import es.com.kete1987.sportmonks.library.odds.model.Market;
import es.com.kete1987.sportmonks.library.odds.model.MarketResponse;
import es.com.kete1987.sportmonks.library.odds.model.MarketsResponse;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import es.com.kete1987.sportmonks.library.odds.model.OddsResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Odds sub-API: pre-match odds, inplay odds, premium odds feeds, markets, and bookmakers.
 *
 * <p>Can be used standalone ({@code new OddsApi("apiKey")}) or via the
 * {@link SportMonksAPI} facade ({@code api.getOdds()}).
 *
 * <p><b>Includes and filters.</b> Every odds method has an overload with {@code String[] includes}
 * ({@code market}, {@code bookmaker}, {@code fixture}) and {@code String filters}. Filters are
 * separated by semicolons and take comma separated ids: {@code markets:1,2},
 * {@code bookmakers:2,13} and {@code WinningOdds} (only the winning selections), for example
 * {@code "markets:1;bookmakers:2"}. The API applies them, so they also cut the download: the odds
 * of one fixture are thousands of rows (around 3 MB) without a filter.
 *
 * <p><b>Subscription.</b> The premium and inplay routes answer 403
 * ({@link SportMonksException#getStatusCode()}) when the plan does not include them. Their
 * response shape follows the documentation and the pre-match odds; it could not be checked against
 * real data.
 */
public class OddsApi extends SportMonksApiBase {

    private static final String PRE_MATCH = "pre-match";
    private static final String PRE_MATCH_FIXTURES = "pre-match/fixtures";
    private static final String INPLAY = "inplay";
    private static final String INPLAY_FIXTURES = "inplay/fixtures";
    private static final String PREMIUM = "premium";
    private static final String PREMIUM_FIXTURES = "premium/fixtures";
    private static final String BOOKMAKERS = "bookmakers";
    private static final String MARKETS = "markets";
    private static final String NO_FILTERS = null;
    private static final String[] NO_INCLUDES = new String[0];

    private final String oddsBase;
    private final String catalogBase;

    public OddsApi(String apiToken) {
        this(apiToken, null);
    }

    public OddsApi(String apiToken, String locale) {
        this(apiToken, locale, null);
    }

    public OddsApi(String apiToken, String locale, String timezone) {
        this(apiToken, locale, timezone, ClientOptions.defaults());
    }

    /**
     * Creates the client with custom HTTP settings (timeouts, an own {@code OkHttpClient}).
     * Pass {@code null} for the default locale or timezone.
     */
    public OddsApi(String apiToken, String locale, String timezone, ClientOptions options) {
        this(buildHttpClient(apiToken, options), Constants.BASE_URL_ODDS, Constants.BASE_URL_ODDS_CATALOG,
                locale, timezone, new RateLimitTracker());
    }

    OddsApi(OkHttpClient client, String oddsBase, String catalogBase, String locale, String timezone, RateLimitTracker tracker) {
        this(client, oddsBase, catalogBase, locale, timezone, tracker, RequestOptions.none());
    }

    private OddsApi(OkHttpClient client, String oddsBase, String catalogBase, String locale, String timezone,
                    RateLimitTracker tracker, RequestOptions options) {
        super(client, locale, timezone, tracker, options);
        this.oddsBase = oddsBase;
        this.catalogBase = catalogBase;
    }

    /**
     * A view of this client that adds {@code options} to every request; see
     * {@link FootballApi#withOptions(RequestOptions)}. The {@code filters} of the options are
     * appended with {@code ;} to the ones passed to a method (e.g. {@code markets:1;WinningOdds}).
     */
    public OddsApi withOptions(RequestOptions options) {
        return new OddsApi(httpClient, oddsBase, catalogBase, locale, timezone, rateLimitTracker, options);
    }

    private HttpUrl.Builder oddsUrl(String path, Object... segments) {
        return apiUrl(oddsBase, path, segments);
    }

    private HttpUrl.Builder catalogUrl(String path, Object... segments) {
        return apiUrl(catalogBase, path, segments);
    }

    /** One request, no pagination: the per fixture routes and the {@code latest} / {@code between} feeds. */
    private List<Odd> oddsOf(HttpUrl.Builder url, String[] includes, String filters) throws IOException, SportMonksException {
        OddsResponse resp = get(withFilters(withIncludes(url, includes), filters).build(), OddsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    /** Every page of a feed; a {@code limit} above zero stops as soon as it is reached. */
    private List<Odd> pagedOddsOf(HttpUrl.Builder url, int limit, String[] includes, String filters)
            throws IOException, SportMonksException {
        HttpUrl base = withFilters(withIncludes(url, includes), filters).build();
        return fetchPaged(base, OddsResponse.class, OddsResponse::getData, OddsResponse::getPagination, limit);
    }

    // -------------------------------------------------------------------------
    // Pre-match odds
    // -------------------------------------------------------------------------

    /**
     * All pre-match odds of every fixture, paged. <b>Huge:</b> it holds millions of rows, so pass a
     * {@code limit} ({@link #getAllPreMatchOdds(int, String[], String)}) or filter by market and
     * bookmaker. Without a limit this walks the whole table.
     */
    public List<Odd> getAllPreMatchOdds() throws IOException, SportMonksException {
        return getAllPreMatchOdds(0);
    }

    /** Like {@link #getAllPreMatchOdds()} but returns at most {@code limit} rows (0 or less means all). */
    public List<Odd> getAllPreMatchOdds(int limit) throws IOException, SportMonksException {
        return getAllPreMatchOdds(limit, NO_INCLUDES, NO_FILTERS);
    }

    /**
     * Like {@link #getAllPreMatchOdds(int)} with includes and filters.
     *
     * @param filters unencoded filter expression (e.g. {@code markets:1;bookmakers:2}); null or blank omits filters
     */
    public List<Odd> getAllPreMatchOdds(int limit, String[] includes, String filters) throws IOException, SportMonksException {
        return pagedOddsOf(oddsUrl(PRE_MATCH), limit, includes, filters);
    }

    /**
     * The pre-match odds that changed most recently, across all fixtures. The API does not paginate
     * this feed and ignores {@code per_page}, and it is heavy (from tens of kilobytes to about 3 MB
     * depending on how much changed), so filter it with {@code markets:} or {@code bookmakers:}.
     */
    public List<Odd> getLatestPreMatchOdds() throws IOException, SportMonksException {
        return getLatestPreMatchOdds(NO_INCLUDES, NO_FILTERS);
    }

    /** Like {@link #getLatestPreMatchOdds()} with includes and filters (e.g. {@code markets:1}). */
    public List<Odd> getLatestPreMatchOdds(String[] includes, String filters) throws IOException, SportMonksException {
        return oddsOf(oddsUrl("pre-match/latest"), includes, filters);
    }

    /** Pre-match odds of one fixture. Without a filter this is thousands of rows; see the class notes. */
    public List<Odd> getMatchOdds(String matchId, String... includes) throws IOException, SportMonksException {
        return getMatchOdds(matchId, includes, NO_FILTERS);
    }

    /** Like {@link #getMatchOdds(String, String...)} with filters (e.g. {@code markets:1;bookmakers:2}). */
    public List<Odd> getMatchOdds(String matchId, String[] includes, String filters) throws IOException, SportMonksException {
        return oddsOf(oddsUrl(PRE_MATCH_FIXTURES, matchId), includes, filters);
    }

    public List<Odd> getMatchOddsByBookmaker(String matchId, String bookmakerId) throws IOException, SportMonksException {
        return getMatchOddsByBookmaker(matchId, bookmakerId, NO_INCLUDES, NO_FILTERS);
    }

    public List<Odd> getMatchOddsByBookmaker(String matchId, String bookmakerId, String... includes)
            throws IOException, SportMonksException {
        return getMatchOddsByBookmaker(matchId, bookmakerId, includes, NO_FILTERS);
    }

    public List<Odd> getMatchOddsByBookmaker(String matchId, String bookmakerId, String[] includes, String filters)
            throws IOException, SportMonksException {
        return oddsOf(oddsUrl(PRE_MATCH_FIXTURES, matchId, BOOKMAKERS, bookmakerId), includes, filters);
    }

    public List<Odd> getMatchOddsByMarket(String matchId, String marketId) throws IOException, SportMonksException {
        return getMatchOddsByMarket(matchId, marketId, NO_INCLUDES, NO_FILTERS);
    }

    public List<Odd> getMatchOddsByMarket(String matchId, String marketId, String... includes)
            throws IOException, SportMonksException {
        return getMatchOddsByMarket(matchId, marketId, includes, NO_FILTERS);
    }

    public List<Odd> getMatchOddsByMarket(String matchId, String marketId, String[] includes, String filters)
            throws IOException, SportMonksException {
        return oddsOf(oddsUrl(PRE_MATCH_FIXTURES, matchId, MARKETS, marketId), includes, filters);
    }

    // -------------------------------------------------------------------------
    // Markets
    // -------------------------------------------------------------------------

    public List<Market> getAllMarkets() throws IOException, SportMonksException {
        HttpUrl base = catalogUrl(MARKETS).build();
        return fetchPaged(base, MarketsResponse.class, MarketsResponse::getData, MarketsResponse::getPagination, 0);
    }

    /** The markets available in the premium odds feed. */
    public List<Market> getPremiumMarkets() throws IOException, SportMonksException {
        HttpUrl base = catalogUrl("markets/premium").build();
        return fetchPaged(base, MarketsResponse.class, MarketsResponse::getData, MarketsResponse::getPagination, 0);
    }

    public Market getMarketById(long marketId) throws IOException, SportMonksException {
        HttpUrl url = catalogUrl(MARKETS, marketId).build();
        MarketResponse resp = get(url, MarketResponse.class);
        return resp != null ? resp.getData() : null;
    }

    /** Searches the markets by name, walking every page of results. */
    public List<Market> searchMarkets(String query) throws IOException, SportMonksException {
        return searchMarkets(query, 0);
    }

    /** Like {@link #searchMarkets(String)} but returns at most {@code limit} markets (0 or less means all). */
    public List<Market> searchMarkets(String query, int limit) throws IOException, SportMonksException {
        HttpUrl base = catalogUrl("markets/search", query).build();
        return fetchPaged(base, MarketsResponse.class, MarketsResponse::getData, MarketsResponse::getPagination, limit);
    }

    // -------------------------------------------------------------------------
    // Bookmakers
    // -------------------------------------------------------------------------

    public List<Bookmaker> getAllBookmakers() throws IOException, SportMonksException {
        HttpUrl base = catalogUrl(BOOKMAKERS).build();
        return fetchPaged(base, BookmakersResponse.class, BookmakersResponse::getData, BookmakersResponse::getPagination, 0);
    }

    /** The bookmakers available in the premium odds feed. */
    public List<Bookmaker> getPremiumBookmakers() throws IOException, SportMonksException {
        HttpUrl base = catalogUrl("bookmakers/premium").build();
        return fetchPaged(base, BookmakersResponse.class, BookmakersResponse::getData, BookmakersResponse::getPagination, 0);
    }

    /** The bookmakers that have pre-match odds for the fixture. */
    public List<Bookmaker> getBookmakersByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl base = catalogUrl("bookmakers/fixtures", fixtureId).build();
        return fetchPaged(base, BookmakersResponse.class, BookmakersResponse::getData, BookmakersResponse::getPagination, 0);
    }

    public Bookmaker getBookmakerById(long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = catalogUrl(BOOKMAKERS, bookmakerId).build();
        BookmakerResponse resp = get(url, BookmakerResponse.class);
        return resp != null ? resp.getData() : null;
    }

    /** Searches the bookmakers by name, walking every page of results. */
    public List<Bookmaker> searchBookmakers(String query) throws IOException, SportMonksException {
        return searchBookmakers(query, 0);
    }

    /** Like {@link #searchBookmakers(String)} but returns at most {@code limit} bookmakers (0 or less means all). */
    public List<Bookmaker> searchBookmakers(String query, int limit) throws IOException, SportMonksException {
        HttpUrl base = catalogUrl("bookmakers/search", query).build();
        return fetchPaged(base, BookmakersResponse.class, BookmakersResponse::getData, BookmakersResponse::getPagination, limit);
    }

    public List<BookmakerMapping> getBookmakerMappingsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = catalogUrl("bookmakers/fixtures", fixtureId, "mapping").build();
        BookmakerMappingsResponse resp = get(url, BookmakerMappingsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Inplay odds
    // -------------------------------------------------------------------------

    /** The odds of every fixture in play, paged. */
    public List<Odd> getAllInplayOdds() throws IOException, SportMonksException {
        return getAllInplayOdds(0, NO_INCLUDES, NO_FILTERS);
    }

    /** Like {@link #getAllInplayOdds()} but returns at most {@code limit} rows (0 or less means all). */
    public List<Odd> getAllInplayOdds(int limit, String[] includes, String filters) throws IOException, SportMonksException {
        return pagedOddsOf(oddsUrl(INPLAY), limit, includes, filters);
    }

    public List<Odd> getInplayOddsByFixture(long fixtureId) throws IOException, SportMonksException {
        return getInplayOddsByFixture(fixtureId, NO_INCLUDES, NO_FILTERS);
    }

    public List<Odd> getInplayOddsByFixture(long fixtureId, String[] includes, String filters)
            throws IOException, SportMonksException {
        return oddsOf(oddsUrl(INPLAY_FIXTURES, fixtureId), includes, filters);
    }

    public List<Odd> getInplayOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId)
            throws IOException, SportMonksException {
        return getInplayOddsByFixtureAndBookmaker(fixtureId, bookmakerId, NO_INCLUDES, NO_FILTERS);
    }

    public List<Odd> getInplayOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId, String[] includes, String filters)
            throws IOException, SportMonksException {
        return oddsOf(oddsUrl(INPLAY_FIXTURES, fixtureId, BOOKMAKERS, bookmakerId), includes, filters);
    }

    public List<Odd> getInplayOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException {
        return getInplayOddsByFixtureAndMarket(fixtureId, marketId, NO_INCLUDES, NO_FILTERS);
    }

    public List<Odd> getInplayOddsByFixtureAndMarket(long fixtureId, long marketId, String[] includes, String filters)
            throws IOException, SportMonksException {
        return oddsOf(oddsUrl(INPLAY_FIXTURES, fixtureId, MARKETS, marketId), includes, filters);
    }

    /** The inplay odds that changed most recently ({@code inplay/latest}). */
    public List<Odd> getLastUpdatedInplayOdds() throws IOException, SportMonksException {
        return getLastUpdatedInplayOdds(NO_INCLUDES, NO_FILTERS);
    }

    public List<Odd> getLastUpdatedInplayOdds(String[] includes, String filters) throws IOException, SportMonksException {
        return oddsOf(oddsUrl("inplay/latest"), includes, filters);
    }

    // -------------------------------------------------------------------------
    // Premium odds
    // -------------------------------------------------------------------------

    /** All premium odds, paged. Huge: pass a limit ({@link #getAllPremiumOdds(int, String[], String)}) or filters. */
    public List<Odd> getAllPremiumOdds() throws IOException, SportMonksException {
        return getAllPremiumOdds(0, NO_INCLUDES, NO_FILTERS);
    }

    /** Like {@link #getAllPremiumOdds()} but returns at most {@code limit} rows (0 or less means all). */
    public List<Odd> getAllPremiumOdds(int limit, String[] includes, String filters) throws IOException, SportMonksException {
        return pagedOddsOf(oddsUrl(PREMIUM), limit, includes, filters);
    }

    public List<Odd> getPremiumPreMatchOddsByFixture(long fixtureId) throws IOException, SportMonksException {
        return getPremiumPreMatchOddsByFixture(fixtureId, NO_INCLUDES, NO_FILTERS);
    }

    public List<Odd> getPremiumPreMatchOddsByFixture(long fixtureId, String[] includes, String filters)
            throws IOException, SportMonksException {
        return oddsOf(oddsUrl(PREMIUM_FIXTURES, fixtureId), includes, filters);
    }

    public List<Odd> getPremiumPreMatchOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId)
            throws IOException, SportMonksException {
        return getPremiumPreMatchOddsByFixtureAndBookmaker(fixtureId, bookmakerId, NO_INCLUDES, NO_FILTERS);
    }

    public List<Odd> getPremiumPreMatchOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId, String[] includes, String filters)
            throws IOException, SportMonksException {
        return oddsOf(oddsUrl(PREMIUM_FIXTURES, fixtureId, BOOKMAKERS, bookmakerId), includes, filters);
    }

    public List<Odd> getPremiumPreMatchOddsByFixtureAndMarket(long fixtureId, long marketId)
            throws IOException, SportMonksException {
        return getPremiumPreMatchOddsByFixtureAndMarket(fixtureId, marketId, NO_INCLUDES, NO_FILTERS);
    }

    public List<Odd> getPremiumPreMatchOddsByFixtureAndMarket(long fixtureId, long marketId, String[] includes, String filters)
            throws IOException, SportMonksException {
        return oddsOf(oddsUrl(PREMIUM_FIXTURES, fixtureId, MARKETS, marketId), includes, filters);
    }

    /**
     * Premium odds updated between two moments, given as Unix timestamps in seconds. The API rejects
     * ranges longer than five minutes with a 422.
     */
    public List<Odd> getLastUpdatedPremiumPreMatchOdds(long from, long to) throws IOException, SportMonksException {
        return getLastUpdatedPremiumPreMatchOdds(from, to, NO_INCLUDES, NO_FILTERS);
    }

    public List<Odd> getLastUpdatedPremiumPreMatchOdds(long from, long to, String[] includes, String filters)
            throws IOException, SportMonksException {
        return oddsOf(oddsUrl("premium/updated/between", from, to), includes, filters);
    }

    /** The premium odds history, paged. Huge: pass a limit ({@link #getPremiumOddsHistory(int, String[], String)}) or filters. */
    public List<Odd> getPremiumOddsHistory() throws IOException, SportMonksException {
        return getPremiumOddsHistory(0, NO_INCLUDES, NO_FILTERS);
    }

    /** Like {@link #getPremiumOddsHistory()} but returns at most {@code limit} rows (0 or less means all). */
    public List<Odd> getPremiumOddsHistory(int limit, String[] includes, String filters) throws IOException, SportMonksException {
        return pagedOddsOf(oddsUrl("premium/history"), limit, includes, filters);
    }

    /** Premium odds history updated between two Unix timestamps in seconds (at most five minutes apart). */
    public List<Odd> getLastUpdatedPremiumOddsHistory(long from, long to) throws IOException, SportMonksException {
        return getLastUpdatedPremiumOddsHistory(from, to, NO_INCLUDES, NO_FILTERS);
    }

    public List<Odd> getLastUpdatedPremiumOddsHistory(long from, long to, String[] includes, String filters)
            throws IOException, SportMonksException {
        return oddsOf(oddsUrl("premium/history/updated/between", from, to), includes, filters);
    }
}
