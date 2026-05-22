package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.schedule.Schedule;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleApiTest extends BaseApiTest {

    @Test
    void getSchedulesBySeason_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("schedules.json");

        api.getSchedulesBySeason(19686L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("schedules/seasons/19686"));
        assertFalse(request.getPath().contains("/teams/"));
    }

    @Test
    void getSchedulesBySeason_returnsParsedList() throws IOException, SportMonksException {
        enqueue("schedules.json");

        List<Schedule> schedules = api.getSchedulesBySeason(19686L);

        assertEquals(1, schedules.size());
        assertEquals(19686L, schedules.get(0).getSeasonId());
        assertEquals(1L, schedules.get(0).getTeamId());
    }

    @Test
    void getSchedulesBySeason_roundsAreParsed() throws IOException, SportMonksException {
        enqueue("schedules.json");

        List<Schedule> schedules = api.getSchedulesBySeason(19686L);

        assertNotNull(schedules.get(0).getRounds());
        assertEquals(1, schedules.get(0).getRounds().size());
        assertEquals(88L, schedules.get(0).getRounds().get(0).getId());
        assertEquals("1", schedules.get(0).getRounds().get(0).getName());
    }

    @Test
    void getSchedulesByTeam_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("schedules.json");

        api.getSchedulesByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("schedules/teams/1"));
    }

    @Test
    void getSchedulesByTeam_returnsParsedList() throws IOException, SportMonksException {
        enqueue("schedules.json");

        List<Schedule> schedules = api.getSchedulesByTeam(1L);

        assertFalse(schedules.isEmpty());
    }

    @Test
    void getSchedulesBySeasonAndTeam_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("schedules.json");

        api.getSchedulesBySeasonAndTeam(19686L, 1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("schedules/seasons/19686/teams/1"));
    }

    @Test
    void getSchedulesBySeasonAndTeam_returnsParsedList() throws IOException, SportMonksException {
        enqueue("schedules.json");

        List<Schedule> schedules = api.getSchedulesBySeasonAndTeam(19686L, 1L);

        assertFalse(schedules.isEmpty());
        assertEquals(1L, schedules.get(0).getTeamId());
    }
}
