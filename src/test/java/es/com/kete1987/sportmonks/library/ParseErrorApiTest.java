package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Issue #83: a body that cannot be parsed must surface as a {@link SportMonksException} (with
 * the status code and the URL) instead of an unchecked {@code JsonSyntaxException} that escapes
 * {@code catch (IOException | SportMonksException e)}.
 */
class ParseErrorApiTest extends BaseApiTest {

    private static final String PAGE_ONE = "{\"data\":[{\"id\":1,\"name\":\"Old Trafford\"}],"
            + "\"pagination\":{\"count\":1,\"per_page\":1,\"current_page\":1,\"has_more\":true,"
            + "\"next_page\":\"https://api.sportmonks.com/v3/football/venues?page=2\"}}";

    private void enqueueBody(String body) {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(body)
                .addHeader("Content-Type", "application/json"));
    }

    @Test
    void malformedJson_isWrappedInSportMonksException() {
        enqueueBody("{\"data\": {\"id\": 1,");

        SportMonksException e = assertThrows(SportMonksException.class, () -> api.getTeamById(1L));

        assertEquals(200, e.getStatusCode());
        assertTrue(e.getMessage().startsWith("200 - "), e.getMessage());
        assertTrue(e.getMessage().contains("/teams/1"), e.getMessage());
        assertNotNull(e.getCause());
    }

    @Test
    void wrongTypeInAField_isWrappedInSportMonksException() {
        enqueueBody("{\"data\":{\"id\":\"not-a-number\"}}");

        SportMonksException e = assertThrows(SportMonksException.class, () -> api.getTeamById(1L));

        assertEquals(200, e.getStatusCode());
    }

    @Test
    void unparseablePageInAWalk_isWrappedInSportMonksException() {
        enqueueBody(PAGE_ONE);
        enqueueBody("<html>gateway hiccup</html>");

        SportMonksException e = assertThrows(SportMonksException.class, () -> api.getAllVenues());

        assertTrue(e.getMessage().contains("page=2"), e.getMessage());
    }
}
