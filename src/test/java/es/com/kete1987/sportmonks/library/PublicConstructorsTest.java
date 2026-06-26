package es.com.kete1987.sportmonks.library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Smoke tests for the public (apiToken-based) constructors of each API class.
 * These constructors build an OkHttpClient and wire it to the production base URL;
 * they do not make any network calls during construction, so no mock server is needed.
 */
class PublicConstructorsTest {

    @Test
    void footballApi_constructsWithToken() {
        assertDoesNotThrow(() -> new FootballApi("dummy-token"));
    }

    @Test
    void footballApi_constructsWithTokenAndLocale() {
        assertDoesNotThrow(() -> new FootballApi("dummy-token", "es"));
    }

    @Test
    void oddsApi_constructsWithToken() {
        assertDoesNotThrow(() -> new OddsApi("dummy-token"));
    }

    @Test
    void oddsApi_constructsWithTokenAndLocale() {
        assertDoesNotThrow(() -> new OddsApi("dummy-token", "es"));
    }

    @Test
    void predictionsApi_constructsWithToken() {
        assertDoesNotThrow(() -> new PredictionsApi("dummy-token"));
    }

    @Test
    void predictionsApi_constructsWithTokenAndLocale() {
        assertDoesNotThrow(() -> new PredictionsApi("dummy-token", "es"));
    }

    @Test
    void coreApi_constructsWithToken() {
        assertDoesNotThrow(() -> new CoreApi("dummy-token"));
    }

    @Test
    void coreApi_constructsWithTokenAndLocale() {
        assertDoesNotThrow(() -> new CoreApi("dummy-token", "es"));
    }

    @Test
    void sportMonksApi_constructsWithToken() {
        assertDoesNotThrow(() -> new SportMonksAPI("dummy-token"));
    }

    @Test
    void sportMonksApi_constructsWithTokenAndLocale() {
        assertDoesNotThrow(() -> new SportMonksAPI("dummy-token", "es"));
    }

    @Test
    void apis_constructWithTokenLocaleAndTimezone() {
        assertDoesNotThrow(() -> new FootballApi("dummy-token", "es", "Europe/Amsterdam"));
        assertDoesNotThrow(() -> new OddsApi("dummy-token", "es", "Europe/Amsterdam"));
        assertDoesNotThrow(() -> new PredictionsApi("dummy-token", "es", "Europe/Amsterdam"));
        assertDoesNotThrow(() -> new CoreApi("dummy-token", "es", "Europe/Amsterdam"));
        assertDoesNotThrow(() -> new SportMonksAPI("dummy-token", "es", "Europe/Amsterdam"));
    }
}
