package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeagueApiTest extends BaseApiTest {

    @Test
    void getAllLeagues_returnsParsedList() throws IOException, SportMonksException {
        enqueue("leagues.json");

        List<League> leagues = api.getAllLeagues();

        assertEquals(2, leagues.size());
        assertEquals(8L, leagues.get(0).getId());
        assertEquals("Premier League", leagues.get(0).getName());
        assertEquals(271L, leagues.get(1).getId());
        assertEquals("Superliga", leagues.get(1).getName());
    }

    @Test
    void getAllLeagues_usesLeaguesPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("leagues.json");

        api.getAllLeagues();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("leagues"));
        assertFalse(request.getPath().contains("leagues/"));
    }

    @Test
    void getLeagueById_returnsSingleParsed() throws IOException, SportMonksException {
        enqueue("league_detail.json");

        League league = api.getLeagueById(8L);

        assertNotNull(league);
        assertEquals(8L, league.getId());
        assertEquals("Premier League", league.getName());
    }

    @Test
    void getLeagueById_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("league_detail.json");

        api.getLeagueById(8L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("leagues/8"));
    }

    @Test
    void getLiveLeagues_usesLivePath() throws IOException, SportMonksException, InterruptedException {
        enqueue("leagues.json");

        api.getLiveLeagues();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("leagues/live"));
    }

    @Test
    void getLiveLeagues_returnsParsedList() throws IOException, SportMonksException {
        enqueue("leagues.json");

        List<League> leagues = api.getLiveLeagues();

        assertEquals(2, leagues.size());
    }

    @Test
    void getLeaguesByDate_usesDatePath() throws IOException, SportMonksException, InterruptedException {
        enqueue("leagues.json");

        api.getLeaguesByDate("2024-10-26");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("leagues/date/2024-10-26"));
    }

    @Test
    void getLeaguesByCountry_usesCountryPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("leagues.json");

        api.getLeaguesByCountry(462L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("leagues/countries/462"));
    }

    @Test
    void searchLeagues_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("leagues.json");

        api.searchLeagues("Premier");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("leagues/search/Premier"));
    }

    @Test
    void searchLeagues_returnsParsedList() throws IOException, SportMonksException {
        enqueue("leagues.json");

        List<League> leagues = api.searchLeagues("Premier");

        assertEquals(2, leagues.size());
    }

    @Test
    void getLeaguesByTeam_usesTeamPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("leagues.json");

        api.getLeaguesByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("leagues/teams/1"));
        assertFalse(request.getPath().contains("current"));
    }

    @Test
    void getCurrentLeaguesByTeam_usesCurrentPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("leagues.json");

        api.getCurrentLeaguesByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("leagues/teams/1/current"));
    }
}
