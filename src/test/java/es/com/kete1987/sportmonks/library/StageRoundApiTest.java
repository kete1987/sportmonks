package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StageRoundApiTest extends BaseApiTest {

    // --- Stages ---

    @Test
    void getAllStages_usesStagesPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("stages.json");

        api.getAllStages();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("stages"));
        assertFalse(request.getPath().contains("stages/"));
    }

    @Test
    void getAllStages_returnsParsedList() throws IOException, SportMonksException {
        enqueue("stages.json");

        List<Stage> stages = api.getAllStages();

        assertEquals(2, stages.size());
        assertEquals(1L, stages.get(0).getId());
        assertEquals("Regular Season", stages.get(0).getName());
    }

    @Test
    void getStagesBySeasonId_usesSeasonPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("stages.json");

        api.getStagesBySeasonId(23614L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("stages/seasons/23614"));
    }

    @Test
    void getStageById_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("stage_detail.json");

        api.getStageById(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("stages/1"));
    }

    @Test
    void getStageById_returnsSingleParsed() throws IOException, SportMonksException {
        enqueue("stage_detail.json");

        Stage stage = api.getStageById(1L);

        assertNotNull(stage);
        assertEquals(1L, stage.getId());
        assertEquals("Regular Season", stage.getName());
        assertEquals(1L, stage.getSportId());
        assertEquals(8L, stage.getLeagueId());
        assertEquals(23614L, stage.getSeasonId());
        assertFalse(stage.getFinished());
        assertTrue(stage.getIsCurrent());
        assertNull(stage.getFixtures());
        assertNull(stage.getAggregates());
        assertNull(stage.getGroups());
    }

    @Test
    void searchStages_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("stages.json");

        api.searchStages("Regular");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("stages/search/Regular"));
    }

    // --- Rounds ---

    @Test
    void getAllRounds_usesRoundsPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("rounds.json");

        api.getAllRounds();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("rounds"));
        assertFalse(request.getPath().contains("rounds/"));
    }

    @Test
    void getAllRounds_returnsParsedList() throws IOException, SportMonksException {
        enqueue("rounds.json");

        List<Round> rounds = api.getAllRounds();

        assertEquals(2, rounds.size());
        assertEquals(1L, rounds.get(0).getId());
        assertEquals("1", rounds.get(0).getName());
    }

    @Test
    void getRoundsBySeasonId_usesSeasonPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("rounds.json");

        api.getRoundsBySeasonId(23614L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("rounds/seasons/23614"));
    }

    @Test
    void getRoundById_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("round_detail.json");

        api.getRoundById(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("rounds/1"));
    }

    @Test
    void getRoundById_returnsSingleParsed() throws IOException, SportMonksException {
        enqueue("round_detail.json");

        Round round = api.getRoundById(1L);

        assertNotNull(round);
        assertEquals(1L, round.getId());
        assertEquals("1", round.getName());
        assertEquals(1L, round.getSportId());
        assertEquals(8L, round.getLeagueId());
        assertEquals(23614L, round.getSeasonId());
        assertEquals(1L, round.getStageId());
        assertTrue(round.getFinished());
        assertFalse(round.getIsCurrent());
        assertNull(round.getFixtures());
    }

    @Test
    void searchRounds_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("rounds.json");

        api.searchRounds("Matchday");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("rounds/search/Matchday"));
    }
}
