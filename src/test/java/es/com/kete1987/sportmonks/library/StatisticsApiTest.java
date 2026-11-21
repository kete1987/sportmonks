package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.statistic.SeasonStatistic;
import es.com.kete1987.sportmonks.library.football.model.statistic.SeasonStatisticEntity;
import es.com.kete1987.sportmonks.library.football.model.statistic.Statistic;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsApiTest extends BaseApiTest {

    @ParameterizedTest
    @EnumSource(SeasonStatisticEntity.class)
    void getSeasonStatistics_usesTheEntityRoute(SeasonStatisticEntity entity) throws IOException, SportMonksException, InterruptedException {
        enqueue("season_statistics_players.json");

        api.getSeasonStatistics(entity, 580L);

        RecordedRequest request = server.takeRequest();
        assertEquals("/statistics/seasons/" + entity.getPath() + "/580", request.getRequestUrl().encodedPath());
    }

    @Test
    void getSeasonStatistics_parsesTheSeasonAndItsDetails() throws IOException, SportMonksException {
        enqueue("season_statistics_players.json");

        SeasonStatistic season = api.getSeasonStatistics(SeasonStatisticEntity.PLAYERS, 580L).get(0);

        assertEquals(1879282337L, season.getId());
        assertEquals(580L, season.getPlayerId());
        assertEquals(18701L, season.getTeamId());
        assertEquals(27797L, season.getSeasonId());
        assertTrue(season.getHasValues());
        assertEquals(27L, season.getPositionId());
        assertEquals(7, season.getJerseyNumber());
        assertNull(season.getCoachId());
        assertNull(season.getRefereeId());
        assertEquals(2, season.getDetails().size());
    }

    @Test
    void getSeasonStatistics_detailsKeepTheirValues() throws IOException, SportMonksException {
        enqueue("season_statistics_players.json");

        SeasonStatistic season = api.getSeasonStatistics(SeasonStatisticEntity.PLAYERS, 580L).get(0);

        Statistic rating = season.getDetailByTypeId(118L);
        assertNotNull(rating);
        assertEquals(53593121L, rating.getId());
        assertEquals(6.75, ((Map<?, ?>) rating.getValue()).get("average"));
        assertNull(season.getDetailByTypeId(999L));
    }

    @Test
    void getSeasonStatistics_followsTheCursorAndKeepsIncludes() throws IOException, SportMonksException, InterruptedException {
        enqueue("season_statistics_teams.json");
        enqueue("season_statistics_teams_page2.json");

        List<SeasonStatistic> seasons = api.getSeasonStatistics(SeasonStatisticEntity.TEAMS, 676L, "season");

        assertEquals(2, seasons.size());
        assertEquals(27965L, seasons.get(1).getSeasonId());
        server.takeRequest();
        RecordedRequest second = server.takeRequest();
        assertEquals("SEASON2", second.getRequestUrl().queryParameter("cursor"));
        assertEquals("season", second.getRequestUrl().queryParameter("include"));
    }

    @Test
    void getSeasonStatistics_withLimit_setsPerPage() throws IOException, SportMonksException, InterruptedException {
        enqueue("season_statistics_teams.json");

        List<SeasonStatistic> seasons = api.getSeasonStatistics(SeasonStatisticEntity.TEAMS, 676L, 1);

        assertEquals(1, seasons.size());
        assertEquals("1", server.takeRequest().getRequestUrl().queryParameter("per_page"));
    }

    @Test
    void getStatsByStage_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("statistics.json");

        api.getStatsByStage(77L);

        RecordedRequest request = server.takeRequest();
        assertEquals("/statistics/stages/77", request.getRequestUrl().encodedPath());
    }

    @Test
    void getStatsByStage_returnsParsedList() throws IOException, SportMonksException {
        enqueue("statistics.json");

        List<Statistic> stats = api.getStatsByStage(77L);

        assertEquals(2, stats.size());
        assertEquals(4050806L, stats.get(1).getId());
    }

    @Test
    void getStatsByRound_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("statistics.json");

        api.getStatsByRound(88L);

        RecordedRequest request = server.takeRequest();
        assertEquals("/statistics/rounds/88", request.getRequestUrl().encodedPath());
    }

    @Test
    void getStatsByRound_keepsModelAndRelationIds() throws IOException, SportMonksException {
        enqueue("statistics.json");

        List<Statistic> stats = api.getStatsByRound(151251L);

        assertEquals(151251L, stats.get(0).getModelId());
        assertNull(stats.get(0).getRelationId());
        assertEquals(106L, stats.get(1).getRelationId());
        assertEquals("Getafe", ((Map<?, ?>) stats.get(1).getValue()).get("team_most_corners_name"));
    }
}
