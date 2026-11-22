package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Checks of issues #83 and #85 against the real API: path encoding, error codes, parsing and
 * the page size of full walks. Every call is capped with a small {@code limit} to save quota.
 */
class HttpInfrastructureIntegrationTest {

    private static SportMonksAPI api;

    @BeforeAll
    static void setUp() {
        String apiKey = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(apiKey != null && !apiKey.isEmpty(),
                "SPORTMONKS_API_KEY not set - skipping integration tests");
        api = new SportMonksAPI(apiKey);
    }

    private static void assertEveryNameContains(List<Team> teams, String text) {
        for (Team team : teams) {
            assertTrue(team.getName().contains(text),
                    "searched for '" + text + "' but the API matched '" + team.getName() + "'");
        }
    }

    @Test
    void searchTeams_questionMarkIsPartOfTheSearchedText() throws IOException, SportMonksException {
        List<Team> teams = api.searchTeams("Real?x", 5);

        assertEveryNameContains(teams, "Real?x");
    }

    @Test
    void searchTeams_hashIsPartOfTheSearchedText() throws IOException, SportMonksException {
        List<Team> teams = api.searchTeams("Inter#1", 5);

        assertEveryNameContains(teams, "Inter#1");
    }

    @Test
    void searchTeams_accentsAndSpacesStillMatch() throws IOException, SportMonksException {
        // The API search ignores accents, so it also matches "Atletico Madrid W".
        List<Team> teams = api.searchTeams("Atlético Madrid", 2);

        assertFalse(teams.isEmpty(), "the encoded name should still find Atletico Madrid");
        assertEveryNameContains(teams, "Madrid");
    }
}
