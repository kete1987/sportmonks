package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Responses without {@code data} (an empty body, or the API's "No result(s) found" message)
 * must yield an empty list for list endpoints and {@code null} for single resources, never a
 * {@link NullPointerException}.
 */
class FootballNullSafetyApiTest extends BaseApiTest {

    private static final String NO_RESULTS = "{\"message\":\"No result(s) found matching your request.\"}";

    private void enqueueBody(String body) {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(body));
    }

    @Test
    void getLiveMatches_emptyBody_returnsEmptyList() throws IOException, SportMonksException {
        enqueueBody("");
        assertTrue(api.getLiveMatches().isEmpty());
    }

    @Test
    void getLiveMatches_noData_returnsEmptyList() throws IOException, SportMonksException {
        enqueueBody(NO_RESULTS);
        assertTrue(api.getLiveMatches().isEmpty());
    }

    @Test
    void getLatestUpdatedLivescores_emptyBody_returnsEmptyList() throws IOException, SportMonksException {
        enqueueBody("");
        assertTrue(api.getLatestUpdatedLivescores().isEmpty());
    }

    @Test
    void getLatestUpdatedLivescores_noData_returnsEmptyList() throws IOException, SportMonksException {
        enqueueBody(NO_RESULTS);
        assertTrue(api.getLatestUpdatedLivescores().isEmpty());
    }

    @Test
    void getLatestUpdatedFixtures_emptyBody_returnsEmptyList() throws IOException, SportMonksException {
        enqueueBody("");
        assertTrue(api.getLatestUpdatedFixtures().isEmpty());
    }

    @Test
    void getLatestUpdatedFixtures_noData_returnsEmptyList() throws IOException, SportMonksException {
        enqueueBody(NO_RESULTS);
        assertTrue(api.getLatestUpdatedFixtures().isEmpty());
    }

    @Test
    void getAllStates_emptyBody_returnsEmptyList() throws IOException, SportMonksException {
        enqueueBody("");
        assertTrue(api.getAllStates().isEmpty());
    }

    @Test
    void getAllStates_noData_returnsEmptyList() throws IOException, SportMonksException {
        enqueueBody(NO_RESULTS);
        assertTrue(api.getAllStates().isEmpty());
    }

    @Test
    void getStateById_emptyBody_returnsNull() throws IOException, SportMonksException {
        enqueueBody("");
        assertNull(api.getStateById(1L));
    }

    @Test
    void getMatchDetail_emptyBody_returnsNull() throws IOException, SportMonksException {
        enqueueBody("");
        assertNull(api.getMatchDetail("1", "state"));
    }

    @Test
    void getMatchDetail_noData_returnsNull() throws IOException, SportMonksException {
        enqueueBody(NO_RESULTS);
        assertNull(api.getMatchDetail("1", "state"));
    }
}
