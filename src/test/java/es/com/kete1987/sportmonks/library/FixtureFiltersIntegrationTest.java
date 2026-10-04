package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.LineUpData;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.util.StatisticsType;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FixtureFiltersIntegrationTest {
    private static SportMonksAPI api;

    @BeforeAll
    static void setUp() {
        String key = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(key != null && !key.isEmpty(), "SPORTMONKS_API_KEY not set");
        api = new SportMonksAPI(key);
    }

    @Test
    void entityFiltersConfirmLineupDetailTypes() throws IOException, SportMonksException {
        assertTrue(api.getAllEntityFilters().get("lineupdetail").contains("lineupdetailTypes"));
    }

    @Test
    void captainFilterReducesDetailsWithoutChangingPlayersOrCaptains() throws IOException, SportMonksException {
        String[] includes = {"lineups.details"};
        MatchDetail unfiltered = api.getMatchDetail("18545372", includes);
        MatchDetail filtered = api.getMatchDetail("18545372", includes, "lineupdetailTypes:40");

        assertFalse(unfiltered.getLineups().isEmpty());
        Map<Long, Boolean> captains = unfiltered.getLineups().stream()
                .collect(Collectors.toMap(LineUpData::getId, LineUpData::isCaptain));
        assertTrue(captains.containsValue(true), "Fixture must contain a captain to exercise the filter");
        assertEquals(captains, filtered.getLineups().stream()
                .collect(Collectors.toMap(LineUpData::getId, LineUpData::isCaptain)));
        assertTrue(filtered.getLineups().stream().flatMap(p -> p.getDetails().stream())
                .allMatch(d -> d.getTypeId() != null && d.getTypeId() == StatisticsType.CAPTAIN));
        long before = unfiltered.getLineups().stream().mapToLong(p -> p.getDetails().size()).sum();
        long after = filtered.getLineups().stream().mapToLong(p -> p.getDetails().size()).sum();
        assertTrue(after > 0 && after < before, "Filtered response must contain only captain details");
    }

    @Test
    void roundFixturesAcceptNestedLineupFilters() throws IOException, SportMonksException {
        List<MatchDetail> fixtures = api.getRoundById(275911L,
                new String[]{"fixtures.lineups.details"}, "lineupdetailTypes:40").getFixtures();
        assertFalse(fixtures.isEmpty());
        assertTrue(fixtures.stream().flatMap(f -> f.getLineups().stream()).anyMatch(LineUpData::isCaptain));
        assertTrue(fixtures.stream().flatMap(f -> f.getLineups().stream()).flatMap(p -> p.getDetails().stream())
                .allMatch(d -> d.getTypeId() != null && d.getTypeId() == StatisticsType.CAPTAIN));
    }

    @Test
    void filteredRoundCollectionReturnsTheCompleteSeason() throws IOException, SportMonksException {
        Round reference = api.getRoundById(275911L);
        List<Round> rounds = api.getRoundsBySeasonId(reference.getSeasonId(), new String[0], "roundLeagues:564");
        assertEquals(38, rounds.size(), "The completed La Liga season has 38 rounds, exceeding one 25-row page");
        assertEquals(38, rounds.stream().map(Round::getId).distinct().count());
        assertTrue(rounds.stream().anyMatch(r -> r.getId().equals(reference.getId())));
        assertTrue(rounds.stream().allMatch(r -> r.getSeasonId().equals(reference.getSeasonId())));
    }
}
