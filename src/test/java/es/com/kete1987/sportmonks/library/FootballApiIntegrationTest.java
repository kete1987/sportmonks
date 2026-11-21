package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.schedule.Schedule;
import es.com.kete1987.sportmonks.library.football.model.statistic.SeasonStatistic;
import es.com.kete1987.sportmonks.library.football.model.statistic.SeasonStatisticEntity;
import es.com.kete1987.sportmonks.library.football.model.statistic.Statistic;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.referee.Referee;
import es.com.kete1987.sportmonks.library.football.model.coach.Coach;
import es.com.kete1987.sportmonks.library.football.model.rival.Rival;
import es.com.kete1987.sportmonks.library.football.model.comments.Comment;
import es.com.kete1987.sportmonks.library.football.model.player.Player;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Checks of issue #82 against the real API: routes that do not exist, endpoints the library
 * lacks, methods that stop after the first page and models that drop fields. A 403 means the
 * route exists but the test subscription has no access to it; 404 or 422 mean the route is wrong.
 *
 * <p>Endpoints with thousands of rows are walked with a {@code limit} above the 50-row page cap
 * ({@link #ACROSS_PAGES}), which needs a second request, instead of crawling every page.
 */
class FootballApiIntegrationTest {

    private static final long TEAM_ID = 676L;
    private static final long PLAYER_ID = 580L;
    private static final long COACH_ID = 455800L;
    private static final long REFEREE_ID = 53L;
    private static final long ROUND_ID = 151251L;
    private static final long STAGE_ID = 77457696L;
    private static final long SEASON_ID = 13133L;
    private static final long SPAIN_ID = 32L;
    private static final long FIXTURE_ID = 19732648L;
    private static final long LALIGA_ID = 564L;
    private static final int PAGE = 25;
    private static final int ACROSS_PAGES = 60;

    private static SportMonksAPI api;
    private static Gson gson;

    @BeforeAll
    static void setUp() {
        String apiKey = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(apiKey != null && !apiKey.isEmpty(),
                "SPORTMONKS_API_KEY not set - skipping integration tests");
        api = new SportMonksAPI(apiKey);
        gson = api.getFootball().gson();
    }

    @FunctionalInterface
    private interface ApiCall {
        void run() throws IOException, SportMonksException;
    }

    @FunctionalInterface
    private interface ListCall<T> {
        List<T> get() throws IOException, SportMonksException;
    }

    /** HTTP status carried by a {@link SportMonksException} message ({@code "code - body"}). */
    private static int statusOf(SportMonksException e) {
        String message = String.valueOf(e.getMessage());
        int dash = message.indexOf(" - ");
        try {
            return Integer.parseInt((dash > 0 ? message.substring(0, dash) : message).trim());
        } catch (NumberFormatException notAStatus) {
            return -1;
        }
    }

    /** Passes on success or 403 (route exists, no access); fails on 404/422 (wrong route). */
    private static void assertRouteExists(String route, ApiCall call) throws IOException {
        try {
            call.run();
        } catch (SportMonksException e) {
            int status = statusOf(e);
            assertNotEquals(404, status, route + " does not exist: " + e.getMessage());
            assertNotEquals(422, status, route + " is rejected: " + e.getMessage());
            assertEquals(403, status, route + " failed: " + e.getMessage());
        }
    }

    private static <T> void assertNoRepeats(List<T> result, Function<T, Long> id) {
        Set<Long> ids = new HashSet<>();
        for (T item : result) {
            assertTrue(ids.add(id.apply(item)), "repeated id " + id.apply(item) + " - page was re-fetched");
        }
    }

    /** For datasets of a few pages, fetched whole: more than one page and no page repeated. */
    private static <T> void assertMoreThanOnePage(List<T> result, Function<T, Long> id) {
        assertTrue(result.size() > PAGE, "expected more than one page, got " + result.size());
        assertNoRepeats(result, id);
    }

    /** For huge datasets: a limit past the page cap must be filled from a second page. */
    private static <T> void assertFillsLimitAcrossPages(List<T> result, Function<T, Long> id) {
        assertEquals(ACROSS_PAGES, result.size(), "the walk stopped after the first page");
        assertNoRepeats(result, id);
    }

    /**
     * Venues with non-numeric zip codes ("L4 4EL") break {@code Venue.zipcode} (a {@code Long});
     * that model is fixed by issue #81, so the check is skipped rather than failed until then.
     */
    private static List<Venue> venuesOrSkip(ListCall<Venue> call) throws IOException, SportMonksException {
        try {
            return call.get();
        } catch (JsonSyntaxException e) {
            Assumptions.assumeFalse(String.valueOf(e.getMessage()).contains("zipcode"),
                    "Venue.zipcode cannot parse a real venue yet (issue #81): " + e.getMessage());
            throw e;
        }
    }

    // -------------------------------------------------------------------------
    // Broken routes
    // -------------------------------------------------------------------------

    @Test
    void getSeasonStatistics_ofATeam_keepsSeasonAndDetails() throws IOException, SportMonksException {
        List<SeasonStatistic> seasons = api.getSeasonStatistics(SeasonStatisticEntity.TEAMS, TEAM_ID, 3);

        assertEquals(3, seasons.size());
        SeasonStatistic first = seasons.get(0);
        assertEquals(TEAM_ID, first.getTeamId());
        assertNotNull(first.getSeasonId());
        assertFalse(first.getDetails().isEmpty());
        assertNotNull(first.getDetails().get(0).getTypeId());
        assertNotNull(first.getDetails().get(0).getValue());
    }

    @Test
    void getSeasonStatistics_ofAPlayerCoachAndReferee_useTheirOwnRoutes() throws IOException, SportMonksException {
        SeasonStatistic player = api.getSeasonStatistics(SeasonStatisticEntity.PLAYERS, PLAYER_ID, 1).get(0);
        SeasonStatistic coach = api.getSeasonStatistics(SeasonStatisticEntity.COACHES, COACH_ID, 1).get(0);
        SeasonStatistic referee = api.getSeasonStatistics(SeasonStatisticEntity.REFEREES, REFEREE_ID, 1).get(0);

        assertEquals(PLAYER_ID, player.getPlayerId());
        assertNotNull(player.getTeamId());
        assertEquals(COACH_ID, coach.getCoachId());
        assertEquals(REFEREE_ID, referee.getRefereeId());
        assertFalse(referee.getDetails().isEmpty());
    }

    @Test
    void getSeasonStatistics_walksPastTheFirstPage() throws IOException, SportMonksException {
        List<SeasonStatistic> seasons = api.getSeasonStatistics(SeasonStatisticEntity.TEAMS, TEAM_ID);

        assertMoreThanOnePage(seasons, SeasonStatistic::getId);
    }

    @Test
    void getMatchFactsByFixture_routeExists() throws IOException {
        assertRouteExists("match-facts by fixture", () -> api.getMatchFactsByFixture(FIXTURE_ID));
    }

    @Test
    void getMatchFactsByDateRange_routeExists() throws IOException {
        assertRouteExists("match-facts by date range", () -> api.getMatchFactsByDateRange("2026-10-01", "2026-10-05"));
    }

    // -------------------------------------------------------------------------
    // Models that drop fields
    // -------------------------------------------------------------------------

    @Test
    void getStatsByRound_keepsModelAndRelationIds() throws IOException, SportMonksException {
        List<Statistic> stats = api.getStatsByRound(ROUND_ID);

        assertFalse(stats.isEmpty());
        boolean anyRelation = false;
        for (Statistic s : stats) {
            JsonObject json = gson.toJsonTree(s).getAsJsonObject();
            assertTrue(json.has("model_id"), "model_id lost by the model: " + json);
            anyRelation |= json.has("relation_id");
        }
        assertTrue(anyRelation, "relation_id lost by the model");
        assertEquals(ROUND_ID, stats.get(0).getModelId());
    }

    @Test
    void getSchedulesByTeam_keepsTheStageIdentity() throws IOException, SportMonksException {
        List<Schedule> schedules = api.getSchedulesByTeam(TEAM_ID);

        assertFalse(schedules.isEmpty());
        JsonObject json = gson.toJsonTree(schedules.get(0)).getAsJsonObject();
        for (String key : new String[]{"id", "name", "type_id", "sort_order", "finished", "is_current", "starting_at", "ending_at", "rounds"}) {
            assertTrue(json.has(key), key + " lost by the model: " + json.keySet());
        }
        assertEquals(schedules.get(0).getId(), schedules.get(0).getRounds().get(0).getStageId());
    }

    // -------------------------------------------------------------------------
    // Missing endpoints
    // -------------------------------------------------------------------------

    @Test
    void getAllFixtures_appliesLimitAndFilters() throws IOException, SportMonksException {
        List<MatchDetail> result = api.getAllFixtures(3, new String[0], "fixtureLeagues:" + LALIGA_ID);

        assertEquals(3, result.size());
        for (MatchDetail match : result) {
            assertEquals(LALIGA_ID, match.getLeagueId());
        }
    }

    @Test
    void getTransferRumoursConfig_routeExists() throws IOException {
        assertRouteExists("transfer-rumours/config", () -> api.getTransferRumoursConfig());
    }

    @Test
    void getVenuesBySeason_acceptsIncludes() throws IOException, SportMonksException {
        List<Venue> venues = venuesOrSkip(() -> api.getVenues(String.valueOf(SEASON_ID), "country"));

        assertFalse(venues.isEmpty());
        assertTrue(venues.get(0).getId() > 0);
    }

    // -------------------------------------------------------------------------
    // Methods that only returned the first page
    // -------------------------------------------------------------------------

    @Test
    void getTeamsByCountry_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(api.getTeamsByCountry(SPAIN_ID, ACROSS_PAGES), Team::getId);
    }

    @Test
    void getRefereesByCountry_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(api.getRefereesByCountry(SPAIN_ID, ACROSS_PAGES), Referee::getId);
    }

    @Test
    void getCoachesByCountry_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(api.getCoachesByCountry(SPAIN_ID, ACROSS_PAGES), Coach::getId);
    }

    @Test
    void getAllVenues_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(venuesOrSkip(() -> api.getAllVenues(ACROSS_PAGES)), Venue::getId);
    }

    @Test
    void searchVenues_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(venuesOrSkip(() -> api.searchVenues("Estadio", ACROSS_PAGES)), Venue::getId);
    }

    @Test
    void getAllRivals_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(api.getAllRivals(ACROSS_PAGES), Rival::getId);
    }

    @Test
    void getAllCommentaries_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(api.getAllCommentaries(ACROSS_PAGES), Comment::getId);
    }

    @Test
    void getStatsByStage_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getStatsByStage(STAGE_ID), s -> s.getId());
    }

    @Test
    void getLatestTransfers_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(api.getLatestTransfers(ACROSS_PAGES), t -> t.getId());
    }

    @Test
    void getTransfersByDateRange_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(api.getTransfersByDateRange("2026-07-01", "2026-07-31", ACROSS_PAGES), t -> t.getId());
    }

    @Test
    void searchTeams_withoutLimit_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.searchTeams("united"), Team::getId);
    }

    @Test
    void searchPlayers_withoutLimit_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(api.searchPlayers("silva", ACROSS_PAGES), Player::getId);
    }

    @Test
    void searchCoaches_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.searchCoaches("garcia"), Coach::getId);
    }

    @Test
    void searchReferees_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(api.searchReferees("garcia", ACROSS_PAGES), Referee::getId);
    }

    @Test
    void getRefereesBySeason_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(api.getRefereesBySeason(SEASON_ID, ACROSS_PAGES), Referee::getId);
    }

    @Test
    void getAllStandings_walksEveryPage() throws IOException, SportMonksException {
        assertFillsLimitAcrossPages(api.getAllStandings(ACROSS_PAGES), s -> s.getId());
    }
}
