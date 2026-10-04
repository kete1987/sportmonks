package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.LineUpData;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FixtureFiltersApiTest extends BaseApiTest {
    private static final String FILTERS = "lineupdetailTypes:40;fixtureLeagues:564,8";

    @FunctionalInterface
    interface FilteredCall {
        Object call(SportMonksAPI api, String[] includes, String filters) throws IOException, SportMonksException;
    }

    @FunctionalInterface
    interface LegacyCall {
        Object call(SportMonksAPI api, String[] includes) throws IOException, SportMonksException;
    }

    static Stream<Arguments> endpoints() {
        return Stream.of(
                endpoint("/livescores", "fixtures_single_page.json", SportMonksAPI::getLivescores, SportMonksAPI::getLivescores),
                endpoint("/livescores/multi/1,2", "fixtures_single_page.json", (a, i, f) -> a.getLivescoresFiltered(new String[]{"1", "2"}, i, f), (a, i) -> a.getLivescoresFiltered(new String[]{"1", "2"}, i)),
                endpoint("/livescores/inplay", "fixtures_single_page.json", SportMonksAPI::getLiveMatches, SportMonksAPI::getLiveMatches),
                endpoint("/fixtures/date/2026-09-01", "fixtures_single_page.json", (a, i, f) -> a.getMatchesByDate("2026-09-01", i, f), (a, i) -> a.getMatchesByDate("2026-09-01", i)),
                endpoint("/fixtures/between/2026-09-01/2026-09-02", "fixtures_single_page.json", (a, i, f) -> a.getMatchesByDateRange("2026-09-01", "2026-09-02", i, f), (a, i) -> a.getMatchesByDateRange("2026-09-01", "2026-09-02", i)),
                endpoint("/fixtures/between/2026-09-01/2026-09-02/9", "fixtures_single_page.json", (a, i, f) -> a.getMatchesByDateRangeForTeam("2026-09-01", "2026-09-02", "9", i, f), (a, i) -> a.getMatchesByDateRangeForTeam("2026-09-01", "2026-09-02", "9", i)),
                endpoint("/fixtures/multi/1,2", "fixtures_single_page.json", (a, i, f) -> a.getMatchesByMultipleIDs(new String[]{"1", "2"}, i, f), (a, i) -> a.getMatchesByMultipleIDs(new String[]{"1", "2"}, i)),
                endpoint("/livescores/latest", "fixtures_single_page.json", SportMonksAPI::getLatestUpdatedLivescores, SportMonksAPI::getLatestUpdatedLivescores),
                endpoint("/fixtures/head-to-head/9/10", "fixtures_single_page.json", (a, i, f) -> a.getFixturesByHeadToHead(9, 10, i, f), (a, i) -> a.getFixturesByHeadToHead(9, 10, i)),
                endpoint("/fixtures/search/Barcelona", "fixtures_single_page.json", (a, i, f) -> a.searchFixtures("Barcelona", i, f), (a, i) -> a.searchFixtures("Barcelona", i)),
                endpoint("/fixtures/search/Barcelona", "fixtures_single_page.json", (a, i, f) -> a.searchFixtures("Barcelona", 1, i, f), (a, i) -> a.searchFixtures("Barcelona", 1, i)),
                endpoint("/fixtures/latest", "fixtures_single_page.json", SportMonksAPI::getLatestUpdatedFixtures, SportMonksAPI::getLatestUpdatedFixtures),
                endpoint("/fixtures/upcoming/markets/1", "fixtures_single_page.json", (a, i, f) -> a.getUpcomingFixturesByMarket(1, i, f), (a, i) -> a.getUpcomingFixturesByMarket(1, i)),
                endpoint("/fixtures/upcoming/tv-stations/1", "fixtures_single_page.json", (a, i, f) -> a.getUpcomingFixturesByTvStation(1, i, f), (a, i) -> a.getUpcomingFixturesByTvStation(1, i)),
                endpoint("/fixtures/past/tv-stations/1", "fixtures_single_page.json", (a, i, f) -> a.getPastFixturesByTvStation(1, i, f), (a, i) -> a.getPastFixturesByTvStation(1, i)),
                endpoint("/fixtures/18545372", "fixture_lineups_captain.json", (a, i, f) -> a.getMatchDetail("18545372", i, f), (a, i) -> a.getMatchDetail("18545372", i)),
                endpoint("/stages", "stages.json", SportMonksAPI::getAllStages, SportMonksAPI::getAllStages),
                endpoint("/stages/seasons/13133", "stages.json", (a, i, f) -> a.getStagesBySeasonId(13133, i, f), (a, i) -> a.getStagesBySeasonId(13133, i)),
                endpoint("/stages/1", "stage_detail.json", (a, i, f) -> a.getStageById(1, i, f), (a, i) -> a.getStageById(1, i)),
                endpoint("/stages/search/Final", "stages.json", (a, i, f) -> a.searchStages("Final", i, f), (a, i) -> a.searchStages("Final", i)),
                endpoint("/rounds", "rounds.json", SportMonksAPI::getAllRounds, SportMonksAPI::getAllRounds),
                endpoint("/rounds/seasons/13133", "rounds.json", (a, i, f) -> a.getRoundsBySeasonId(13133, i, f), (a, i) -> a.getRoundsBySeasonId(13133, i)),
                endpoint("/rounds/1", "round_detail.json", (a, i, f) -> a.getRoundById(1, i, f), (a, i) -> a.getRoundById(1, i)),
                endpoint("/rounds/search/Final", "rounds.json", (a, i, f) -> a.searchRounds("Final", i, f), (a, i) -> a.searchRounds("Final", i))
        );
    }

    private static Arguments endpoint(String path, String fixture, FilteredCall filtered, LegacyCall legacy) {
        return Arguments.of(path, fixture, filtered, legacy);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("endpoints")
    void filtersAreEncodedAndLegacyCallsRemainUnfiltered(String path, String fixture, FilteredCall filtered, LegacyCall legacy)
            throws IOException, SportMonksException, InterruptedException {
        String[] includes = path.startsWith("/rounds") || path.startsWith("/stages")
                ? new String[]{"fixtures.lineups.details"} : new String[]{"lineups.details", "participants"};
        enqueue(fixture);
        assertNotNull(filtered.call(api, includes, FILTERS));
        HttpUrl request = server.takeRequest().getRequestUrl();
        assertEquals(path, request.encodedPath());
        assertEquals(String.join(";", includes), request.queryParameter("include"));
        assertEquals(FILTERS, request.queryParameter("filters"));
        assertEquals(1, request.queryParameterValues("filters").size());

        enqueue(fixture);
        assertNotNull(legacy.call(api, includes));
        HttpUrl oldRequest = server.takeRequest().getRequestUrl();
        assertEquals(path, oldRequest.encodedPath());
        assertEquals(request.queryParameter("include"), oldRequest.queryParameter("include"));
        assertNull(oldRequest.queryParameter("filters"));
    }

    @Test
    void captainOnlyDetailsKeepCaptainDetection() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixture_lineups_captain.json");
        List<LineUpData> players = api.getMatchDetail("18545372", new String[]{"lineups.details"}, "lineupdetailTypes:40").getLineups();
        assertEquals(4, players.size());
        assertTrue(players.get(0).isCaptain());
        assertFalse(players.get(1).isCaptain());
        assertFalse(players.get(2).isCaptain());
        assertFalse(players.get(3).isCaptain());
        assertEquals(1, players.get(0).getDetails().size());
        assertEquals(40L, players.get(0).getDetails().get(0).getTypeId());
        assertEquals("lineupdetailTypes:40", server.takeRequest().getRequestUrl().queryParameter("filters"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t\n"})
    void missingFiltersAreOmitted(String filters) throws IOException, SportMonksException, InterruptedException {
        enqueue("fixture_detail.json");
        api.getMatchDetail("1", new String[]{"participants"}, filters);
        HttpUrl request = server.takeRequest().getRequestUrl();
        assertEquals("participants", request.queryParameter("include"));
        assertNull(request.queryParameter("filters"));
    }

    @Test
    void filtersWorkWithoutIncludesOnStandaloneFootballApi() throws IOException, SportMonksException, InterruptedException {
        for (String[] includes : new String[][]{null, new String[0]}) {
            enqueue("fixtures_single_page.json");
            api.getFootball().getLivescores(includes, "fixtureLeagues:564");
            HttpUrl request = server.takeRequest().getRequestUrl();
            assertNull(request.queryParameter("include"));
            assertEquals("fixtureLeagues:564", request.queryParameter("filters"));
        }
    }

    @Test
    void livescoresFallbackKeepsFilters() throws IOException, SportMonksException, InterruptedException {
        for (String[] ids : new String[][]{null, new String[0]}) {
            enqueue("fixtures_single_page.json");
            api.getLivescoresFiltered(ids, new String[]{"lineups.details"}, FILTERS);
            HttpUrl request = server.takeRequest().getRequestUrl();
            assertEquals("/livescores", request.encodedPath());
            assertEquals(FILTERS, request.queryParameter("filters"));
        }
    }

    @Test
    void filtersSurviveCursorPagination() throws IOException, SportMonksException, InterruptedException {
        enqueue("cursor_page1.json");
        enqueue("cursor_page3.json");
        assertEquals(3, api.getMatchesByDateRange("2026-09-01", "2026-09-02", new String[]{"lineups.details"}, FILTERS).size());
        assertPaginationQueries(true);
    }

    @Test
    void filtersSurviveOffsetPagination() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_page1.json");
        enqueue("fixtures_page2.json");
        assertEquals(2, api.getLivescores(new String[]{"lineups.details"}, FILTERS).size());
        assertPaginationQueries(false);
    }

    @Test
    void filteredSearchStillHonorsLimit() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_page1.json");
        enqueue("error_401.json", 401);
        assertEquals(1, api.searchFixtures("Barcelona", 1, new String[]{"lineups.details"}, FILTERS).size());
        assertEquals(1, server.getRequestCount());
        HttpUrl request = server.takeRequest().getRequestUrl();
        assertEquals("1", request.queryParameter("per_page"));
        assertEquals(FILTERS, request.queryParameter("filters"));
    }

    private void assertPaginationQueries(boolean cursor) throws InterruptedException {
        assertEquals(2, server.getRequestCount());
        HttpUrl first = server.takeRequest().getRequestUrl();
        HttpUrl second = server.takeRequest().getRequestUrl();
        for (HttpUrl request : new HttpUrl[]{first, second}) {
            assertEquals(FILTERS, request.queryParameter("filters"));
            assertEquals("lineups.details", request.queryParameter("include"));
        }
        assertEquals(first.encodedPath(), second.encodedPath());
        if (cursor) {
            assertNotNull(second.queryParameter("cursor"));
            assertNull(second.queryParameter("page"));
        } else {
            assertEquals("2", second.queryParameter("page"));
            assertNull(second.queryParameter("cursor"));
        }
    }

    @Test
    void filterValuesCannotInjectOtherQueryParameters() throws IOException, SportMonksException, InterruptedException {
        String base = server.url("/").toString();
        api = new SportMonksAPI(new OkHttpClient(), base, base, base, base, "es", "Europe/Madrid");
        enqueue("fixture_detail.json");
        String filter = "lineupdetailTypes:40&locale=en#test+value";
        api.getMatchDetail("1", new String[]{"lineups.details"}, filter);
        HttpUrl request = server.takeRequest().getRequestUrl();
        assertEquals(filter, request.queryParameter("filters"));
        assertEquals("es", request.queryParameter("locale"));
        assertEquals("Europe/Madrid", request.queryParameter("timezone"));
        assertEquals(1, request.queryParameterValues("locale").size());
        assertNull(request.fragment());
    }

    @Test
    void entityFiltersUseMyBaseAndExposeLineupDetailTypes() throws IOException, SportMonksException, InterruptedException {
        String base = server.url("/").toString();
        api = new SportMonksAPI(new OkHttpClient(), base + "football/", base + "odds/", base + "core/", base + "my/");
        enqueue("filters.json");
        assertTrue(api.getAllEntityFilters().get("lineupdetail").contains("lineupdetailTypes"));
        assertEquals("/my/filters/entity", server.takeRequest().getRequestUrl().encodedPath());
    }
}
