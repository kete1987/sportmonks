package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Issue #83: retrying 429 (waiting {@code rate_limit.resets_in_seconds}, capped) and 5xx (with
 * exponential backoff) is opt-in; by default a failed response is reported at once, as before.
 */
class RetryApiTest {

    private static final String TEAM = "{\"data\":{\"id\":1}}";

    private MockWebServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    private FootballApi footballWith(ClientOptions options) {
        OkHttpClient client = SportMonksApiBase.buildHttpClient("test-token", options);
        return new FootballApi(client, server.url("/").toString(), null, null, new RateLimitTracker());
    }

    private static ClientOptions retrying(int maxRetries, Duration maxWait) {
        return ClientOptions.builder()
                .maxRetries(maxRetries)
                .maxRetryWait(maxWait)
                .retryBackoff(Duration.ofMillis(1))
                .build();
    }

    private void enqueue(int code, String body) {
        server.enqueue(new MockResponse().setResponseCode(code).setBody(body)
                .addHeader("Content-Type", "application/json"));
    }

    private static String tooManyRequests(long resetsInSeconds) {
        return "{\"message\":\"Too Many Attempts.\",\"rate_limit\":{\"resets_in_seconds\":" + resetsInSeconds
                + ",\"remaining\":0,\"requested_entity\":\"Team\"}}";
    }

    @Test
    void byDefault_aTooManyRequestsIsNotRetried() {
        enqueue(429, tooManyRequests(0));
        enqueue(200, TEAM);
        FootballApi football = footballWith(ClientOptions.defaults());

        SportMonksException e = assertThrows(SportMonksException.class, () -> football.getTeamById(1L));

        assertEquals(429, e.getStatusCode());
        assertEquals(1, server.getRequestCount());
    }

    @Test
    void tooManyRequests_isRetriedAfterTheReset() throws IOException, SportMonksException {
        enqueue(429, tooManyRequests(0));
        enqueue(200, TEAM);

        assertEquals(Long.valueOf(1), footballWith(retrying(2, Duration.ofSeconds(5))).getTeamById(1L).getId());
        assertEquals(2, server.getRequestCount());
    }

    @Test
    void tooManyRequests_isNotRetriedWhenTheResetExceedsTheCap() {
        enqueue(429, tooManyRequests(3600));
        enqueue(200, TEAM);
        FootballApi football = footballWith(retrying(2, Duration.ofSeconds(5)));

        SportMonksException e = assertThrows(SportMonksException.class, () -> football.getTeamById(1L));

        assertEquals(429, e.getStatusCode());
        assertEquals(1, server.getRequestCount());
    }

    @Test
    void tooManyRequestsWithoutRateLimit_isRetriedWithBackoff() throws IOException, SportMonksException {
        enqueue(429, "<html>slow down</html>");
        enqueue(429, "{\"message\":\"Too Many Attempts.\"}");
        enqueue(200, TEAM);

        assertEquals(Long.valueOf(1), footballWith(retrying(2, Duration.ofSeconds(5))).getTeamById(1L).getId());
        assertEquals(3, server.getRequestCount());
    }

    @Test
    void negativeMaxRetries_isRejected() {
        ClientOptions.Builder builder = ClientOptions.builder();

        assertThrows(IllegalArgumentException.class, () -> builder.maxRetries(-1));
    }

    @Test
    void serverError_isRetriedWithBackoff() throws IOException, SportMonksException {
        enqueue(503, "");
        enqueue(502, "");
        enqueue(200, TEAM);

        assertEquals(Long.valueOf(1), footballWith(retrying(2, Duration.ofSeconds(5))).getTeamById(1L).getId());
        assertEquals(3, server.getRequestCount());
    }

    @Test
    void serverError_givesUpAfterMaxRetries() {
        enqueue(503, "");
        enqueue(503, "");
        enqueue(503, "{\"message\":\"down\"}");
        FootballApi football = footballWith(retrying(2, Duration.ofSeconds(5)));

        SportMonksException e = assertThrows(SportMonksException.class, () -> football.getTeamById(1L));

        assertEquals(503, e.getStatusCode());
        assertEquals(3, server.getRequestCount());
    }

    @Test
    void clientErrors_areNeverRetried() {
        enqueue(404, "{\"message\":\"The requested endpoint does not exist\"}");
        enqueue(200, TEAM);
        FootballApi football = footballWith(retrying(2, Duration.ofSeconds(5)));

        SportMonksException e = assertThrows(SportMonksException.class, () -> football.getTeamById(1L));

        assertEquals(404, e.getStatusCode());
        assertEquals(1, server.getRequestCount());
    }

    @Test
    void retryWait_doesNotOutliveTheCallTimeout() {
        // The reset is 5 s away and fits in the retry cap, but the whole call may only last 1 s:
        // the wait must end when the call times out instead of sleeping the full 5 s.
        enqueue(429, tooManyRequests(5));
        enqueue(200, TEAM);
        FootballApi football = footballWith(ClientOptions.builder()
                .maxRetries(1)
                .maxRetryWait(Duration.ofSeconds(10))
                .callTimeout(Duration.ofSeconds(1))
                .build());
        long start = System.nanoTime();

        assertThrows(IOException.class, () -> football.getTeamById(1L));

        long elapsedMillis = Duration.ofNanos(System.nanoTime() - start).toMillis();
        assertTrue(elapsedMillis < 3000, "the call kept waiting for " + elapsedMillis + " ms");
    }
}
