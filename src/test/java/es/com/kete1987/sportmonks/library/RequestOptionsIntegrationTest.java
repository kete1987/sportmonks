package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.player.Player;
import es.com.kete1987.sportmonks.library.football.model.seasonstats.ParticipantSeasonStats;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.topscorers.TopScoresPlayer;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The effect of {@link RequestOptions} against the real API (#84). Runs only under
 * {@code -Pintegration} and skips itself without {@code SPORTMONKS_API_KEY}.
 */
class RequestOptionsIntegrationTest {

    private static final long CELTIC = 53L;
    private static final long PLAYER = 580L;
    private static final long PLAYER_SEASON = 1586L;
    private static final String SEASON = "19735";
    private static final String STATISTICS = "statistics";
    private static final RequestOptions DESC = RequestOptions.builder().order(RequestOptions.Order.DESC).build();

    private static SportMonksAPI api;

    @BeforeAll
    static void setUp() {
        String key = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(key != null && !key.isEmpty(), "SPORTMONKS_API_KEY not set");
        api = new SportMonksAPI(key);
    }

    @Test
    void select_returnsOnlyTheSelectedFieldsPlusKeys() throws IOException, SportMonksException {
        Team team = api.withOptions(RequestOptions.builder().select("name", "image_path").build()).getTeamById(CELTIC);

        assertEquals("Celtic", team.getName());
        assertNotNull(team.getImagePath());
        assertEquals(CELTIC, team.getId());
        assertNull(team.getShortCode());
        assertNull(team.getFounded());
        assertNull(team.getGender());
    }

    @Test
    void orderDesc_reversesLeagues() throws IOException, SportMonksException {
        List<Long> ascending = ids(api.getAllLeagues(3));
        List<Long> descending = ids(api.withOptions(DESC).getAllLeagues(3));

        assertTrue(descending.get(0) > ascending.get(ascending.size() - 1), ascending + " vs " + descending);
        assertTrue(isStrictlyDescending(descending), descending.toString());
    }

    /** More than one page: the cursor is only valid together with the order it was created with. */
    @Test
    void orderDesc_survivesTheCursorWalk() throws IOException, SportMonksException {
        List<Long> descending = ids(api.withOptions(DESC).getAllLeagues(60));

        assertEquals(60, descending.size());
        assertTrue(isStrictlyDescending(descending), descending.toString());
    }

    @Test
    void sortByStartingAtDesc_ordersFixtures() throws IOException, SportMonksException {
        RequestOptions latestFirst = DESC.toBuilder().sortBy("starting_at").build();

        List<String> starts = api.withOptions(latestFirst).getAllFixtures(5).stream()
                .map(MatchDetail::getStartingAt).collect(Collectors.toList());
        String firstByDefault = api.getAllFixtures(1).get(0).getStartingAt();

        assertEquals(5, starts.size());
        List<String> sorted = starts.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        assertEquals(sorted, starts);
        assertTrue(starts.get(0).compareTo(firstByDefault) > 0, starts.get(0) + " vs " + firstByDefault);
    }

    @Test
    void filters_reachAnEndpointWithoutAFiltersParameter() throws IOException, SportMonksException {
        Player unfiltered = api.getPlayerById(PLAYER, STATISTICS);
        Player filtered = api.withOptions(RequestOptions.builder()
                .filters("playerstatisticSeasons:" + PLAYER_SEASON).build()).getPlayerById(PLAYER, STATISTICS);

        assertTrue(unfiltered.getStatistics().size() > 1);
        assertFalse(filtered.getStatistics().isEmpty());
        assertTrue(filtered.getStatistics().size() < unfiltered.getStatistics().size());
        assertTrue(filtered.getStatistics().stream().map(ParticipantSeasonStats::getSeasonId)
                .allMatch(id -> id == PLAYER_SEASON));
    }

    @Test
    void topScoresFiltered_combinesWithOptionFilters() throws IOException, SportMonksException {
        List<TopScoresPlayer> leader = api.getTopScoresFiltered(SEASON, 208, 1);
        long playerId = leader.get(0).getPlayerId();

        List<TopScoresPlayer> combined = api.withOptions(RequestOptions.builder()
                .filters("seasontopscorerPlayers:" + playerId).build()).getTopScoresFiltered(SEASON, 208, 0);

        assertEquals(1, combined.size());
        assertEquals(playerId, combined.get(0).getPlayerId());
    }

    @Test
    void sortBy_isRejectedWhereTheEntityHasNoCustomSort() {
        SportMonksAPI byPoints = api.withOptions(RequestOptions.builder().sortBy("points").build());

        SportMonksException e = assertThrows(SportMonksException.class, () -> byPoints.getStandings(SEASON));
        assertEquals(400, e.getStatusCode());
    }

    private static List<Long> ids(List<League> leagues) {
        return leagues.stream().map(League::getId).collect(Collectors.toList());
    }

    private static boolean isStrictlyDescending(List<Long> ids) {
        for (int i = 1; i < ids.size(); i++) {
            if (ids.get(i) >= ids.get(i - 1)) return false;
        }
        return true;
    }
}
