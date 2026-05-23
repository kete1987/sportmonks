package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.teamranking.TeamRanking;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamRankingApiTest extends BaseApiTest {

    @Test
    void getAllTeamRankings_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("team_rankings.json");

        api.getAllTeamRankings();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("team-rankings"));
        assertFalse(request.getPath().contains("team-rankings/"));
    }

    @Test
    void getAllTeamRankings_returnsParsedList() throws IOException, SportMonksException {
        enqueue("team_rankings.json");

        List<TeamRanking> rankings = api.getAllTeamRankings();

        assertEquals(2, rankings.size());
        assertEquals(10001L, rankings.get(0).getId());
        assertEquals(1L, rankings.get(0).getTeamId());
        assertEquals(1L, rankings.get(0).getPosition());
        assertEquals(1850L, rankings.get(0).getPoints());
        assertEquals("2024-01-01", rankings.get(0).getRankingDate());
    }

    @Test
    void getTeamRankingsByTeam_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("team_rankings.json");

        api.getTeamRankingsByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("team-rankings/teams/1"));
    }

    @Test
    void getTeamRankingsByDate_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("team_rankings.json");

        api.getTeamRankingsByDate("2024-01-01");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("team-rankings/date/2024-01-01"));
    }

    @Test
    void getTeamRankingsByDate_returnsParsedList() throws IOException, SportMonksException {
        enqueue("team_rankings.json");

        List<TeamRanking> rankings = api.getTeamRankingsByDate("2024-01-01");

        assertEquals(2, rankings.size());
    }
}
