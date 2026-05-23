package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.matchfact.MatchFact;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchFactApiTest extends BaseApiTest {

    @Test
    void getAllMatchFacts_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("match_facts.json");

        api.getAllMatchFacts();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("match-facts"));
        assertFalse(request.getPath().contains("match-facts/"));
    }

    @Test
    void getAllMatchFacts_returnsParsedList() throws IOException, SportMonksException {
        enqueue("match_facts.json");

        List<MatchFact> facts = api.getAllMatchFacts();

        assertEquals(2, facts.size());
        assertEquals(8001L, facts.get(0).getId());
        assertEquals(1001L, facts.get(0).getFixtureId());
        assertEquals(10L, facts.get(0).getTypeId());
        assertEquals("2023-10-15T20:30:00Z", facts.get(0).getCreatedAt());
    }

    @Test
    void getMatchFactsByFixture_usesFixturePath() throws IOException, SportMonksException, InterruptedException {
        enqueue("match_facts.json");

        api.getMatchFactsByFixture(1001L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("match-facts/fixtures/1001"));
    }

    @Test
    void getMatchFactsByDateRange_usesDateRangePath() throws IOException, SportMonksException, InterruptedException {
        enqueue("match_facts.json");

        api.getMatchFactsByDateRange("2023-10-01", "2023-10-31");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("match-facts/between/2023-10-01/2023-10-31"));
    }

    @Test
    void getMatchFactsByLeague_usesLeaguePath() throws IOException, SportMonksException, InterruptedException {
        enqueue("match_facts.json");

        api.getMatchFactsByLeague(501L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("match-facts/leagues/501"));
    }
}
