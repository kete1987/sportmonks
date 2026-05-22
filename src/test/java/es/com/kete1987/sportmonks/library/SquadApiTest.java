package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.squad.SquadPlayer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SquadApiTest extends BaseApiTest {

    @Test
    void getSquadByTeam_usesTeamPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("squad.json");

        api.getSquadByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("squads/teams/1"));
        assertFalse(request.getPath().contains("extended"));
    }

    @Test
    void getSquadByTeam_returnsParsedList() throws IOException, SportMonksException {
        enqueue("squad.json");

        List<SquadPlayer> squad = api.getSquadByTeam(1L);

        assertEquals(2, squad.size());
        assertEquals(100L, squad.get(0).getPlayerId());
        assertEquals(1L, squad.get(0).getTeamId());
        assertEquals(10L, squad.get(0).getJerseyNumber());
        assertFalse(squad.get(0).getCaptain());
        assertTrue(squad.get(1).getCaptain());
    }

    @Test
    void getExtendedSquadByTeam_usesExtendedPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("squad.json");

        api.getExtendedSquadByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("squads/teams/1/extended"));
    }

    @Test
    void getExtendedSquadByTeam_returnsParsedList() throws IOException, SportMonksException {
        enqueue("squad.json");

        List<SquadPlayer> squad = api.getExtendedSquadByTeam(1L);

        assertEquals(2, squad.size());
    }

    @Test
    void getSquadBySeasonAndTeam_usesSeasonAndTeamPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("squad.json");

        api.getSquadBySeasonAndTeam(23614L, 1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("squads/seasons/23614/teams/1"));
    }

    @Test
    void getSquadBySeasonAndTeam_returnsParsedList() throws IOException, SportMonksException {
        enqueue("squad.json");

        List<SquadPlayer> squad = api.getSquadBySeasonAndTeam(23614L, 1L);

        assertEquals(2, squad.size());
    }
}
