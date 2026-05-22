package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.statistic.Statistic;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsApiTest extends BaseApiTest {

    @Test
    void getSeasonStatsByParticipant_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("statistics.json");

        api.getSeasonStatsByParticipant(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("statistics/seasons/participants/1"));
    }

    @Test
    void getSeasonStatsByParticipant_returnsParsedList() throws IOException, SportMonksException {
        enqueue("statistics.json");

        List<Statistic> stats = api.getSeasonStatsByParticipant(1L);

        assertEquals(2, stats.size());
        assertEquals(1001L, stats.get(0).getId());
        assertEquals(52L, stats.get(0).getTypeId());
        assertEquals(1L, stats.get(0).getParticipantId());
        assertEquals("App\\Models\\Football\\Team", stats.get(0).getParticipantType());
        assertEquals(19686L, stats.get(0).getSeasonId());
    }

    @Test
    void getStatsByStage_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("statistics.json");

        api.getStatsByStage(77L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("statistics/stages/77"));
    }

    @Test
    void getStatsByStage_returnsParsedList() throws IOException, SportMonksException {
        enqueue("statistics.json");

        List<Statistic> stats = api.getStatsByStage(77L);

        assertEquals(2, stats.size());
        assertEquals(1002L, stats.get(1).getId());
    }

    @Test
    void getStatsByRound_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("statistics.json");

        api.getStatsByRound(88L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("statistics/rounds/88"));
    }

    @Test
    void getStatsByRound_returnsParsedList() throws IOException, SportMonksException {
        enqueue("statistics.json");

        List<Statistic> stats = api.getStatsByRound(88L);

        assertNotNull(stats);
        assertFalse(stats.isEmpty());
    }
}
