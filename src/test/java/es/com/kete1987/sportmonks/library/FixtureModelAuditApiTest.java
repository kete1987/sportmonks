package es.com.kete1987.sportmonks.library;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.team.TeamMeta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Audit of the fixture model (issue #80). The JSON files are trimmed copies of real responses:
 * fixture 19732648 (Barcelona - Real Madrid, with the sport, league, season, stage, round, tvstations,
 * odds, periods and state includes), a World Cup group fixture (19609153, with {@code group}) and a
 * Champions League play-off (19662553, with {@code aggregate}). Gson drops unknown keys silently, so
 * each test compares the raw JSON with the model through {@link JsonCoverage}.
 */
class FixtureModelAuditApiTest extends BaseApiTest {
    private static final String FULL = "fixture_detail_audit.json";
    private static final String GROUP = "fixture_detail_audit_group.json";
    private static final String AGGREGATE = "fixture_detail_audit_aggregate.json";

    private static List<String> missing(String fixture) throws IOException {
        try (InputStream in = FixtureModelAuditApiTest.class.getClassLoader().getResourceAsStream("fixtures/" + fixture);
             Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            JsonElement data = JsonParser.parseReader(reader).getAsJsonObject().get("data");
            return JsonCoverage.missing(data, MatchDetail.class);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"result_info", "details", "placeholder", "has_odds", "has_premium_odds"})
    void matchBaseFieldIsModelled(String key) throws IOException {
        assertFalse(missing(FULL).contains("MatchDetail." + key), "Match does not model " + key);
    }

    @ParameterizedTest
    @ValueSource(strings = {"sport", "league", "season", "stage", "round", "tvstations", "odds"})
    void matchDetailModelsTheInclude(String include) throws IOException {
        assertFalse(missing(FULL).contains("MatchDetail." + include), "MatchDetail does not model include " + include);
    }

    @ParameterizedTest
    @ValueSource(strings = {GROUP, AGGREGATE})
    void matchDetailModelsGroupAndAggregateIncludes(String fixture) throws IOException {
        List<String> missing = missing(fixture);

        assertFalse(missing.contains("MatchDetail.group") || missing.contains("MatchDetail.aggregate"),
                "MatchDetail does not model: " + missing);
    }

    @Test
    void periodModelsHasTimer() throws IOException {
        assertFalse(missing(FULL).contains("MatchDetail.periods[].has_timer"), "Period does not model has_timer");
    }

    @ParameterizedTest
    @ValueSource(strings = {FULL, GROUP, AGGREGATE})
    void everyKeyOfTheFixtureIsModelled(String fixture) throws IOException {
        List<String> missing = missing(fixture);

        assertTrue(missing.isEmpty(), "MatchDetail does not model: " + missing);
    }

    @Test
    void teamMetaWinnerIsTheBooleanTheApiSends() throws IOException, SportMonksException {
        enqueue(FULL);

        MatchDetail match = api.getMatchDetail("19732648");

        TeamMeta home = match.getHomeTeam().getMeta();
        TeamMeta away = match.getAwayTeam().getMeta();
        Object homeWinner = home.getWinner();
        Object awayWinner = away.getWinner();
        assertNotNull(homeWinner);
        assertInstanceOf(Boolean.class, homeWinner);
        assertEquals(Boolean.FALSE, homeWinner);
        assertEquals(Boolean.TRUE, awayWinner);
    }
}

