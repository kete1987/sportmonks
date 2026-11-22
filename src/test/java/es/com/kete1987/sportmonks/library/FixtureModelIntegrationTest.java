package es.com.kete1987.sportmonks.library;

import com.google.gson.JsonElement;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Audit of the fixture model against the real API (issue #80). Compares the raw JSON of fixture
 * 19732648 (Barcelona - Real Madrid) and of a group and an aggregate fixture with {@link MatchDetail},
 * and reads the new typed includes. Includes that answer 403 with the test subscription (premium and
 * inplay odds, news, predicted and expected lineups, match facts, AI overviews, xG, trends, pressure
 * and ball coordinates) are not requested.
 *
 * Skipped unless {@code SPORTMONKS_API_KEY} is set; run with {@code mvn test -Pintegration}.
 */
class FixtureModelIntegrationTest {
    private static final String COMMON_INCLUDES = "venue;state;lineups;events;statistics;periods;participants;scores;"
            + "sport;league;season;stage;round;group;aggregate;tvStations;odds;metadata;sidelined;referees;coaches;"
            + "timeline;weatherReport";
    private static String key;
    private static SportMonksAPI api;

    @BeforeAll
    static void setUp() {
        key = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(key != null && !key.isEmpty(), "SPORTMONKS_API_KEY not set");
        api = new SportMonksAPI(key);
    }

    private static void assertCovered(long fixtureId, String includes) throws IOException, InterruptedException {
        JsonElement data = JsonCoverage.fetchData(key, "football/fixtures/" + fixtureId + "?include=" + includes);
        List<String> missing = JsonCoverage.missing(data, MatchDetail.class);
        assertTrue(missing.isEmpty(), "MatchDetail does not model: " + missing);
    }

    @Test
    void leagueFixtureModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered(19732648L, COMMON_INCLUDES);
    }

    @Test
    void cupGroupFixtureModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered(19609153L, "group;stage;league;season;round");
    }

    @Test
    void aggregateFixtureModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered(19662553L, "aggregate;stage;league;season;round");
    }

    @Test
    void typedIncludesAndBaseFieldsOfTheClasico() throws IOException, SportMonksException {
        MatchDetail match = api.getMatchDetail("19732648",
                "sport", "league", "season", "stage", "round", "tvStations", "participants", "periods");

        assertEquals("Football", match.getSport().getName());
        assertEquals("La Liga", match.getLeague().getName());
        assertEquals(27965L, match.getSeason().getId());
        assertEquals(77482690L, match.getStage().getId());
        assertEquals(408951L, match.getRound().getId());
        assertFalse(match.getTvStations().isEmpty());
        assertNotNull(match.getTvStations().get(0).getTvStationId());
        assertNotNull(match.getResultInfo());
        assertNotNull(match.getHasOdds());
        assertNotNull(match.getPlaceholder());
        assertNotNull(match.getPeriods().get(0).getHasTimer());
        assertEquals(Boolean.TRUE, match.getAwayTeam().getMeta().getWinner());
    }

    @Test
    void oddsIncludeReturnsTypedRows() throws IOException, SportMonksException {
        MatchDetail match = api.getMatchDetail("19732648", "odds");

        assertFalse(match.getOdds().isEmpty());
        assertNotNull(match.getOdds().get(0).getSortOrder());
        assertNull(match.getGroup());
    }

    @Test
    void groupAndAggregateIncludesAreTyped() throws IOException, SportMonksException {
        MatchDetail grouped = api.getMatchDetail("19609153", "group");
        MatchDetail twoLegs = api.getMatchDetail("19662553", "aggregate");

        assertEquals("Group A", grouped.getGroup().getName());
        assertEquals("7-5", twoLegs.getAggregate().getResult());
    }
}
