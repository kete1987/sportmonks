package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.totw.TeamOfTheWeek;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamOfTheWeekApiTest extends BaseApiTest {

    @Test
    void getAllTeamsOfTheWeek_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("totw.json");

        api.getAllTeamsOfTheWeek();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("team-of-the-week"));
        assertFalse(request.getPath().contains("team-of-the-week/"));
    }

    @Test
    void getAllTeamsOfTheWeek_returnsParsedList() throws IOException, SportMonksException {
        enqueue("totw.json");

        List<TeamOfTheWeek> totw = api.getAllTeamsOfTheWeek();

        assertEquals(2, totw.size());
        assertEquals(9001L, totw.get(0).getId());
        assertEquals(88L, totw.get(0).getRoundId());
        assertEquals(501L, totw.get(0).getLeagueId());
    }

    @Test
    void getTeamOfTheWeekByRound_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("totw.json");

        api.getTeamOfTheWeekByRound(88L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("team-of-the-week/rounds/88"));
    }

    @Test
    void getLatestTeamOfTheWeekByLeague_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("totw.json");

        api.getLatestTeamOfTheWeekByLeague(501L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("team-of-the-week/leagues/501/latest"));
    }

    @Test
    void getLatestTeamOfTheWeekByLeague_returnsParsedList() throws IOException, SportMonksException {
        enqueue("totw.json");

        List<TeamOfTheWeek> totw = api.getLatestTeamOfTheWeekByLeague(501L);

        assertFalse(totw.isEmpty());
    }
}
