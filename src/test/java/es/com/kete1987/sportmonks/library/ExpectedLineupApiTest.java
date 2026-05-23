package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.expectedlineup.ExpectedLineup;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpectedLineupApiTest extends BaseApiTest {

    @Test
    void getExpectedLineupByTeam_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("expected_lineups.json");

        api.getExpectedLineupByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("expected-lineups/teams/1"));
    }

    @Test
    void getExpectedLineupByTeam_returnsParsedList() throws IOException, SportMonksException {
        enqueue("expected_lineups.json");

        List<ExpectedLineup> lineups = api.getExpectedLineupByTeam(1L);

        assertEquals(1, lineups.size());
        assertEquals(11001L, lineups.get(0).getId());
        assertEquals(1L, lineups.get(0).getTeamId());
        assertEquals("4-3-3", lineups.get(0).getFormation());
    }

    @Test
    void getExpectedLineupsByPlayer_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("expected_lineups.json");

        api.getExpectedLineupsByPlayer(101L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("expected-lineups/players/101"));
    }

    @Test
    void getExpectedLineupsByPlayer_returnsParsedList() throws IOException, SportMonksException {
        enqueue("expected_lineups.json");

        List<ExpectedLineup> lineups = api.getExpectedLineupsByPlayer(101L);

        assertFalse(lineups.isEmpty());
    }
}
