package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Issue #83: a full walk (no {@code limit}) asks for the largest page the API accepts
 * ({@code per_page=50}) on its first request, halving the requests of the default 25. Cursor
 * requests never carry {@code per_page} (the API answers 400; the size travels inside the
 * cursor), and offset requests keep it so the pages stay aligned.
 */
class FullWalkPageSizeApiTest extends BaseApiTest {

    private void enqueuePage(long id, boolean hasMore, String nextPage, String nextCursor) {
        String body = "{\"data\":[{\"id\":" + id + "}],\"pagination\":{\"count\":1,\"per_page\":50,"
                + "\"current_page\":" + id + ",\"has_more\":" + hasMore
                + ",\"next_page\":" + quoted(nextPage) + ",\"next_cursor\":" + quoted(nextCursor) + "}}";
        server.enqueue(new MockResponse().setResponseCode(200).setBody(body)
                .addHeader("Content-Type", "application/json"));
    }

    private static String quoted(String value) {
        return value == null ? "null" : "\"" + value + "\"";
    }

    private HttpUrl takeUrl() throws InterruptedException {
        return server.takeRequest().getRequestUrl();
    }

    @Test
    void fullWalk_firstRequestAsksForFiftyRows() throws IOException, SportMonksException, InterruptedException {
        enqueuePage(1, false, null, null);

        api.getAllVenues();

        assertEquals("50", takeUrl().queryParameter("per_page"));
    }

    @Test
    void fullWalkByCursor_dropsPerPageAfterTheFirstRequest() throws IOException, SportMonksException, InterruptedException {
        enqueuePage(1, true, null, "https://api.sportmonks.com/v3/football/venues?cursor=abc");
        enqueuePage(2, false, null, null);

        api.getAllVenues();

        assertEquals("50", takeUrl().queryParameter("per_page"));
        HttpUrl second = takeUrl();
        assertEquals("abc", second.queryParameter("cursor"));
        assertNull(second.queryParameter("per_page"));
    }

    @Test
    void fullWalkByOffset_keepsThePageSizeAligned() throws IOException, SportMonksException, InterruptedException {
        enqueuePage(1, true, "https://api.sportmonks.com/v3/football/venues?page=2", null);
        enqueuePage(2, false, null, null);

        api.getAllVenues();

        takeUrl();
        HttpUrl second = takeUrl();
        assertEquals("2", second.queryParameter("page"));
        assertEquals("50", second.queryParameter("per_page"));
    }
}
