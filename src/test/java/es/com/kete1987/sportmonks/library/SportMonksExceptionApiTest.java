package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Issue #83: a non-2xx response must expose its HTTP status, the Sportmonks error code and the
 * {@code rate_limit} of the body, while {@code getMessage()} keeps the {@code "code - body"}
 * format that consumers parse.
 */
class SportMonksExceptionApiTest extends BaseApiTest {

    private static final String NOT_FOUND = "{\"message\":\"The requested endpoint does not exist\"}";
    private static final String FORBIDDEN = "{\"message\":\"You do not have access to this endpoint\","
            + "\"link\":\"https://docs.sportmonks.com/football/api/response-codes/other-exceptions\",\"code\":5007}";
    private static final String TOO_MANY = "{\"message\":\"Too Many Attempts.\",\"code\":5008,"
            + "\"rate_limit\":{\"resets_in_seconds\":1200,\"remaining\":0,\"requested_entity\":\"Team\"}}";

    private void enqueueBody(int code, String body) {
        server.enqueue(new MockResponse().setResponseCode(code).setBody(body)
                .addHeader("Content-Type", "application/json"));
    }

    @Test
    void notFound_exposesStatusCodeAndKeepsMessageFormat() {
        enqueueBody(404, NOT_FOUND);

        SportMonksException e = assertThrows(SportMonksException.class, () -> api.getTeamById(1L));

        assertEquals(404, e.getStatusCode());
        assertEquals("404 - " + NOT_FOUND, e.getMessage());
        assertNull(e.getErrorCode());
        assertNull(e.getRateLimit());
    }

    @Test
    void forbidden_exposesTheSportmonksErrorCode() {
        enqueueBody(403, FORBIDDEN);

        SportMonksException e = assertThrows(SportMonksException.class, () -> api.getTeamById(1L));

        assertEquals(403, e.getStatusCode());
        assertEquals(Integer.valueOf(5007), e.getErrorCode());
    }

    @Test
    void tooManyRequests_exposesTheRateLimitOfTheBody() {
        enqueueBody(429, TOO_MANY);

        SportMonksException e = assertThrows(SportMonksException.class, () -> api.getTeamById(1L));

        assertEquals(429, e.getStatusCode());
        assertEquals(Long.valueOf(1200), e.getRateLimit().getResetsInSeconds());
        assertEquals("Team", e.getRateLimit().getRequestedEntity());
    }

    @Test
    void nonJsonErrorBody_stillCarriesTheStatusCode() {
        enqueueBody(502, "<html>Bad Gateway</html>");

        SportMonksException e = assertThrows(SportMonksException.class, () -> api.getTeamById(1L));

        assertEquals(502, e.getStatusCode());
        assertEquals("502 - <html>Bad Gateway</html>", e.getMessage());
        assertNull(e.getErrorCode());
    }
}
