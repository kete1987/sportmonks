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
 */
public class OddsApi extends SportMonksApiBase {

    private static final String PRE_MATCH_FIXTURES = "pre-match/fixtures";
    private static final String INPLAY_FIXTURES = "inplay/fixtures";
    private static final String BOOKMAKERS = "bookmakers";
    private static final String MARKETS = "markets";
    private static final String PREMIUM = "premium";

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
        super(client, locale, timezone, tracker);
        this.oddsBase = oddsBase;
        this.catalogBase = catalogBase;
    }

    private HttpUrl.Builder oddsUrl(String path, Object... segments) {
        return apiUrl(oddsBase, path, segments);
    }

    private HttpUrl.Builder catalogUrl(String path, Object... segments) {
        return apiUrl(catalogBase, path, segments);
    }

    private List<Odd> oddsData(OddsResponse resp) {
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Pre-match odds
    // -------------------------------------------------------------------------

    public List<Odd> getMatchOdds(String matchId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(oddsUrl(PRE_MATCH_FIXTURES, matchId), includes).build();
        return oddsData(get(url, OddsResponse.class));
    }

    public List<Odd> getMatchOddsByBookmaker(String matchId, String bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl(PRE_MATCH_FIXTURES, matchId, BOOKMAKERS, bookmakerId).build();
        return oddsData(get(url, OddsResponse.class));
    }

    public List<Odd> getMatchOddsByMarket(String matchId, String marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl(PRE_MATCH_FIXTURES, matchId, MARKETS, marketId).build();
        return oddsData(get(url, OddsResponse.class));
    }

    // -------------------------------------------------------------------------
    // Markets
    // -------------------------------------------------------------------------

    public List<Market> getAllMarkets() throws IOException, SportMonksException {
        HttpUrl base = catalogUrl(MARKETS).build();
        return fetchPaged(base, MarketsResponse.class, MarketsResponse::getData, MarketsResponse::getPagination, 0);
    }

    public Market getMarketById(long marketId) throws IOException, SportMonksException {
        HttpUrl url = catalogUrl(MARKETS, marketId).build();
        MarketResponse resp = get(url, MarketResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Market> searchMarkets(String query) throws IOException, SportMonksException {
        HttpUrl url = catalogUrl("markets/search", query).build();
        MarketsResponse resp = get(url, MarketsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Bookmakers
    // -------------------------------------------------------------------------

    public List<Bookmaker> getAllBookmakers() throws IOException, SportMonksException {
        HttpUrl base = catalogUrl(BOOKMAKERS).build();
        return fetchPaged(base, BookmakersResponse.class, BookmakersResponse::getData, BookmakersResponse::getPagination, 0);
    }

    public Bookmaker getBookmakerById(long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = catalogUrl(BOOKMAKERS, bookmakerId).build();
        BookmakerResponse resp = get(url, BookmakerResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Bookmaker> searchBookmakers(String query) throws IOException, SportMonksException {
        HttpUrl url = catalogUrl("bookmakers/search", query).build();
        BookmakersResponse resp = get(url, BookmakersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<BookmakerMapping> getBookmakerMappingsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = catalogUrl("bookmakers/fixtures", fixtureId, "mapping").build();
        BookmakerMappingsResponse resp = get(url, BookmakerMappingsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Inplay odds
    // -------------------------------------------------------------------------

    public List<Odd> getInplayOddsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl(INPLAY_FIXTURES, fixtureId).build();
        return oddsData(get(url, OddsResponse.class));
    }

    public List<Odd> getInplayOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl(INPLAY_FIXTURES, fixtureId, BOOKMAKERS, bookmakerId).build();
        return oddsData(get(url, OddsResponse.class));
    }

    public List<Odd> getInplayOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl(INPLAY_FIXTURES, fixtureId, MARKETS, marketId).build();
        return oddsData(get(url, OddsResponse.class));
    }

    public List<Odd> getLastUpdatedInplayOdds() throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/lastupdated").build();
        return oddsData(get(url, OddsResponse.class));
    }

    // -------------------------------------------------------------------------
    // Premium pre-match odds
    // -------------------------------------------------------------------------

    public List<Odd> getPremiumPreMatchOddsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl(PRE_MATCH_FIXTURES, fixtureId, PREMIUM).build();
        return oddsData(get(url, OddsResponse.class));
    }

    public List<Odd> getPremiumPreMatchOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl(PRE_MATCH_FIXTURES, fixtureId, BOOKMAKERS, bookmakerId, PREMIUM).build();
        return oddsData(get(url, OddsResponse.class));
    }

    public List<Odd> getPremiumPreMatchOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl(PRE_MATCH_FIXTURES, fixtureId, MARKETS, marketId, PREMIUM).build();
        return oddsData(get(url, OddsResponse.class));
    }

    public List<Odd> getLastUpdatedPremiumPreMatchOdds() throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/lastupdated/premium").build();
        return oddsData(get(url, OddsResponse.class));
    }

    // -------------------------------------------------------------------------
    // Premium inplay odds
    // -------------------------------------------------------------------------

    public List<Odd> getPremiumInplayOddsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl(INPLAY_FIXTURES, fixtureId, PREMIUM).build();
        return oddsData(get(url, OddsResponse.class));
    }

    public List<Odd> getPremiumInplayOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl(INPLAY_FIXTURES, fixtureId, BOOKMAKERS, bookmakerId, PREMIUM).build();
        return oddsData(get(url, OddsResponse.class));
    }

    public List<Odd> getPremiumInplayOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl(INPLAY_FIXTURES, fixtureId, MARKETS, marketId, PREMIUM).build();
        return oddsData(get(url, OddsResponse.class));
    }

    public List<Odd> getLastUpdatedPremiumInplayOdds() throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/lastupdated/premium").build();
        return oddsData(get(url, OddsResponse.class));
    }
}
