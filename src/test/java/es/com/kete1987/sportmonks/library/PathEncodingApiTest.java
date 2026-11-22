package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Issue #85: text and ids are path segments, so {@code /}, {@code ?}, {@code #}, spaces and
 * accents must travel percent-encoded inside their own segment instead of changing the route
 * or turning into the query or the fragment.
 */
class PathEncodingApiTest extends BaseApiTest {

    private void enqueueEmpty() {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":[]}")
                .addHeader("Content-Type", "application/json; charset=utf-8"));
    }

    private RecordedRequest take() throws InterruptedException {
        return server.takeRequest(5, TimeUnit.SECONDS);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "AC/DC           | /teams/search/AC%2FDC",
            "Real?x          | /teams/search/Real%3Fx",
            "Inter#1         | /teams/search/Inter%231",
            "Atlético Madrid | /teams/search/Atl%C3%A9tico%20Madrid",
    })
    void searchTeams_encodesTheTextAsASingleSegment(String name, String expectedPath)
            throws IOException, SportMonksException, InterruptedException {
        enqueueEmpty();

        api.searchTeams(name);

        RecordedRequest request = take();
        assertEquals(expectedPath, request.getRequestUrl().encodedPath());
        assertNull(request.getRequestUrl().queryParameter("x"));
    }

    @Test
    void searchPlayers_keepsTheTextAfterTheQuestionMarkInThePath() throws Exception {
        enqueueEmpty();

        api.searchPlayers("Real?x");

        assertEquals("/players/search/Real%3Fx", take().getRequestUrl().encodedPath());
    }

    @Test
    void searchCities_encodesSlashInTheCoreApi() throws Exception {
        enqueueEmpty();

        api.searchCities("Rio/Janeiro");

        assertEquals("/cities/search/Rio%2FJaneiro", take().getRequestUrl().encodedPath());
    }

    @Test
    void searchMarkets_encodesHashInTheOddsCatalogue() throws Exception {
        enqueueEmpty();

        api.searchMarkets("1#2");

        assertEquals("/v3/odds/markets/search/1%232", take().getRequestUrl().encodedPath());
    }

    @Test
    void getMatchOdds_encodesAStringFixtureId() throws Exception {
        enqueueEmpty();

        api.getMatchOdds("1/2");

        assertEquals("/v3/football/odds/pre-match/fixtures/1%2F2", take().getRequestUrl().encodedPath());
    }

    @Test
    void getTopScores_encodesAStringSeasonId() throws Exception {
        enqueueEmpty();

        api.getTopScores("19734?x", 1, "player");

        assertEquals("/topscorers/seasons/19734%3Fx", take().getRequestUrl().encodedPath());
    }

    @Test
    void getMatchesByDateRangeForTeam_encodesEverySegment() throws Exception {
        enqueueEmpty();

        api.getMatchesByDateRangeForTeam("2024-01-01", "2024-01-31", "8 9");

        assertEquals("/fixtures/between/2024-01-01/2024-01-31/8%209", take().getRequestUrl().encodedPath());
    }

    @Test
    void fixedRoutesStillMatchWhenSegmentsArePlain() throws Exception {
        enqueueEmpty();

        api.getPremiumPreMatchOddsByFixtureAndMarket(19L, 1L);

        assertEquals("/v3/football/odds/premium/fixtures/19/markets/1", take().getRequestUrl().encodedPath());
    }
}
