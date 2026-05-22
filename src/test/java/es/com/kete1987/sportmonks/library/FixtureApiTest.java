package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FixtureApiTest extends BaseApiTest {

    // -------------------------------------------------------------------------
    // getTodayMatches
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // getTodayMatchesFiltered
    // -------------------------------------------------------------------------

    @Test
    void getTodayMatchesFiltered_withIds_usesMultiPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getTodayMatchesFiltered(new String[]{"1", "2"});

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("livescores/multi/1,2"));
    }

    @Test
    void getTodayMatchesFiltered_withNullIds_fallsBackToTodayMatches() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getTodayMatchesFiltered(null);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("livescores"));
        assertFalse(request.getPath().contains("multi"));
    }

    // -------------------------------------------------------------------------
    // getLiveMatches
    // -------------------------------------------------------------------------

    @Test
    void getLiveMatches_usesInplayPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getLiveMatches();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("livescores/inplay"));
    }

    @Test
    void getLiveMatches_returnsParsedList() throws IOException, SportMonksException {
        enqueue("fixtures_single_page.json");

        List<MatchDetail> matches = api.getLiveMatches();

        assertEquals(2, matches.size());
        assertEquals(1, matches.get(0).getId().intValue());
    }

    // -------------------------------------------------------------------------
    // getLatestLivescores
    // -------------------------------------------------------------------------

    @Test
    void getLatestLivescores_usesLatestPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getLatestLivescores();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("livescores/latest"));
    }

    @Test
    void getLatestLivescores_returnsParsedList() throws IOException, SportMonksException {
        enqueue("fixtures_single_page.json");

        List<MatchDetail> matches = api.getLatestLivescores();

        assertEquals(2, matches.size());
        assertEquals(1, matches.get(0).getId().intValue());
    }

    // -------------------------------------------------------------------------
    // getMatchesByDate
    // -------------------------------------------------------------------------

    @Test
    void getMatchesByDate_usesDatePath() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getMatchesByDate("2024-10-26");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("fixtures/date/2024-10-26"));
    }

    @Test
    void getMatchesByDate_returnsParsedList() throws IOException, SportMonksException {
        enqueue("fixtures_single_page.json");

        List<MatchDetail> matches = api.getMatchesByDate("2024-10-26");

        assertEquals(2, matches.size());
    }

    // -------------------------------------------------------------------------
    // getMatchesByDateRange
    // -------------------------------------------------------------------------

    @Test
    void getMatchesByDateRange_usesBetweenPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getMatchesByDateRange("2024-10-01", "2024-10-31");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("fixtures/between/2024-10-01/2024-10-31"));
    }

    @Test
    void getMatchesByDateRange_returnsParsedList() throws IOException, SportMonksException {
        enqueue("fixtures_single_page.json");

        List<MatchDetail> matches = api.getMatchesByDateRange("2024-10-01", "2024-10-31");

        assertEquals(2, matches.size());
    }

    // -------------------------------------------------------------------------
    // getMatchesByDateRangeForTeam
    // -------------------------------------------------------------------------

    @Test
    void getMatchesByDateRangeForTeam_usesTeamPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getMatchesByDateRangeForTeam("2024-10-01", "2024-10-31", "42");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("fixtures/between/2024-10-01/2024-10-31/42"));
    }

    // -------------------------------------------------------------------------
    // getMatchesByMultipleIDs
    // -------------------------------------------------------------------------

    @Test
    void getMatchesByMultipleIDs_usesMultiPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getMatchesByMultipleIDs(new String[]{"1", "2", "3"});

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("fixtures/multi/1,2,3"));
    }

    // -------------------------------------------------------------------------
    // getHeadToHead
    // -------------------------------------------------------------------------

    @Test
    void getHeadToHead_usesHeadToHeadPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getHeadToHead("10", "20");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("fixtures/head-to-head/10/20"));
    }

    @Test
    void getHeadToHead_returnsParsedList() throws IOException, SportMonksException {
        enqueue("fixtures_single_page.json");

        List<MatchDetail> matches = api.getHeadToHead("10", "20");

        assertEquals(2, matches.size());
        assertEquals(1, matches.get(0).getId().intValue());
    }

    // -------------------------------------------------------------------------
    // searchFixtures
    // -------------------------------------------------------------------------

    @Test
    void searchFixtures_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.searchFixtures("Barcelona");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("fixtures/search/Barcelona"));
    }

    @Test
    void searchFixtures_returnsParsedList() throws IOException, SportMonksException {
        enqueue("fixtures_single_page.json");

        List<MatchDetail> matches = api.searchFixtures("Barcelona");

        assertEquals(2, matches.size());
    }

    // -------------------------------------------------------------------------
    // getLatestFixtures
    // -------------------------------------------------------------------------

    @Test
    void getLatestFixtures_usesLatestPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getLatestFixtures();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("fixtures/latest"));
    }

    @Test
    void getLatestFixtures_returnsParsedList() throws IOException, SportMonksException {
        enqueue("fixtures_single_page.json");

        List<MatchDetail> matches = api.getLatestFixtures();

        assertEquals(2, matches.size());
    }

    // -------------------------------------------------------------------------
    // getMatchDetail
    // -------------------------------------------------------------------------

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
