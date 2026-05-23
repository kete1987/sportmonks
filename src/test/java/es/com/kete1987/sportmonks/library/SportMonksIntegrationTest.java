package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.continent.Continent;
import es.com.kete1987.sportmonks.library.core.model.country.Country;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.standings.Standings;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SportMonksIntegrationTest {

    private static SportMonksAPI api;
    private static String seasonId;

    @BeforeAll
    static void setUp() {
        String apiKey = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(apiKey != null && !apiKey.isEmpty(),
                "SPORTMONKS_API_KEY not set — skipping integration tests");
        api = new SportMonksAPI(apiKey);
        seasonId = System.getenv("SPORTMONKS_SEASON_ID");
    }

    @Test
    void getAllContinents_returnsNonEmptyList() throws IOException, SportMonksException {
        List<Continent> result = api.getAllContinents();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    @Test
    void getAllCountries_returnsNonEmptyList() throws IOException, SportMonksException {
        List<Country> result = api.getAllCountries();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    @Test
    void getAllLeagues_returnsNonEmptyList() throws IOException, SportMonksException {
        List<League> result = api.getAllLeagues();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    @Test
    void getTodayMatches_returnsNonNullList() throws IOException, SportMonksException {
        List<MatchDetail> result = api.getTodayMatches();
        assertNotNull(result);
        // List may be empty if no matches today — that is a valid response
    }

    @Test
    void getStandings_returnsNonNullList() throws IOException, SportMonksException {
        Assumptions.assumeTrue(seasonId != null && !seasonId.isEmpty(),
                "SPORTMONKS_SEASON_ID not set — skipping standings test");
        List<Standings> result = api.getStandings(seasonId);
        assertNotNull(result);
    }

    @Test
    void rateLimitHeaders_arePopulatedAfterRequest() throws IOException, SportMonksException {
        api.getAllContinents();
        assertNotNull(api.getRemainingRequests());
        assertNotNull(api.getMaximumRequests());
    }
}
