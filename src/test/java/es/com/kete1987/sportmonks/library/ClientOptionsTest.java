package es.com.kete1987.sportmonks.library;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Issue #83: consumers can tune the HTTP client (timeouts, or a whole {@link OkHttpClient} of
 * their own) through {@link ClientOptions}, while the library keeps adding the token header.
 */
class ClientOptionsTest {

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

    private void enqueueTeam() {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"data\":{\"id\":1}}")
                .addHeader("Content-Type", "application/json"));
    }

    @Test
    void readTimeout_isApplied() {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"data\":{\"id\":1}}")
                .setBodyDelay(2, TimeUnit.SECONDS));
        FootballApi football = footballWith(ClientOptions.builder().readTimeout(Duration.ofMillis(200)).build());

        assertThrows(IOException.class, () -> football.getTeamById(1L));
    }

    @Test
    void ownHttpClient_isUsedAndStillCarriesTheToken() throws Exception {
        enqueueTeam();
        OkHttpClient own = new OkHttpClient.Builder()
                .addInterceptor(chain -> chain.proceed(chain.request().newBuilder().header("X-Consumer", "app").build()))
                .build();
        FootballApi football = footballWith(ClientOptions.builder().httpClient(own).build());

        football.getTeamById(1L);

        RecordedRequest request = server.takeRequest(5, TimeUnit.SECONDS);
        assertEquals("app", request.getHeader("X-Consumer"));
        assertEquals("test-token", request.getHeader("Authorization"));
    }

    @Test
    void defaults_keepThirtySecondTimeouts() {
        OkHttpClient client = SportMonksApiBase.buildHttpClient("t", ClientOptions.defaults());

        assertEquals(30_000, client.connectTimeoutMillis());
        assertEquals(30_000, client.readTimeoutMillis());
        assertEquals(0, client.callTimeoutMillis());
    }

    @Test
    void explicitTimeouts_overrideTheDefaults() {
        ClientOptions options = ClientOptions.builder()
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(10))
                .callTimeout(Duration.ofMinutes(2))
                .build();

        OkHttpClient client = SportMonksApiBase.buildHttpClient("t", options);

        assertEquals(5_000, client.connectTimeoutMillis());
        assertEquals(10_000, client.readTimeoutMillis());
        assertEquals(120_000, client.callTimeoutMillis());
    }

    @Test
    void publicConstructors_acceptOptions() {
        ClientOptions options = ClientOptions.builder().callTimeout(Duration.ofSeconds(60)).build();

        assertDoesNotThrow(() -> new SportMonksAPI("dummy-token", "es", null, options));
        assertDoesNotThrow(() -> new FootballApi("dummy-token", null, null, options));
        assertDoesNotThrow(() -> new OddsApi("dummy-token", null, null, options));
        assertDoesNotThrow(() -> new PredictionsApi("dummy-token", null, null, options));
        assertDoesNotThrow(() -> new CoreApi("dummy-token", null, null, options));
    }
}
