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
        assertEquals("/schedules/seasons/19686", request.getRequestUrl().encodedPath());
    }

    @Test
    void getSchedulesBySeason_parsesTheStage() throws IOException, SportMonksException {
        enqueue("schedules.json");

        Schedule stage = api.getSchedulesBySeason(27965L).get(0);

        assertEquals(77482690L, stage.getId());
        assertEquals(1L, stage.getSportId());
        assertEquals(564L, stage.getLeagueId());
        assertEquals(27965L, stage.getSeasonId());
        assertEquals(223L, stage.getTypeId());
        assertEquals("Regular Season", stage.getName());
        assertEquals(1L, stage.getSortOrder());
        assertFalse(stage.getFinished());
        assertTrue(stage.getIsCurrent());
        assertEquals("2026-08-15", stage.getStartingAt());
        assertEquals("2027-05-30", stage.getEndingAt());
        assertTrue(stage.getGamesInCurrentWeek());
        assertNull(stage.getTieBreakerRuleId());
        assertTrue(stage.getAggregates().isEmpty());
    }

    @Test
    void getSchedulesBySeason_roundsAreParsed() throws IOException, SportMonksException {
        enqueue("schedules.json");

        List<Schedule> schedules = api.getSchedulesBySeason(27965L);

        assertEquals(1, schedules.get(0).getRounds().size());
        assertEquals(408959L, schedules.get(0).getRounds().get(0).getId());
        assertEquals(77482690L, schedules.get(0).getRounds().get(0).getStageId());
        assertEquals(1, schedules.get(0).getRounds().get(0).getFixtures().size());
    }

    @Test
    void getSchedulesByTeam_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("schedules.json");

        api.getSchedulesByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertEquals("/schedules/teams/1", request.getRequestUrl().encodedPath());
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
        assertEquals("/schedules/seasons/19686/teams/1", request.getRequestUrl().encodedPath());
    }

    @Test
    void getSchedulesBySeasonAndTeam_returnsParsedList() throws IOException, SportMonksException {
        enqueue("schedules.json");

        List<Schedule> schedules = api.getSchedulesBySeasonAndTeam(19686L, 1L);

        assertEquals(77482690L, schedules.get(0).getId());
    }
}
