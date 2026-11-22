package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.Market;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Routes of {@link OddsApi} that never worked against the real API (#78). */
class OddsRoutesApiTest extends BaseApiTest {

    private static final String ODDS = "/v3/football/odds/";

    private String pathOfLastRequest() throws InterruptedException {
        RecordedRequest request = server.takeRequest();
        return request.getRequestUrl().encodedPath();
    }

    @Test
    void getLastUpdatedInplayOdds_usesInplayLatest() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getLastUpdatedInplayOdds();

        assertEquals(ODDS + "inplay/latest", pathOfLastRequest());
    }

    @Test
    void getPremiumPreMatchOddsByFixture_usesPremiumFixtures() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumPreMatchOddsByFixture(1L);

        assertEquals(ODDS + "premium/fixtures/1", pathOfLastRequest());
    }

    @Test
    void getPremiumPreMatchOddsByFixtureAndBookmaker_usesPremiumFixtures() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumPreMatchOddsByFixtureAndBookmaker(1L, 2L);

        assertEquals(ODDS + "premium/fixtures/1/bookmakers/2", pathOfLastRequest());
    }

    @Test
    void getPremiumPreMatchOddsByFixtureAndMarket_usesPremiumFixtures() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumPreMatchOddsByFixtureAndMarket(1L, 3L);

        assertEquals(ODDS + "premium/fixtures/1/markets/3", pathOfLastRequest());
    }

    @Test
    void premiumInplayMethods_doNotExist() {
        List<String> premiumInplay = Arrays.stream(OddsApi.class.getMethods())
                .map(Method::getName)
                .filter(name -> name.contains("PremiumInplay"))
                .distinct()
                .collect(Collectors.toList());
        List<String> inFacade = Arrays.stream(SportMonksAPI.class.getMethods())
                .map(Method::getName)
                .filter(name -> name.contains("PremiumInplay"))
                .distinct()
                .collect(Collectors.toList());

        assertTrue(premiumInplay.isEmpty(), "The API has no premium inplay endpoints, found in OddsApi: " + premiumInplay);
        assertTrue(inFacade.isEmpty(), "The API has no premium inplay endpoints, found in SportMonksAPI: " + inFacade);
    }

    @Test
    void searchMarkets_walksEveryPage() throws IOException, SportMonksException {
        enqueue("markets_search_page1.json");
        enqueue("markets_search_page2.json");

        List<Market> markets = api.searchMarkets("goal");

        assertEquals(3, markets.size());
        assertEquals(2, server.getRequestCount());
    }
}
