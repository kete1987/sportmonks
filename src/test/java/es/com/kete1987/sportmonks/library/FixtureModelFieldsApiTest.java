package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.FixtureTvStation;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.match.Period;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Typed getters added for issue #80, over the trimmed real responses used by the audit test. */
class FixtureModelFieldsApiTest extends BaseApiTest {

    @Test
    void matchBaseFieldsArrive() throws IOException, SportMonksException {
        enqueue("fixture_detail_audit.json");

        MatchDetail match = api.getMatchDetail("19732648");

        assertEquals("Real Madrid won after full-time.", match.getResultInfo());
        assertNull(match.getDetails());
        assertEquals(Boolean.FALSE, match.getPlaceholder());
        assertEquals(Boolean.TRUE, match.getHasOdds());
        assertEquals(Boolean.TRUE, match.getHasPremiumOdds());
        assertEquals("1/1", match.getLeg());
    }

    @Test
    void sportLeagueSeasonStageAndRoundIncludesAreTyped() throws IOException, SportMonksException {
        enqueue("fixture_detail_audit.json");

        MatchDetail match = api.getMatchDetail("19732648", "sport", "league", "season", "stage", "round");

        assertEquals("Football", match.getSport().getName());
        assertEquals(564L, match.getLeague().getId());
        assertEquals("La Liga", match.getLeague().getName());
        assertEquals("2026/2027", match.getSeason().getName());
        assertEquals(27965L, match.getSeason().getId());
        assertEquals("Regular Season", match.getStage().getName());
        assertEquals(77482690L, match.getStage().getId());
        assertEquals("10", match.getRound().getName());
        assertEquals(408951L, match.getRound().getId());
        assertNull(match.getGroup());
        assertNull(match.getAggregate());
    }

    @Test
    void tvStationsIncludeKeepsThePivotRows() throws IOException, SportMonksException {
        enqueue("fixture_detail_audit.json");

        MatchDetail match = api.getMatchDetail("19732648", "tvStations");

        List<FixtureTvStation> stations = match.getTvStations();
        assertEquals(3, stations.size());
        assertEquals(118930215L, stations.get(0).getId());
        assertEquals(19732648L, stations.get(0).getFixtureId());
        assertEquals(37L, stations.get(0).getTvStationId());
        assertEquals(3483L, stations.get(0).getCountryId());
        FixtureTvStation extra = stations.get(0);
        assertThrows(UnsupportedOperationException.class, () -> stations.add(extra));
    }

    @Test
    void oddsIncludeIsTyped() throws IOException, SportMonksException {
        enqueue("fixture_detail_audit.json");

        MatchDetail match = api.getMatchDetail("19732648", "odds");

        List<Odd> odds = match.getOdds();
        assertEquals(2, odds.size());
        assertEquals(65L, odds.get(0).getMarketId());
        assertEquals("Brahim Diaz", odds.get(0).getName());
        assertEquals(38L, odds.get(0).getSortOrder());
    }

    @Test
    void groupIncludeIsTyped() throws IOException, SportMonksException {
        enqueue("fixture_detail_audit_group.json");

        MatchDetail match = api.getMatchDetail("19609153", "group");

        assertEquals(253019L, match.getGroup().getId());
        assertEquals("Group A", match.getGroup().getName());
        assertEquals(253019L, match.getGroupId());
        assertNull(match.getAggregate());
    }

    @Test
    void aggregateIncludeIsTyped() throws IOException, SportMonksException {
        enqueue("fixture_detail_audit_aggregate.json");

        MatchDetail match = api.getMatchDetail("19662553", "aggregate");

        assertEquals(60238L, match.getAggregate().getId());
        assertEquals("7-5", match.getAggregate().getResult());
        assertEquals(List.of(19662553L, 19662545L), match.getAggregate().getFixtureIds());
        assertEquals(34L, match.getAggregate().getWinnerParticipantId());
    }

    @Test
    void includesThatWereNotRequestedAreNull() throws IOException, SportMonksException {
        enqueue("fixture_detail.json");

        MatchDetail match = api.getMatchDetail("1");

        assertNull(match.getSport());
        assertNull(match.getLeague());
        assertNull(match.getSeason());
        assertNull(match.getStage());
        assertNull(match.getRound());
        assertNull(match.getGroup());
        assertNull(match.getAggregate());
        assertNull(match.getTvStations());
        assertNull(match.getOdds());
    }

    @Test
    void periodsCarryHasTimer() throws IOException, SportMonksException {
        enqueue("fixture_detail_audit.json");

        MatchDetail match = api.getMatchDetail("19732648", "periods");

        Period first = match.getPeriods().get(0);
        assertEquals(Boolean.FALSE, first.getHasTimer());
        assertEquals(45L, first.getPeriodLength());
    }

    @Test
    void teamMetaWinnerHasDerivedGetter() throws IOException, SportMonksException {
        enqueue("fixture_detail_audit.json");

        MatchDetail match = api.getMatchDetail("19732648", "participants");

        assertTrue(match.getAwayTeam().getMeta().isWinner());
        assertFalse(match.getHomeTeam().getMeta().isWinner());
        assertEquals("home", match.getHomeTeam().getMeta().getLocation());
    }
}
