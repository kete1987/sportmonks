package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Issue #83: the changelog (2026-06-05 and 2026-06-23) documents {@code next_cursor} as a bare
 * token, while the API sends it today as a URL. Both forms must keep the walk on the cursor; a
 * bare token must not silently fall back to offset pagination and its 20.000-row limit.
 */
class BareCursorApiTest extends BaseApiTest {

    private static final String TOKEN = "WzEsMjUsWzIzNDMzNl0sW1sic3BvcnRzLnRlYW1zLmlkIiwwXV1d";

    private void enqueuePage(long id, boolean hasMore, String nextCursor) {
        String cursor = nextCursor == null ? "null" : "\"" + nextCursor + "\"";
        String body = "{\"data\":[{\"id\":" + id + "}],\"pagination\":{\"count\":1,\"per_page\":1,"
                + "\"current_page\":1,\"has_more\":" + hasMore + ",\"next_page\":null,\"next_cursor\":" + cursor + "}}";
        server.enqueue(new MockResponse().setResponseCode(200).setBody(body)
                .addHeader("Content-Type", "application/json"));
    }

    private RecordedRequest secondRequest() throws InterruptedException {
        server.takeRequest();
        return server.takeRequest();
    }

    @Test
    void bareCursorToken_isFollowedAsACursor() throws IOException, SportMonksException, InterruptedException {
        enqueuePage(1, true, TOKEN);
        enqueuePage(2, false, null);

        assertEquals(2, api.getAllTransfers().size());

        RecordedRequest second = secondRequest();
        assertEquals(TOKEN, second.getRequestUrl().queryParameter("cursor"));
        assertNull(second.getRequestUrl().queryParameter("page"));
    }

    @Test
    void cursorUrl_isStillFollowedAsACursor() throws IOException, SportMonksException, InterruptedException {
        enqueuePage(1, true, "https://api.sportmonks.com/v3/football/transfers?cursor=" + TOKEN);
        enqueuePage(2, false, null);

        assertEquals(2, api.getAllTransfers().size());

        assertEquals(TOKEN, secondRequest().getRequestUrl().queryParameter("cursor"));
    }
}
