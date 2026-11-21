package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.football.util.StatisticsType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ids de las constantes añadidas en #75, tomados de {@code GET /core/types}. La coherencia del
 * conjunto de {@link StatisticsType} con el catálogo real la comprueba {@code StatisticsTypeCatalogIntegrationTest}.
 */
class StatisticsTypeTest {

    @ParameterizedTest(name = "{0} = {1}")
    @CsvSource({
            "EXPECTED_POINTS, 7939",
            "EXPECTED_GOALS_PENALTIES, 7940",
            "EXPECTED_GOALS_FREE_KICKS, 7941",
            "EXPECTED_GOALS_CORNERS, 7942",
            "EXPECTED_NON_PENALTY_GOALS, 7943",
            "EXPECTED_GOALS_SET_PLAY, 7944",
            "EXPECTED_GOALS_OPEN_PLAY, 7945",
            "EXPECTED_GOALS_DIFFERENCE, 9684",
            "SHOOTING_PERFORMANCE, 9685",
            "EXPECTED_GOALS_PREVENTED, 9686",
            "EXPECTED_GOALS_AGAINST, 9687",
            "AVERAGE_PLAYER_HEIGHT, 9672",
            "AVERAGE_PLAYER_AGE, 9673",
            "AMOUNT_OF_FOREIGNERS, 9674",
            "PLAYERS_FOOTING, 9675",
            "AVERAGE_POINTS_PER_GAME, 9676",
            "APPEARING_PLAYERS, 9677",
            "MOST_SUBSTITUTED_PLAYERS, 9678",
            "MOST_INJURED_PLAYERS, 9679",
            "PENALTY_CONVERSION_RATE, 9680",
            "SHOT_CONVERSION_RATE, 9681",
            "SHOT_ON_TARGET_PERCENTAGE, 9682",
            "FOULS_PER_CARD, 9683",
            "YELLOWCARDS_OVERTIME, 74",
            "CLEARANCES_OFFLINE, 2271",
            "SCORING_FREQUENCY, 27248",
            "TOTAL_MINUTES_PLAYED, 27249",
            "MOST_SCORED_HALF, 27250",
            "MOST_FREQUENT_SCORING_MINUTE, 27251",
            "INTERCEPTION_STATS, 27252",
            "PASS_STATS, 27253",
            "ASSIST_STATS, 27254",
            "CROSSES_BLOCKED, 27255",
            "HALF_RESULTS, 27256",
            "TEAM_OF_THE_WEEK, 27257",
            "NATIONAL_TEAM_PLAYERS, 27258",
            "HATTRICKS, 27259",
            "INJURY_TIME_GOALS, 27260",
            "GOAL_RESULTS, 27261",
            "TRANSFER_MONEY, 27262",
            "GAMES_PLAYED, 27263",
            "PLAYER_SHOT_CONVERSION, 28036"
    })
    void catalogConstants_haveApiIds(String constant, int expectedId) throws ReflectiveOperationException {
        assertEquals(expectedId, StatisticsType.class.getField(constant).getInt(null));
    }
}
