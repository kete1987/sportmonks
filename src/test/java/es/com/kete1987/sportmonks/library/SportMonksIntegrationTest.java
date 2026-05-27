package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.continent.Continent;
import es.com.kete1987.sportmonks.library.core.model.country.Country;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.match.StatisticsData;
import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.standings.Standings;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.topscorers.TopScoresPlayer;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SportMonksIntegrationTest {

    // Fixed test data IDs — same used in historical manual tests
    private static final String SEASON_ID       = "13133";
    private static final String MATCH_ID_STATS  = "18545372";
    private static final String MATCH_ID_ODDS   = "19439629";
    private static final long   LEAGUE_ID       = 564L;
    private static final long   STAGE_SEASON_ID = 12950L;
    private static final long   ROUND_ID        = 151251L;
    private static final long   TEAM_ID         = 676L;

    private static SportMonksAPI api;

    @BeforeAll
    static void setUp() {
        String apiKey = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(apiKey != null && !apiKey.isEmpty(),
                "SPORTMONKS_API_KEY not set — skipping integration tests");
        api = new SportMonksAPI(apiKey);
    }

    // -------------------------------------------------------------------------
    // Core
    // -------------------------------------------------------------------------

    @Test
    void getAllContinents_returnsNonEmptyList() throws IOException, SportMonksException {
        List<Continent> result = api.getAllContinents();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    @Test
    void getAllCountries_returnsNonEmptyList() throws IOException, SportMonksException {
        List<Country> result = api.getAllCountries();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    // -------------------------------------------------------------------------
    // Leagues
    // -------------------------------------------------------------------------

    @Test
    void getAllLeagues_returnsNonEmptyList() throws IOException, SportMonksException {
        List<League> result = api.getAllLeagues();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    @Test
    void getLeagueById_returnsCorrectLeague() throws IOException, SportMonksException {
        League result = api.getLeagueById(LEAGUE_ID);
        assertNotNull(result);
        assertEquals(LEAGUE_ID, result.getId());
    }

    // -------------------------------------------------------------------------
    // Seasons
    // -------------------------------------------------------------------------

    @Test
    void getAllSeasons_returnsNonEmptyList() throws IOException, SportMonksException {
        List<SeasonData> result = api.getSeasons();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertNotNull(result.get(0).getId());
    }

    @Test
    void getSeasonById_returnsCorrectSeason() throws IOException, SportMonksException {
        SeasonData result = api.getSeasonById(Long.parseLong(SEASON_ID));
        assertNotNull(result);
        assertEquals(Long.parseLong(SEASON_ID), result.getId().longValue());
    }

    // -------------------------------------------------------------------------
    // Fixtures
    // -------------------------------------------------------------------------

    @Test
    void getTodayMatches_returnsNonNullList() throws IOException, SportMonksException {
        List<MatchDetail> result = api.getTodayMatches();
        assertNotNull(result);
        // May be empty if no matches today — valid response
    }

    @Test
    void getMatchesByDate_returnsNonEmptyList() throws IOException, SportMonksException {
        List<MatchDetail> result = api.getMatchesByDate("2019-05-07");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    @Test
    void getMatchesByDateRange_returnsNonEmptyList() throws IOException, SportMonksException {
        List<MatchDetail> result = api.getMatchesByDateRange("2023-03-13", "2023-03-14");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    @Test
    void getMatchDetail_withIncludes_returnsPopulatedData() throws IOException, SportMonksException {
        String[] includes = {"state", "participants", "scores", "periods"};
        MatchDetail result = api.getMatchDetail(MATCH_ID_STATS, includes);

        assertNotNull(result);
        assertTrue(result.getId() > 0);
        assertNotNull(result.getState());
        assertNotNull(result.getParticipants());
        assertFalse(result.getParticipants().isEmpty());
        assertNotNull(result.getScores());
        assertNotNull(result.getHomeTeam());
        assertNotNull(result.getAwayTeam());
    }

    @Test
    void getMatchDetail_withStatisticsInclude_returnsStatisticsData() throws IOException, SportMonksException {
        String[] includes = {"statistics", "participants"};
        MatchDetail result = api.getMatchDetail(MATCH_ID_STATS, includes);

        assertNotNull(result);
        assertNotNull(result.getStatistics());
        assertFalse(result.getStatistics().isEmpty());

        boolean hasAwayStats = false;
        for (StatisticsData s : result.getStatistics()) {
            assertNotNull(s.getTypeId());
            assertTrue(s.getTypeId() > 0);
            if ("away".equals(s.getLocation())) {
                hasAwayStats = true;
            }
        }
        assertTrue(hasAwayStats, "Expected at least one away statistics entry");
    }

    @Test
    void getMatchDetail_withLineupsInclude_returnsLineupData() throws IOException, SportMonksException {
        String[] includes = {"lineups", "participants"};
        MatchDetail result = api.getMatchDetail(MATCH_ID_STATS, includes);

        assertNotNull(result);
        assertNotNull(result.getLineups());
        assertFalse(result.getLineups().isEmpty());
    }

    // -------------------------------------------------------------------------
    // Odds
    // -------------------------------------------------------------------------

    @Test
    void getMatchOdds_returnsNonNullList() throws IOException, SportMonksException {
        List<Odd> result = api.getMatchOdds(MATCH_ID_ODDS);
        assertNotNull(result);
    }

    @Test
    void getMatchOddsByMarket_returnsNonEmptyList() throws IOException, SportMonksException {
        List<Odd> result = api.getMatchOddsByMarket(MATCH_ID_ODDS, "57");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    // -------------------------------------------------------------------------
    // Standings
    // -------------------------------------------------------------------------

    @Test
    void getStandings_returnsNonEmptyList() throws IOException, SportMonksException {
        List<Standings> result = api.getStandings(SEASON_ID);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // -------------------------------------------------------------------------
    // Top Scorers
    // -------------------------------------------------------------------------

    @Test
    void getTopScores_returnsNonEmptyList() throws IOException, SportMonksException {
        List<TopScoresPlayer> result = api.getTopScores(SEASON_ID);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // -------------------------------------------------------------------------
    // Venues
    // -------------------------------------------------------------------------

    @Test
    void getVenues_bySeason_returnsNonEmptyList() throws IOException, SportMonksException {
        List<Venue> result = api.getVenues(SEASON_ID);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    // -------------------------------------------------------------------------
    // Teams
    // -------------------------------------------------------------------------

    @Test
    void getTeamsBySeasonId_returnsNonEmptyList() throws IOException, SportMonksException {
        List<Team> result = api.getTeamsBySeasonId(Long.parseLong(SEASON_ID));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    @Test
    void getTeamById_withIncludes_returnsCorrectTeam() throws IOException, SportMonksException {
        Team result = api.getTeamById(TEAM_ID, "sport", "country", "venue");
        assertNotNull(result);
        assertEquals(TEAM_ID, result.getId().longValue());
    }

    // -------------------------------------------------------------------------
    // Stages
    // -------------------------------------------------------------------------

    @Test
    void getStagesBySeasonId_returnsNonNullList() throws IOException, SportMonksException {
        List<Stage> result = api.getStagesBySeasonId(STAGE_SEASON_ID);
        assertNotNull(result);
    }

    // -------------------------------------------------------------------------
    // Rounds
    // -------------------------------------------------------------------------

    @Test
    void getRoundsBySeasonId_returnsNonEmptyList() throws IOException, SportMonksException {
        List<Round> result = api.getRoundsBySeasonId(Long.parseLong(SEASON_ID));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getId() > 0);
    }

    @Test
    void getRoundById_returnsCorrectRound() throws IOException, SportMonksException {
        Round result = api.getRoundById(ROUND_ID);
        assertNotNull(result);
        assertEquals(ROUND_ID, result.getId().longValue());
    }

    // -------------------------------------------------------------------------
    // Rate limit
    // -------------------------------------------------------------------------

    @Test
    void rateLimitHeaders_arePopulatedAfterRequest() throws IOException, SportMonksException {
        api.getAllContinents();
        assertNotNull(api.getRemainingRequests());
        assertNotNull(api.getMaximumRequests());
    }
}
