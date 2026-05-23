package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamApiTest extends BaseApiTest {

    @Test
    void getAllTeams_usesTeamsPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("teams.json");

        api.getAllTeams();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("teams"));
        assertFalse(request.getPath().contains("teams/"));
    }

    @Test
    void getAllTeams_returnsParsedList() throws IOException, SportMonksException {
        enqueue("teams.json");

        List<Team> teams = api.getAllTeams();

        assertEquals(2, teams.size());
        assertEquals(1L, teams.get(0).getId());
        assertEquals("Manchester United", teams.get(0).getName());
    }

    @Test
    void getTeamsBySeasonId_usesSeasonPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("teams.json");

        api.getTeamsBySeasonId(23614L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("teams/seasons/23614"));
    }

    @Test
    void getTeamsBySeasonId_returnsParsedList() throws IOException, SportMonksException {
        enqueue("teams.json");

        List<Team> teams = api.getTeamsBySeasonId(23614L);

        assertEquals(2, teams.size());
    }

    @Test
    void getTeamsByCountry_usesCountryPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("teams.json");

        api.getTeamsByCountry(462L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("teams/countries/462"));
    }

    @Test
    void getTeamById_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("team_detail.json");

        api.getTeamById(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("teams/1"));
    }

    @Test
    void getTeamById_returnsParsed() throws IOException, SportMonksException {
        enqueue("team_detail.json");

        Team team = api.getTeamById(1L);

        assertNotNull(team);
        assertEquals(1L, team.getId());
        assertEquals("Manchester United", team.getName());
    }

    @Test
    void searchTeams_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("teams.json");

        api.searchTeams("United");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("teams/search/United"));
    }

    @Test
    void searchTeams_returnsParsedList() throws IOException, SportMonksException {
        enqueue("teams.json");

        List<Team> teams = api.searchTeams("United");

        assertEquals(2, teams.size());
    }
}
