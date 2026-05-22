package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FixtureApiTest extends BaseApiTest {

    @Test
    void getTodayMatches_returnsParsedList() throws IOException, SportMonksException {
        enqueue("fixtures_single_page.json");

        List<MatchDetail> matches = api.getTodayMatches();

        assertEquals(2, matches.size());
        assertEquals(1, matches.get(0).getId().intValue());
        assertEquals("FC Barcelona vs Real Madrid", matches.get(0).getName());
        assertEquals(2, matches.get(1).getId().intValue());
    }

    @Test
    void getTodayMatches_paginationFetchesAllPages() throws IOException, SportMonksException {
        enqueue("fixtures_page1.json");
        enqueue("fixtures_page2.json");

        List<MatchDetail> matches = api.getTodayMatches();

        assertEquals(2, matches.size());
        assertEquals(1, matches.get(0).getId().intValue());
        assertEquals(2, matches.get(1).getId().intValue());
    }

    @Test
    void getTodayMatches_includesArePassedAsQueryParam() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getTodayMatches("participants", "scores");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("include=participants%3Bscores")
                || request.getPath().contains("include=participants;scores"));
    }

    @Test
    void getMatchDetail_returnsParsedDetail() throws IOException, SportMonksException {
        enqueue("fixture_detail.json");

        MatchDetail match = api.getMatchDetail("1");

        assertEquals(1, match.getId().intValue());
        assertEquals("FC Barcelona vs Real Madrid", match.getName());
        assertNotNull(match.getParticipants());
        assertEquals(2, match.getParticipants().size());
        assertEquals(3, match.getCurrentLocalTeamGoals());
        assertEquals(1, match.getCurrentVisitorTeamGoals());
    }

    @Test
    void getMatchDetail_homeAndAwayTeams() throws IOException, SportMonksException {
        enqueue("fixture_detail.json");

        MatchDetail match = api.getMatchDetail("1");

        assertNotNull(match.getHomeTeam());
        assertNotNull(match.getAwayTeam());
        assertEquals("FC Barcelona", match.getHomeTeam().getName());
        assertEquals("Real Madrid", match.getAwayTeam().getName());
    }

    @Test
    void getMatchDetail_throws_on401() throws IOException {
        enqueue("error_401.json", 401);

        SportMonksException ex = assertThrows(SportMonksException.class,
                () -> api.getMatchDetail("1"));
        assertTrue(ex.getMessage().startsWith("401"));
    }

    @Test
    void getMatchDetail_throws_on429() throws IOException {
        enqueue("error_429.json", 429);

        SportMonksException ex = assertThrows(SportMonksException.class,
                () -> api.getMatchDetail("1"));
        assertTrue(ex.getMessage().startsWith("429"));
    }

    @Test
    void rateLimitHeaders_areExposedAfterRequest() throws IOException, SportMonksException {
        enqueue("fixtures_single_page.json");

        api.getTodayMatches();

        assertEquals("3000", api.getMaximumRequests());
        assertEquals("2999", api.getRemainingRequests());
    }
}
