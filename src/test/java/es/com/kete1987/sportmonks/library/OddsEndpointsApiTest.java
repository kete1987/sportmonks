package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.Bookmaker;
import es.com.kete1987.sportmonks.library.odds.model.Market;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import okhttp3.HttpUrl;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/** Endpoints, includes and filters added to {@link OddsApi} (#78). */
class OddsEndpointsApiTest extends BaseApiTest {

    private static final String ODDS = "/v3/football/odds/";
    private static final String CATALOG = "/v3/odds/";
    private static final String[] INCLUDES = {"market", "bookmaker"};
    private static final String FILTERS = "markets:1,2;bookmakers:2";

    private HttpUrl nextUrl() throws InterruptedException {
        return server.takeRequest().getRequestUrl();
    }

    private void assertIncludesAndFilters(HttpUrl url) {
        assertEquals("market;bookmaker", url.queryParameter("include"));
        assertEquals(FILTERS, url.queryParameter("filters"));
    }

    // ---- pre-match

    @Test
    void getAllPreMatchOdds_walksPreMatchWithLimit() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        List<Odd> odds = api.getAllPreMatchOdds(1);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "pre-match", url.encodedPath());
        assertEquals("1", url.queryParameter("per_page"));
        assertEquals(1, odds.size());
    }

    @Test
    void getAllPreMatchOdds_acceptsIncludesAndFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getAllPreMatchOdds(5, INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "pre-match", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void getLatestPreMatchOdds_usesLatestWithoutPagination() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds_included.json");

        List<Odd> odds = api.getLatestPreMatchOdds(INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "pre-match/latest", url.encodedPath());
        assertIncludesAndFilters(url);
        assertNull(url.queryParameter("per_page"));
        assertEquals(1, odds.size());
    }

    @Test
    void getMatchOdds_acceptsFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getMatchOdds("7", INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "pre-match/fixtures/7", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void getMatchOddsByBookmaker_acceptsIncludes() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getMatchOddsByBookmaker("7", "2", "market", "fixture");

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "pre-match/fixtures/7/bookmakers/2", url.encodedPath());
        assertEquals("market;fixture", url.queryParameter("include"));
    }

    @Test
    void getMatchOddsByBookmaker_acceptsIncludesAndFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getMatchOddsByBookmaker("7", "2", INCLUDES, FILTERS);

        assertIncludesAndFilters(nextUrl());
    }

    @Test
    void getMatchOddsByMarket_acceptsIncludesAndFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getMatchOddsByMarket("7", "57", INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "pre-match/fixtures/7/markets/57", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void getMatchOddsByMarket_acceptsIncludes() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getMatchOddsByMarket("7", "57", "bookmaker");

        assertEquals("bookmaker", nextUrl().queryParameter("include"));
    }

    // ---- catalogue

    @Test
    void getBookmakersByFixture_usesTheCatalogue() throws IOException, SportMonksException, InterruptedException {
        enqueue("bookmakers.json");

        List<Bookmaker> bookmakers = api.getBookmakersByFixture(19722082L);

        assertEquals(CATALOG + "bookmakers/fixtures/19722082", nextUrl().encodedPath());
        assertEquals(2, bookmakers.size());
    }

    @Test
    void getPremiumBookmakers_usesTheCatalogue() throws IOException, SportMonksException, InterruptedException {
        enqueue("bookmakers.json");

        api.getPremiumBookmakers();

        assertEquals(CATALOG + "bookmakers/premium", nextUrl().encodedPath());
    }

    @Test
    void getPremiumMarkets_usesTheCatalogue() throws IOException, SportMonksException, InterruptedException {
        enqueue("markets.json");

        List<Market> markets = api.getPremiumMarkets();

        assertEquals(CATALOG + "markets/premium", nextUrl().encodedPath());
        assertEquals(2, markets.size());
    }

    @Test
    void searchMarkets_withLimitStopsAtTheLimit() throws IOException, SportMonksException, InterruptedException {
        enqueue("markets_search_page1.json");

        List<Market> markets = api.searchMarkets("goal", 2);

        HttpUrl url = nextUrl();
        assertEquals(CATALOG + "markets/search/goal", url.encodedPath());
        assertEquals("2", url.queryParameter("per_page"));
        assertEquals(2, markets.size());
        assertEquals(1, server.getRequestCount());
    }

    @Test
    void searchBookmakers_withLimitUsesPerPage() throws IOException, SportMonksException, InterruptedException {
        enqueue("bookmakers.json");

        api.searchBookmakers("bet", 1);

        HttpUrl url = nextUrl();
        assertEquals(CATALOG + "bookmakers/search/bet", url.encodedPath());
        assertEquals("1", url.queryParameter("per_page"));
    }

    // ---- inplay

    @Test
    void getAllInplayOdds_usesInplay() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getAllInplayOdds(10, INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "inplay", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void inplayByFixture_acceptsIncludesAndFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getInplayOddsByFixture(1L, INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "inplay/fixtures/1", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void inplayByFixtureAndBookmaker_acceptsIncludesAndFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getInplayOddsByFixtureAndBookmaker(1L, 2L, INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "inplay/fixtures/1/bookmakers/2", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void inplayByFixtureAndMarket_acceptsIncludesAndFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getInplayOddsByFixtureAndMarket(1L, 3L, INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "inplay/fixtures/1/markets/3", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void getLastUpdatedInplayOdds_acceptsIncludesAndFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getLastUpdatedInplayOdds(INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "inplay/latest", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    // ---- premium

    @Test
    void getAllPremiumOdds_usesPremium() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getAllPremiumOdds(10, INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "premium", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void getLastUpdatedPremiumPreMatchOdds_usesUpdatedBetweenTimestamps() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getLastUpdatedPremiumPreMatchOdds(1767225600L, 1767225900L, INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "premium/updated/between/1767225600/1767225900", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void getLastUpdatedPremiumPreMatchOdds_worksWithoutIncludes() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getLastUpdatedPremiumPreMatchOdds(10L, 20L);

        assertEquals(ODDS + "premium/updated/between/10/20", nextUrl().encodedPath());
    }

    @Test
    void getPremiumOddsHistory_usesHistory() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumOddsHistory(10, INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "premium/history", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void getLastUpdatedPremiumOddsHistory_usesHistoryUpdatedBetween() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getLastUpdatedPremiumOddsHistory(10L, 20L);

        assertEquals(ODDS + "premium/history/updated/between/10/20", nextUrl().encodedPath());
    }

    @Test
    void premiumByFixture_acceptsIncludesAndFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumPreMatchOddsByFixture(1L, INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "premium/fixtures/1", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void premiumByFixtureAndBookmaker_acceptsIncludesAndFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumPreMatchOddsByFixtureAndBookmaker(1L, 2L, INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "premium/fixtures/1/bookmakers/2", url.encodedPath());
        assertIncludesAndFilters(url);
    }

    @Test
    void premiumByFixtureAndMarket_acceptsIncludesAndFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumPreMatchOddsByFixtureAndMarket(1L, 3L, INCLUDES, FILTERS);

        HttpUrl url = nextUrl();
        assertEquals(ODDS + "premium/fixtures/1/markets/3", url.encodedPath());
        assertIncludesAndFilters(url);
    }
}
