package es.com.kete1987.sportmonks.library;

import com.google.gson.JsonElement;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.BookmakerMapping;
import es.com.kete1987.sportmonks.library.odds.model.Market;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Runs the odds routes against the real API (#78). With the test subscription a 403 means the route
 * exists but is not covered, while 404 means the route does not exist, so premium and inplay routes
 * are green on 403. Needs {@code SPORTMONKS_API_KEY}; runs with {@code mvn test -Pintegration}.
 */
class OddsRealApiIntegrationTest {

    private static final int FORBIDDEN = 403;
    /** A finished fixture that keeps its pre-match odds and bookmaker mappings. */
    private static final long FIXTURE = 18538184L;

    private static String key;
    private static SportMonksAPI api;

    @BeforeAll
    static void setUp() {
        key = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(key != null && !key.isEmpty(), "SPORTMONKS_API_KEY not set");
        api = new SportMonksAPI(key);
    }

    private static void assertRouteExistsWithoutAccess(Executable call) {
        SportMonksException e = assertThrows(SportMonksException.class, call);
        assertEquals(FORBIDDEN, e.getStatusCode(), "404 would mean the route does not exist: " + e.getMessage());
    }

    @Test
    void lastUpdatedInplayOdds_routeExists() {
        assertRouteExistsWithoutAccess(() -> api.getLastUpdatedInplayOdds());
    }

    @Test
    void premiumPreMatchOddsByFixture_routeExists() {
        assertRouteExistsWithoutAccess(() -> api.getPremiumPreMatchOddsByFixture(FIXTURE));
    }

    @Test
    void premiumPreMatchOddsByFixtureAndBookmaker_routeExists() {
        assertRouteExistsWithoutAccess(() -> api.getPremiumPreMatchOddsByFixtureAndBookmaker(FIXTURE, 2L));
    }

    @Test
    void premiumPreMatchOddsByFixtureAndMarket_routeExists() {
        assertRouteExistsWithoutAccess(() -> api.getPremiumPreMatchOddsByFixtureAndMarket(FIXTURE, 1L));
    }

    @Test
    void searchMarkets_returnsMoreThanTheFirstPage() throws IOException, SportMonksException {
        List<Market> markets = api.searchMarkets("goal");

        assertTrue(markets.size() > 25, "The search has more than one 25-row page, got " + markets.size());
    }

    @Test
    void bookmakerMapping_declaresEveryRealKey() throws IOException, InterruptedException {
        JsonElement data = JsonCoverage.fetchData(key, "odds/bookmakers/fixtures/" + FIXTURE + "/mapping");

        assertEquals(Collections.emptyList(), JsonCoverage.missing(data, BookmakerMapping.class));
    }

    @Test
    void market_declaresEveryRealKey() throws IOException, InterruptedException {
        JsonElement data = JsonCoverage.fetchData(key, "odds/markets?per_page=50");

        assertEquals(Collections.emptyList(), JsonCoverage.missing(data, Market.class));
    }

    @Test
    void odd_declaresEveryRealKeyIncludingMarketBookmakerAndFixture() throws IOException, InterruptedException {
        String path = "football/odds/pre-match/fixtures/" + FIXTURE + "/markets/1?include=market;bookmaker;fixture";
        JsonElement data = JsonCoverage.fetchData(key, path);

        assertEquals(Collections.emptyList(), JsonCoverage.missing(data, Odd.class));
    }
}
