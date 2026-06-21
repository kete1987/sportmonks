package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Covers the optional {@code limit} added to the paginated list/search endpoints
 * (Tier 1 + searches). They all share the generic {@code fetchPaged} helper, so a
 * representative subset exercises the early-stop / per_page / trim behaviour.
 */
class PaginationLimitApiTest extends BaseApiTest {

    // --- early stop: only page 1 is enqueued; if it kept paginating the mock would block ---

    @Test
    void getAllTransfers_withLimit_stopsEarlyAndSetsPerPage() throws IOException, SportMonksException, InterruptedException {
        enqueue("paged_list_page1.json");

        assertEquals(2, api.getAllTransfers(2).size());

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfers"));
        assertTrue(request.getPath().contains("per_page=2"), request.getPath());
    }

    @Test
    void getAllTeamRankings_withLimit_stopsEarly() throws IOException, SportMonksException, InterruptedException {
        enqueue("paged_list_page1.json");

        assertEquals(2, api.getAllTeamRankings(2).size());

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("team-rankings"));
        assertTrue(request.getPath().contains("per_page=2"), request.getPath());
    }

    @Test
    void getAllPlayers_withLimit_stopsEarly() throws IOException, SportMonksException, InterruptedException {
        enqueue("paged_list_page1.json");

        assertEquals(2, api.getAllPlayers(2).size());

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("players"));
        assertTrue(request.getPath().contains("per_page=2"), request.getPath());
    }

    @Test
    void searchLeagues_withLimit_stopsEarly() throws IOException, SportMonksException, InterruptedException {
        enqueue("paged_list_page1.json");

        assertEquals(2, api.searchLeagues("liga", 2).size());

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("leagues/search/liga"));
        assertTrue(request.getPath().contains("per_page=2"), request.getPath());
    }

    @Test
    void searchPlayers_withLimit_stopsEarly() throws IOException, SportMonksException, InterruptedException {
        enqueue("paged_list_page1.json");

        assertEquals(2, api.searchPlayers("messi", 2).size());

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("players/search/messi"));
        assertTrue(request.getPath().contains("per_page=2"), request.getPath());
    }

    // --- trim across pages ---

    @Test
    void getAllMatchFacts_withLimit_trimsOverflowAcrossPages() throws IOException, SportMonksException {
        enqueue("paged_list_page1.json");
        enqueue("paged_list_page2.json");

        assertEquals(3, api.getAllMatchFacts(3).size());
    }

    @Test
    void getAllTeams_withLimit_trimsOverflowAcrossPages() throws IOException, SportMonksException {
        enqueue("paged_list_page1.json");
        enqueue("paged_list_page2.json");

        assertEquals(3, api.getAllTeams(3).size());
    }

    // --- no limit keeps fetching every page (original behaviour preserved) ---

    @Test
    void getAllTransfers_noLimit_fetchesAllPages() throws IOException, SportMonksException {
        enqueue("paged_list_page1.json");
        enqueue("paged_list_page2.json");

        assertEquals(4, api.getAllTransfers().size());
    }

    @Test
    void getAllTransferRumours_noLimit_fetchesAllPages() throws IOException, SportMonksException {
        enqueue("paged_list_page1.json");
        enqueue("paged_list_page2.json");

        assertEquals(4, api.getAllTransferRumours().size());
    }

    // --- searchFixtures uses the same helper via fetchMatchList ---

    @Test
    void searchFixtures_withLimit_stopsEarly() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_page1.json"); // 1 item, has_more true

        assertEquals(1, api.searchFixtures("clasico", 1).size());

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("fixtures/search/clasico"));
        assertTrue(request.getPath().contains("per_page=1"), request.getPath());
    }
}
