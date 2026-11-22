package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.Constants;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.country.Country;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Checks of issues #83 and #85 against the real API: path encoding, error codes, parsing and
 * the page size of full walks. Every call is capped with a small {@code limit} to save quota.
 */
class HttpInfrastructureIntegrationTest {

    private static final AtomicInteger REQUESTS = new AtomicInteger();

    private static SportMonksAPI api;
    /** Same client, but every request is counted in {@link #REQUESTS}. */
    private static SportMonksAPI counted;

    @BeforeAll
    static void setUp() {
        String apiKey = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(apiKey != null && !apiKey.isEmpty(),
                "SPORTMONKS_API_KEY not set - skipping integration tests");
        api = new SportMonksAPI(apiKey);
        OkHttpClient counting = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    REQUESTS.incrementAndGet();
                    return chain.proceed(chain.request());
                })
                .build();
        ClientOptions options = ClientOptions.builder().httpClient(counting).build();
        counted = new SportMonksAPI(SportMonksApiBase.buildHttpClient(apiKey, options),
                new SportMonksAPI.BaseUrls(Constants.BASE_URL_FOOTBALL, Constants.BASE_URL_ODDS,
                        Constants.BASE_URL_ODDS_CATALOG, Constants.BASE_URL_CORE, Constants.BASE_URL_MY),
                null, null);
    }

    /** The 38 leagues of the subscription fit in one page of 50: one request instead of two. */
    @Test
    void fullWalk_leaguesFitInOneRequest() throws IOException, SportMonksException {
        REQUESTS.set(0);

        List<League> leagues = counted.getAllLeagues();

        assertTrue(leagues.size() > 25 && leagues.size() <= 50, "leagues: " + leagues.size());
        assertEquals(1, REQUESTS.get());
    }

    /** A walk across several pages at 50 rows per page, without per_page on cursor requests (400). */
    @Test
    void fullWalk_countriesUseFiftyRowPages() throws IOException, SportMonksException {
        REQUESTS.set(0);

        List<Country> countries = counted.getAllCountries();

        int pages = (countries.size() + 49) / 50;
        assertTrue(pages > 1, "countries: " + countries.size());
        assertEquals(pages, REQUESTS.get(), "countries: " + countries.size());
    }

    private static void assertEveryNameContains(List<Team> teams, String text) {
        for (Team team : teams) {
            assertTrue(team.getName().contains(text),
                    "searched for '" + text + "' but the API matched '" + team.getName() + "'");
        }
    }

    @Test
    void searchTeams_questionMarkIsPartOfTheSearchedText() throws IOException, SportMonksException {
        List<Team> teams = api.searchTeams("Real?x", 5);

        assertEveryNameContains(teams, "Real?x");
    }

    @Test
    void searchTeams_hashIsPartOfTheSearchedText() throws IOException, SportMonksException {
        List<Team> teams = api.searchTeams("Inter#1", 5);

        assertEveryNameContains(teams, "Inter#1");
    }

    /**
     * The API decodes {@code %2F} before routing, so a slash in the searched text cannot be
     * expressed: the route does not exist (404). The library now encodes it and reports the
     * status code instead of an opaque message.
     */
    @Test
    void searchTeams_slashIsRejectedByTheApiWithAStatusCode() {
        SportMonksException e = assertThrows(SportMonksException.class, () -> api.searchTeams("AC/DC", 5));

        assertEquals(404, e.getStatusCode());
    }

    @Test
    void endpointOutsideTheSubscription_exceptionCarriesStatusAndErrorCode() {
        SportMonksException e = assertThrows(SportMonksException.class, () -> api.getMatchFactsByLeague(8L));

        assertEquals(403, e.getStatusCode());
        assertEquals(Integer.valueOf(5007), e.getErrorCode());
        assertTrue(e.getMessage().startsWith("403 - "), e.getMessage());
    }

    @Test
    void searchTeams_accentsAndSpacesStillMatch() throws IOException, SportMonksException {
        // The API search ignores accents, so it also matches "Atletico Madrid W".
        List<Team> teams = api.searchTeams("Atlético Madrid", 2);

        assertFalse(teams.isEmpty(), "the encoded name should still find Atletico Madrid");
        assertEveryNameContains(teams, "Madrid");
    }
}
