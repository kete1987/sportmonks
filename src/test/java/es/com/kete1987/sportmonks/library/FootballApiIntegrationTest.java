package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import es.com.kete1987.sportmonks.library.common.util.Constants;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.schedule.Schedule;
import es.com.kete1987.sportmonks.library.football.model.statistic.Statistic;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;
import okhttp3.HttpUrl;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Checks of issue #82 against the real API: routes that do not exist, endpoints the library
 * lacks, methods that stop after the first page and models that drop fields. A 403 means the
 * route exists but the test subscription has no access to it; 404 or 422 mean the route is wrong.
 */
class FootballApiIntegrationTest {

    private static final long TEAM_ID = 676L;
    private static final long PLAYER_ID = 580L;
    private static final long ROUND_ID = 151251L;
    private static final long STAGE_ID = 77457696L;
    private static final long SEASON_ID = 13133L;
    private static final long SPAIN_ID = 32L;
    private static final long FIXTURE_ID = 19732648L;
    private static final int PAGE = 25;

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

    private static <T> void assertMoreThanOnePage(List<T> result, Function<T, Long> id) {
        assertTrue(result.size() > PAGE, "expected more than one page, got " + result.size());
        Set<Long> ids = new HashSet<>();
        for (T item : result) {
            assertTrue(ids.add(id.apply(item)), "repeated id " + id.apply(item) + " - page was re-fetched");
        }
    }

    private static Method requireMethod(String name, Class<?>... types) {
        try {
            return FootballApi.class.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            return fail("FootballApi has no " + name + " method: " + e.getMessage());
        }
    }

    private static Object invoke(Method method, Object... args) throws IllegalAccessException {
        try {
            return method.invoke(api.getFootball(), args);
        } catch (InvocationTargetException e) {
            return fail(method.getName() + " threw " + e.getCause(), e.getCause());
        }
    }

    // -------------------------------------------------------------------------
    // Broken routes
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("deprecation")
    void getSeasonStatsByParticipant_hitsAValidRouteAndKeepsTheValues() throws IOException, SportMonksException {
        AtomicReference<List<Statistic>> holder = new AtomicReference<>();
        assertRouteExists("statistics/seasons/participants", () -> holder.set(api.getSeasonStatsByParticipant(TEAM_ID)));

        List<Statistic> stats = holder.get();
        assertNotNull(stats, "no statistics returned");
        assertFalse(stats.isEmpty());
        assertNotNull(stats.get(0).getTypeId(), "type_id lost by the model");
        assertNotNull(stats.get(0).getValue(), "value lost by the model");
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
    }

    @Test
    void getSchedulesByTeam_keepsTheStageIdentity() throws IOException, SportMonksException {
        List<Schedule> schedules = api.getSchedulesByTeam(TEAM_ID);

        assertFalse(schedules.isEmpty());
        JsonObject json = gson.toJsonTree(schedules.get(0)).getAsJsonObject();
        for (String key : new String[]{"id", "name", "type_id", "sort_order", "finished", "is_current", "starting_at", "ending_at", "rounds"}) {
            assertTrue(json.has(key), key + " lost by the model: " + json.keySet());
        }
    }

    // -------------------------------------------------------------------------
    // Missing endpoints
    // -------------------------------------------------------------------------

    @Test
    void getAllFixtures_exists() throws IllegalAccessException {
        Method method = requireMethod("getAllFixtures", int.class, String[].class, String.class);

        Object result = invoke(method, 3, new String[0], "fixtureLeagues:564");

        assertEquals(3, ((List<?>) result).size());
        for (Object match : (List<?>) result) {
            assertEquals(564L, ((MatchDetail) match).getLeagueId());
        }
    }

    @Test
    void getTransferRumoursConfig_exists() throws IOException {
        Method method = requireMethod("getTransferRumoursConfig");

        assertRouteExists("transfer-rumours/config", () -> {
            try {
                method.invoke(api.getFootball());
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof SportMonksException) throw (SportMonksException) e.getCause();
                throw new IOException(e.getCause());
            } catch (IllegalAccessException e) {
                throw new IOException(e);
            }
        });
    }

    @Test
    void getVenuesBySeason_acceptsIncludes() throws IllegalAccessException {
        Method method = requireMethod("getVenues", String.class, String[].class);

        List<?> venues = (List<?>) invoke(method, String.valueOf(SEASON_ID), new String[]{"country"});

        assertFalse(venues.isEmpty());
        assertTrue(((Venue) venues.get(0)).getId() > 0);
    }

    // -------------------------------------------------------------------------
    // Methods that only return the first page
    // -------------------------------------------------------------------------

    @Test
    void getTeamsByCountry_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getTeamsByCountry(SPAIN_ID), t -> t.getId());
    }

    @Test
    void getRefereesByCountry_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getRefereesByCountry(SPAIN_ID), r -> r.getId());
    }

    @Test
    void getCoachesByCountry_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getCoachesByCountry(SPAIN_ID), c -> c.getId());
    }

    @Test
    void getAllVenues_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getAllVenues(), v -> v.getId());
    }

    @Test
    void searchVenues_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.searchVenues("Estadio"), v -> v.getId());
    }

    @Test
    void getAllRivals_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getAllRivals(), r -> r.getId());
    }

    @Test
    void getAllCommentaries_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getAllCommentaries(), c -> c.getId());
    }

    @Test
    void getStatsByStage_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getStatsByStage(STAGE_ID), s -> s.getId());
    }

    @Test
    void getLatestTransfers_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getLatestTransfers(), t -> t.getId());
    }

    @Test
    void getTransfersByDateRange_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getTransfersByDateRange("2026-07-01", "2026-07-31"), t -> t.getId());
    }

    @Test
    void searchTeams_withoutLimit_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.searchTeams("united"), t -> t.getId());
    }

    @Test
    void searchPlayers_withoutLimit_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.searchPlayers("silva"), p -> p.getId());
    }

    @Test
    void searchCoaches_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.searchCoaches("garcia"), c -> c.getId());
    }

    @Test
    void searchReferees_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.searchReferees("garcia"), r -> r.getId());
    }

    @Test
    void getRefereesBySeason_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getRefereesBySeason(SEASON_ID), r -> r.getId());
    }

    @Test
    void getAllStandings_walksEveryPage() throws IOException, SportMonksException {
        assertMoreThanOnePage(api.getAllStandings(), s -> s.getId());
    }

    @Test
    void getSeasonStatsByPlayer_routeIsValid() throws IOException {
        assertRouteExists("statistics/seasons/players", () -> api.getFootball().execute(
                HttpUrl.get(Constants.BASE_URL_FOOTBALL + "statistics/seasons/players/" + PLAYER_ID + "?per_page=1")));
    }
}
