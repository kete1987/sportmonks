package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.match.StatisticsData;
import es.com.kete1987.sportmonks.library.football.util.StatisticsType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsDataValueTest extends BaseApiTest {

    private List<StatisticsData> loadStatistics() throws IOException, SportMonksException {
        enqueue("fixture_statistics_values.json");
        MatchDetail match = api.getMatchDetail("19732648", "statistics");
        return match.getStatistics();
    }

    @Test
    void integerValue_isReadAsLongAndDouble() throws IOException, SportMonksException {
        StatisticsData stat = loadStatistics().get(0);

        assertEquals(14L, stat.getData().getValue());
        assertEquals(14.0, stat.getData().getDoubleValue());
    }

    @Test
    void decimalValue_isNotLostByDoubleGetter() throws IOException, SportMonksException {
        StatisticsData stat = loadStatistics().get(1);

        assertEquals(StatisticsType.EXPECTED_GOALS, stat.getTypeId().intValue());
        assertEquals(2.37, stat.getData().getDoubleValue());
        assertEquals(2L, stat.getData().getValue());
    }

    @Test
    void nullValue_doesNotThrow() throws IOException, SportMonksException {
        StatisticsData stat = loadStatistics().get(2);

        assertNull(stat.getData().getValue());
        assertNull(stat.getData().getDoubleValue());
    }

    @Test
    void missingValue_doesNotThrow() throws IOException, SportMonksException {
        StatisticsData stat = loadStatistics().get(3);

        assertNull(stat.getData().getValue());
        assertNull(stat.getData().getDoubleValue());
    }

    @Test
    void statisticsType_newConstantsHaveApiIds() {
        assertEquals(5304, StatisticsType.EXPECTED_GOALS);
        assertEquals(5305, StatisticsType.EXPECTED_GOALS_ON_TARGET);
        assertEquals(9706, StatisticsType.CHANCES_CREATED);
        assertEquals(27264, StatisticsType.SUCCESSFUL_LONG_PASSES);
        assertEquals(27265, StatisticsType.SUCCESSFUL_LONG_PASSES_PERCENTAGE);
        assertEquals(27266, StatisticsType.AERIALS_LOST);
        assertEquals(27267, StatisticsType.TACKLES_WON);
        assertEquals(27268, StatisticsType.TACKLES_WON_PERCENTAGE);
        assertEquals(27269, StatisticsType.PASSES_IN_FINAL_THIRD);
        assertEquals(27270, StatisticsType.LONG_BALLS_WON_PERCENTAGE);
        assertEquals(27271, StatisticsType.BALL_RECOVERY);
        assertEquals(27272, StatisticsType.BACKWARD_PASSES);
        assertEquals(27273, StatisticsType.POSSESSION_LOST);
        assertEquals(27274, StatisticsType.AERIALS);
        assertEquals(27275, StatisticsType.AERIALS_WON_PERCENTAGE);
        assertEquals(27276, StatisticsType.DUELS_WON_PERCENTAGE);
        assertEquals(48997, StatisticsType.ERROR_LEAD_TO_SHOT);
    }
}
