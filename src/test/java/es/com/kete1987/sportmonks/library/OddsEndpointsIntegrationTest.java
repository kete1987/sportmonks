package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.Bookmaker;
import es.com.kete1987.sportmonks.library.odds.model.Market;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import es.com.kete1987.sportmonks.library.odds.util.MarketIds;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * New odds endpoints, includes and filters against the real API (#78). Needs
 * {@code SPORTMONKS_API_KEY}; runs with {@code mvn test -Pintegration}.
 *
 * <p>Routes the test subscription does not cover answer 403 (the route exists); 404 or 422 would
 * mean a broken route, so those tests are green on 403. Their JSON could not be checked and is
 * therefore not asserted.
 */
class OddsEndpointsIntegrationTest {

    private static final int FORBIDDEN = 403;
    /** A finished fixture that keeps its pre-match odds. */
    private static final String FIXTURE = "18538184";
    private static final long FIXTURE_ID = 18538184L;
    /** 888Sport, one of the bookmakers with odds for the fixture. */
    private static final long BOOKMAKER = 5L;
    /** A five minute window, the maximum the {@code updated/between} routes accept. */
    private static final long FROM = 1767225600L;
    private static final long TO = FROM + 300L;

    private static SportMonksAPI api;
    private static List<Odd> unfiltered;

    @BeforeAll
    static void setUp() throws IOException, SportMonksException {
        String key = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(key != null && !key.isEmpty(), "SPORTMONKS_API_KEY not set");
        api = new SportMonksAPI(key);
        unfiltered = api.getMatchOdds(FIXTURE);
    }

    private static void assertRouteExistsWithoutAccess(Executable call) {
        SportMonksException e = assertThrows(SportMonksException.class, call);
        assertEquals(FORBIDDEN, e.getStatusCode(), "Only 403 proves the route exists: " + e.getMessage());
    }

    // ---- 200 verified

    @Test
    void getAllPreMatchOdds_returnsTheRequestedNumberOfRows() throws IOException, SportMonksException {
        List<Odd> odds = api.getAllPreMatchOdds(3);

        assertEquals(3, odds.size());
        assertNotNull(odds.get(0).getFixtureId());
    }

    @Test
    void getLatestPreMatchOdds_appliesIncludesAndFilters() throws IOException, SportMonksException {
        List<Odd> odds = api.getLatestPreMatchOdds(new String[]{"market"}, "markets:" + MarketIds.FULLTIME_RESULT);

        assertFalse(odds.isEmpty());
        assertTrue(odds.stream().allMatch(o -> o.getMarketId() == MarketIds.FULLTIME_RESULT));
        assertTrue(odds.stream().allMatch(o -> o.getMarket() != null && o.getMarket().getId() == MarketIds.FULLTIME_RESULT));
    }

    @Test
    void marketsFilter_reducesTheRows() throws IOException, SportMonksException {
        List<Odd> odds = api.getMatchOdds(FIXTURE, new String[0], "markets:" + MarketIds.FULLTIME_RESULT);

        assertFalse(odds.isEmpty());
        assertTrue(odds.size() < unfiltered.size());
        assertTrue(odds.stream().allMatch(o -> o.getMarketId() == MarketIds.FULLTIME_RESULT));
    }

    @Test
    void bookmakersFilter_reducesTheRows() throws IOException, SportMonksException {
        List<Odd> odds = api.getMatchOdds(FIXTURE, new String[0], "bookmakers:" + BOOKMAKER);

        assertFalse(odds.isEmpty());
        assertTrue(odds.size() < unfiltered.size());
        assertTrue(odds.stream().allMatch(o -> o.getBookmakerId() == BOOKMAKER));
    }

    @Test
    void winningOddsFilter_keepsOnlyWinningRows() throws IOException, SportMonksException {
        List<Odd> odds = api.getMatchOdds(FIXTURE, new String[0], "WinningOdds");

        assertFalse(odds.isEmpty());
        assertTrue(odds.size() < unfiltered.size());
        assertTrue(odds.stream().allMatch(Odd::getWinning));
    }

    @Test
    void filtersCombineWithSemicolons() throws IOException, SportMonksException {
        List<Odd> odds = api.getMatchOdds(FIXTURE, new String[]{"market", "bookmaker"},
                "markets:" + MarketIds.FULLTIME_RESULT + ";bookmakers:" + BOOKMAKER);

        assertFalse(odds.isEmpty());
        assertTrue(odds.size() <= 3);
        assertTrue(odds.stream().allMatch(o -> o.getMarket() != null && o.getBookmaker() != null));
    }

    @Test
    void byBookmaker_acceptsIncludesAndFilters() throws IOException, SportMonksException {
        List<Odd> odds = api.getMatchOddsByBookmaker(FIXTURE, String.valueOf(BOOKMAKER),
                new String[]{"market"}, "markets:" + MarketIds.FULLTIME_RESULT);

        assertFalse(odds.isEmpty());
        assertTrue(odds.stream().allMatch(o -> o.getBookmakerId() == BOOKMAKER && o.getMarket() != null));
        assertTrue(odds.stream().allMatch(o -> o.getMarketId() == MarketIds.FULLTIME_RESULT));
    }

    @Test
    void byMarket_acceptsIncludes() throws IOException, SportMonksException {
        List<Odd> odds = api.getMatchOddsByMarket(FIXTURE, String.valueOf(MarketIds.FULLTIME_RESULT), "bookmaker");

        assertFalse(odds.isEmpty());
        assertTrue(odds.stream().allMatch(o -> o.getBookmaker() != null));
    }

    @Test
    void getBookmakersByFixture_listsTheBookmakersWithOdds() throws IOException, SportMonksException {
        List<Bookmaker> bookmakers = api.getBookmakersByFixture(FIXTURE_ID);

        assertFalse(bookmakers.isEmpty());
        assertTrue(bookmakers.stream().anyMatch(b -> b.getId() == BOOKMAKER));
    }

    @Test
    void premiumCatalogues_areAvailable() throws IOException, SportMonksException {
        List<Bookmaker> bookmakers = api.getPremiumBookmakers();
        List<Market> markets = api.getPremiumMarkets();

        assertFalse(bookmakers.isEmpty());
        assertFalse(markets.isEmpty());
        assertEquals(MarketIds.FULLTIME_RESULT, markets.get(0).getId());
    }

    @Test
    void searchBookmakers_withLimitReturnsOnlyThatMany() throws IOException, SportMonksException {
        assertEquals(1, api.searchBookmakers("bet", 1).size());
    }

    // ---- 403: the route exists, the subscription does not cover it

    @Test
    void getAllInplayOdds_routeExists() {
        assertRouteExistsWithoutAccess(api::getAllInplayOdds);
    }

    @Test
    void inplayByFixture_routeExists() {
        assertRouteExistsWithoutAccess(() -> api.getInplayOddsByFixture(FIXTURE_ID, new String[]{"market"}, "markets:1"));
    }

    @Test
    void getAllPremiumOdds_routeExists() {
        assertRouteExistsWithoutAccess(api::getAllPremiumOdds);
    }

    @Test
    void getPremiumOddsHistory_routeExists() {
        assertRouteExistsWithoutAccess(api::getPremiumOddsHistory);
    }

    @Test
    void getLastUpdatedPremiumPreMatchOdds_routeExists() {
        assertRouteExistsWithoutAccess(() -> api.getLastUpdatedPremiumPreMatchOdds(FROM, TO));
    }

    @Test
    void getLastUpdatedPremiumOddsHistory_routeExists() {
        assertRouteExistsWithoutAccess(() -> api.getLastUpdatedPremiumOddsHistory(FROM, TO));
    }
}
