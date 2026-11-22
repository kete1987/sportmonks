package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.expectedgoal.ExpectedGoal;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpectedGoalApiTest extends BaseApiTest {

    @Test
    void getExpectedGoalsByFixture_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("expected_goals.json");

        api.getExpectedGoalsByFixture();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("expected/fixtures"));
    }

    @Test
    void getExpectedGoalsByFixture_returnsParsedList() throws IOException, SportMonksException {
        enqueue("expected_goals.json");

        List<ExpectedGoal> goals = api.getExpectedGoalsByFixture();

        assertEquals(1, goals.size());
        assertEquals(3001L, goals.get(0).getId());
        assertEquals(100L, goals.get(0).getFixtureId());
        assertEquals(1L, goals.get(0).getParticipantId());
        assertEquals("team", goals.get(0).getParticipantType());
        assertEquals(58.2, goals.get(0).getPossession());
        assertEquals(1.73, goals.get(0).getGoalsExpected());
    }

    @Test
    void getExpectedGoalsByLineup_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("expected_goals.json");

        api.getExpectedGoalsByLineup();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("expected/lineups"));
    }

    @Test
    void getExpectedGoalsByLineup_returnsParsedList() throws IOException, SportMonksException {
        enqueue("expected_goals.json");

        List<ExpectedGoal> goals = api.getExpectedGoalsByLineup();

        assertFalse(goals.isEmpty());
    }
}
