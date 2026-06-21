package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.topscorers.TopScoresPlayer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopScorerApiTest extends BaseApiTest {

    @Test
    void getTopScores_noLimit_fetchesAllPages() throws IOException, SportMonksException {
        enqueue("topscorers_page1.json");
        enqueue("topscorers_page2.json");

        List<TopScoresPlayer> result = api.getTopScores("100");

        assertEquals(4, result.size());
    }

    @Test
    void getTopScores_withLimit_stopsEarlyAndSetsPerPage() throws IOException, SportMonksException, InterruptedException {
        // Only the first page is enqueued: if the limit didn't stop pagination the
        // mock would have no second response and the call would block.
        enqueue("topscorers_page1.json");

        List<TopScoresPlayer> result = api.getTopScores("100", 2, "player");

        assertEquals(2, result.size());
        assertEquals(11L, result.get(0).getPlayerId());

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("per_page=2"), request.getPath());
        assertTrue(request.getPath().contains("topscorers/seasons/100"));
    }

    @Test
    void getTopScores_withLimit_trimsOverflowAcrossPages() throws IOException, SportMonksException {
        // limit 3: page1 (2) is not enough, so page2 is fetched (total 4) and trimmed to 3.
        enqueue("topscorers_page1.json");
        enqueue("topscorers_page2.json");

        List<TopScoresPlayer> result = api.getTopScores("100", 3, "player");

        assertEquals(3, result.size());
        assertEquals(13L, result.get(2).getPlayerId());
    }

    @Test
    void getTopScores_nonPositiveLimit_meansNoCap() throws IOException, SportMonksException {
        enqueue("topscorers_page1.json");
        enqueue("topscorers_page2.json");

        List<TopScoresPlayer> result = api.getTopScores("100", 0, "player");

        assertEquals(4, result.size());
    }

    @Test
    void getTopScoresByStage_withLimit_stopsEarly() throws IOException, SportMonksException, InterruptedException {
        enqueue("topscorers_page1.json");

        List<TopScoresPlayer> result = api.getTopScoresByStage("55", 2, "player");

        assertEquals(2, result.size());

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("topscorers/stages/55"));
        assertTrue(request.getPath().contains("per_page=2"), request.getPath());
    }

    @Test
    void getTopScoresFiltered_withLimit_keepsFilterAndLimits() throws IOException, SportMonksException, InterruptedException {
        enqueue("topscorers_page1.json");

        List<TopScoresPlayer> result = api.getTopScoresFiltered("100", 208, 2, "player");

        assertEquals(2, result.size());

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("filters=seasontopscorerTypes%3A208")
                || request.getPath().contains("filters=seasontopscorerTypes:208"), request.getPath());
        assertTrue(request.getPath().contains("per_page=2"), request.getPath());
    }
}
