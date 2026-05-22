package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.standings.Standings;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StandingsApiTest extends BaseApiTest {

    @Test
    void getAllStandings_usesStandingsPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("standings_all.json");

        api.getAllStandings();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("standings"));
        assertFalse(request.getPath().contains("standings/"));
    }

    @Test
    void getAllStandings_returnsParsedList() throws IOException, SportMonksException {
        enqueue("standings_all.json");

        List<Standings> standings = api.getAllStandings();

        assertEquals(2, standings.size());
        assertEquals(101L, standings.get(0).getId());
        assertEquals(1L, standings.get(0).getPosition());
        assertEquals(89L, standings.get(0).getPoints());
    }

    @Test
    void getStandingsByRound_usesRoundPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("standings_by_round.json");

        api.getStandingsByRound(88L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("standings/rounds/88"));
        assertFalse(request.getPath().contains("/grouped"));
    }

    @Test
    void getStandingsByRound_returnsParsedList() throws IOException, SportMonksException {
        enqueue("standings_by_round.json");

        List<Standings> standings = api.getStandingsByRound(88L);

        assertEquals(1, standings.size());
        assertEquals(201L, standings.get(0).getId());
    }

    @Test
    void getStandingCorrectionsBySeason_usesCorrectionsPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("standings_corrections.json");

        api.getStandingCorrectionsBySeason(19686L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("standings/corrections/seasons/19686"));
    }

    @Test
    void getStandingCorrectionsBySeason_returnsParsedList() throws IOException, SportMonksException {
        enqueue("standings_corrections.json");

        List<Standings> standings = api.getStandingCorrectionsBySeason(19686L);

        assertEquals(1, standings.size());
        assertEquals(301L, standings.get(0).getId());
    }

    @Test
    void getLiveStandingsByLeague_usesLivePath() throws IOException, SportMonksException, InterruptedException {
        enqueue("standings_live.json");

        api.getLiveStandingsByLeague(501L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("standings/live/leagues/501"));
    }

    @Test
    void getLiveStandingsByLeague_returnsParsedList() throws IOException, SportMonksException {
        enqueue("standings_live.json");

        List<Standings> standings = api.getLiveStandingsByLeague(501L);

        assertEquals(1, standings.size());
        assertEquals(401L, standings.get(0).getId());
    }

    @Test
    void getGroupedStandingsByRound_usesGroupedPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("standings_grouped.json");

        api.getGroupedStandingsByRound(88L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("standings/rounds/88/grouped"));
    }

    @Test
    void getGroupedStandingsByRound_returnsParsedList() throws IOException, SportMonksException {
        enqueue("standings_grouped.json");

        List<Standings> standings = api.getGroupedStandingsByRound(88L);

        assertEquals(2, standings.size());
        assertEquals(501L, standings.get(0).getId());
        assertEquals(502L, standings.get(1).getId());
    }
}
