package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.team.TeamPlayer;
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
    void getTeamById_withPlayersPlayerInclude_deserializesNestedPlayer() throws IOException, SportMonksException, InterruptedException {
        enqueue("team_detail_squad.json");

        Team team = api.getTeamById(1L, "players.player");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("include=players.player"));

        List<TeamPlayer> players = team.getPlayers();
        assertEquals(2, players.size());

        TeamPlayer keeper = players.get(0);
        // Squad pivot fields still present.
        assertEquals(37605747L, keeper.getPlayerId());
        assertEquals(1L, keeper.getJerseyNumber());
        assertEquals(24L, keeper.getPositionId());
        // Nested player resolved from players.player.
        assertNotNull(keeper.getPlayer());
        assertEquals("André Onana", keeper.getPlayer().getName());
        assertEquals("André Onana", keeper.getPlayer().getDisplayName());
        assertEquals("https://cdn.sportmonks.com/images/soccer/players/19/37605747.png",
                keeper.getPlayer().getImagePath());
    }

    @Test
    void getTeamById_withoutPlayersInclude_nestedPlayerIsNull() throws IOException, SportMonksException {
        enqueue("team_detail.json");

        Team team = api.getTeamById(1L);

        assertNull(team.getPlayers());
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
