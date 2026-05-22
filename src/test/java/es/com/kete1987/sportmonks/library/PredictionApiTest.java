package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.prediction.Predictability;
import es.com.kete1987.sportmonks.library.football.model.prediction.Probability;
import es.com.kete1987.sportmonks.library.football.model.prediction.ValueBet;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PredictionApiTest extends BaseApiTest {

    @Test
    void getAllProbabilities_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("probabilities.json");

        api.getAllProbabilities();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("predictions/probabilities"));
        assertFalse(request.getPath().contains("leagues"));
        assertFalse(request.getPath().contains("fixtures"));
    }

    @Test
    void getAllProbabilities_returnsParsedList() throws IOException, SportMonksException {
        enqueue("probabilities.json");

        List<Probability> probabilities = api.getAllProbabilities();

        assertEquals(2, probabilities.size());
        assertEquals(1001L, probabilities.get(0).getId());
        assertEquals(100L, probabilities.get(0).getFixtureId());
        assertEquals(55.5, probabilities.get(0).getHome());
        assertEquals(25.0, probabilities.get(0).getDraw());
        assertEquals(19.5, probabilities.get(0).getAway());
    }

    @Test
    void getPredictabilityByLeague_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("predictability.json");

        api.getPredictabilityByLeague(271L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("predictions/probabilities/leagues/271"));
    }

    @Test
    void getPredictabilityByLeague_returnsParsedList() throws IOException, SportMonksException {
        enqueue("predictability.json");

        List<Predictability> list = api.getPredictabilityByLeague(271L);

        assertEquals(1, list.size());
        assertEquals(271L, list.get(0).getLeagueId());
        assertEquals(68.4, list.get(0).getPredictability());
        assertEquals(19686L, list.get(0).getSeasonId());
    }

    @Test
    void getProbabilitiesByFixture_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("probabilities.json");

        api.getProbabilitiesByFixture(100L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("predictions/probabilities/fixtures/100"));
    }

    @Test
    void getProbabilitiesByFixture_returnsParsedList() throws IOException, SportMonksException {
        enqueue("probabilities.json");

        List<Probability> probabilities = api.getProbabilitiesByFixture(100L);

        assertFalse(probabilities.isEmpty());
    }

    @Test
    void getAllValueBets_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("value_bets.json");

        api.getAllValueBets();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("predictions/value-bets"));
        assertFalse(request.getPath().contains("fixtures"));
    }

    @Test
    void getAllValueBets_returnsParsedList() throws IOException, SportMonksException {
        enqueue("value_bets.json");

        List<ValueBet> bets = api.getAllValueBets();

        assertEquals(1, bets.size());
        assertEquals(2001L, bets.get(0).getId());
        assertEquals(100L, bets.get(0).getFixtureId());
        assertEquals(1L, bets.get(0).getBookmakerId());
        assertEquals(2.15, bets.get(0).getOdds());
        assertEquals(8.3, bets.get(0).getValue());
        assertEquals("Home", bets.get(0).getLabel());
    }

    @Test
    void getValueBetsByFixture_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("value_bets.json");

        api.getValueBetsByFixture(100L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("predictions/value-bets/fixtures/100"));
    }
}
