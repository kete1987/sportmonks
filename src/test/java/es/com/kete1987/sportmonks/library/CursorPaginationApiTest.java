package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the cursor-first auto-pagination of {@code fetchPaged}: it follows {@code next_cursor}
 * when the API returns one, falls back to {@code &page=N} when it does not, and stops short of the
 * 20.000-row depth limit the API enforces on offset pagination instead of letting it reject the
 * request. Every list endpoint shares the helper, so a representative subset covers all of them.
 */
class CursorPaginationApiTest extends BaseApiTest {

    private static final String CURSOR_PAGE_2 = "Y1VSU09SLVBBR0UtMg";
    private static final String CURSOR_PAGE_3 = "Y1VSU09SLVBBR0UtMw";

    // --- cursor ---

    @Test
    void cursorPagination_chainsPagesUntilHasMoreIsFalse() throws IOException, SportMonksException, InterruptedException {
        enqueue("cursor_page1.json");
        enqueue("cursor_page2.json");
        enqueue("cursor_page3.json");

        assertEquals(5, api.getAllTransfers().size());
        assertEquals(3, server.getRequestCount());

        assertFalse(server.takeRequest().getPath().contains("cursor="));
        assertTrue(server.takeRequest().getPath().contains("cursor=" + CURSOR_PAGE_2));
        assertTrue(server.takeRequest().getPath().contains("cursor=" + CURSOR_PAGE_3));
    }

    /**
     * The API returns {@code next_cursor} as a full URL stripped of the API token and of the
     * original query — following it verbatim would fail auth and lose the includes, so only the
     * cursor value is carried over onto the URL the library built.
     */
    @Test
    void cursorPagination_appliesCursorToTheOriginalUrl() throws IOException, SportMonksException, InterruptedException {
        enqueue("cursor_page1.json");
        enqueue("cursor_page3.json");

        api.getAllContinents("countries");

        server.takeRequest();
        RecordedRequest second = server.takeRequest();
        assertTrue(second.getPath().startsWith("/continents"), second.getPath());
        assertTrue(second.getPath().contains("include=countries"), second.getPath());
        assertTrue(second.getPath().contains("cursor=" + CURSOR_PAGE_2), second.getPath());
    }

    /** With both fields present the cursor wins, and no {@code page} is sent alongside it. */
    @Test
    void cursorPagination_takesPrecedenceOverNextPage() throws IOException, SportMonksException, InterruptedException {
        enqueue("cursor_page1.json");
        enqueue("cursor_page3.json");

        api.getAllTransfers();

        server.takeRequest();
        assertFalse(server.takeRequest().getPath().contains("page="));
    }

    @Test
    void cursorPagination_withLimit_stopsEarlyAndKeepsPerPage() throws IOException, SportMonksException, InterruptedException {
        enqueue("cursor_page1.json");

        assertEquals(2, api.getAllTransfers(2).size());
        assertEquals(1, server.getRequestCount());
        assertTrue(server.takeRequest().getPath().contains("per_page=2"));
    }

    /** A cursor repeating itself would otherwise loop forever, so the walk stops on the repeat. */
    @Test
    void cursorPagination_stopsWhenTheCursorRepeats() throws IOException, SportMonksException {
        enqueue("cursor_page1.json");
        enqueue("cursor_page1.json");

        assertEquals(4, api.getAllTransfers().size());
        assertEquals(2, server.getRequestCount());
    }

    // --- offset fallback ---

    @Test
    void offsetPagination_isUsedWhenTheApiReturnsNoCursor() throws IOException, SportMonksException, InterruptedException {
        enqueue("paged_list_page1.json");
        enqueue("paged_list_page2.json");

        assertEquals(4, api.getAllTransfers().size());

        server.takeRequest();
        assertTrue(server.takeRequest().getPath().contains("page=2"));
    }

    /**
     * Offset pagination is rejected past 20.000 rows (page 401 with {@code per_page=50}). The walk
     * stops and returns what it gathered instead of firing the request the API would reject — the
     * second response is enqueued as an error precisely to prove it is never requested.
     */
    @Test
    void offsetPagination_stopsBeforeCrossingTheDepthLimit() throws IOException, SportMonksException {
        enqueue("offset_depth_limit.json");
        enqueue("error_401.json", 400);

        assertEquals(2, api.getAllMarkets().size());
        assertEquals(1, server.getRequestCount());
    }

    /** Page 800 with {@code per_page=25} lands exactly on 20.000 rows, so it is still requested. */
    @Test
    void offsetPagination_requestsTheLastPageWithinTheLimit() throws IOException, SportMonksException, InterruptedException {
        enqueue("offset_depth_last_allowed.json");
        enqueue("paged_list_page2.json");

        assertEquals(4, api.getAllMarkets().size());

        server.takeRequest();
        assertTrue(server.takeRequest().getPath().contains("page=800"));
    }
}
