package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.Formation;
import es.com.kete1987.sportmonks.library.football.model.match.LineUpData;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/** Uses synthetic API data, not a captured response or complete starting eleven. */
class FormationApiTest extends BaseApiTest {
    @Test
    void getMatchDetailPreservesFormationsAndRawLineupPositions()
            throws IOException, SportMonksException, InterruptedException {
        enqueue("fixture_formations.json");
        String[] includes = {"participants", "formations", "lineups", "lineups.details"};

        MatchDetail match = api.getMatchDetail("1001", includes);

        HttpUrl url = server.takeRequest().getRequestUrl();
        assertEquals("/fixtures/1001", url.encodedPath());
        assertEquals(new HashSet<>(Arrays.asList(includes)),
                new HashSet<>(Arrays.asList(url.queryParameter("include").split(";"))));

        List<Formation> formations = match.getFormations();
        assertEquals(2, formations.size());
        assertFormation(formations.get(0), 2002L, 20L, "4-4-2", "away");
        assertFormation(formations.get(1), 2001L, 10L, "4-2-3-1", "home");
        assertThrows(UnsupportedOperationException.class, () -> formations.add(new Formation()));

        LineUpData starter = match.getLineups().get(0);
        assertEquals("2:4", starter.getFormationField());
        assertEquals(5L, starter.getFormationPosition());
        assertEquals(154L, starter.getDetailedPositionId());
        assertEquals("unexpected/raw:slot?", match.getLineups().get(3).getFormationField());
        assertEquals(1L, match.getLineups().get(3).getFormationPosition());

        // Explicit null and absent fields are separate cases in the fixture.
        assertMissingPosition(match.getLineups().get(1));
        assertMissingPosition(match.getLineups().get(2));

        assertEquals(Arrays.asList(3001L, 3005L), ids(match.getLineupLocalTeam()));
        assertEquals(Arrays.asList(3004L), ids(match.getLineupVisitorTeam()));
        assertEquals(Arrays.asList(3003L), ids(match.getBenchLocalTeam()));
        assertEquals(Arrays.asList(3002L), ids(match.getBenchVisitorTeam()));
        assertEquals("D", starter.getPosition());
        assertTrue(starter.isCaptain());
        assertFalse(match.getLineups().get(1).isCaptain());
        assertEquals(formations.get(1).getParticipantId(), starter.getTeamId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", ",\"formations\":null"})
    void absentOrNullFormationsRemainNull(String relation) throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setBody("{\"data\":{\"id\":1001" + relation + "}}"));
        assertNull(api.getMatchDetail("1001").getFormations());
    }

    @Test
    void emptyFormationsRemainEmptyAndUnmodifiable() throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setBody("{\"data\":{\"formations\":[]}}"));
        List<Formation> formations = api.getMatchDetail("1001", "formations").getFormations();
        assertTrue(formations.isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> formations.add(new Formation()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"{}", "{\"id\":null,\"fixture_id\":null,\"participant_id\":null,\"formation\":null,\"location\":null}"})
    void incompleteFormationToleratesMissingAndNullFields(String formation)
            throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setBody("{\"data\":{\"formations\":[" + formation + "]}}"));
        Formation result = api.getMatchDetail("1001", "formations").getFormations().get(0);
        assertNull(result.getId());
        assertNull(result.getFixtureId());
        assertNull(result.getParticipantId());
        assertNull(result.getFormation());
        assertNull(result.getLocation());
    }

    private void assertFormation(Formation formation, long id, long participantId, String system, String location) {
        assertEquals(id, formation.getId());
        assertEquals(1001L, formation.getFixtureId());
        assertEquals(participantId, formation.getParticipantId());
        assertEquals(system, formation.getFormation());
        assertEquals(location, formation.getLocation());
    }

    private void assertMissingPosition(LineUpData player) {
        assertNull(player.getFormationField());
        assertNull(player.getFormationPosition());
        assertNull(player.getDetailedPositionId());
    }

    private List<Long> ids(List<LineUpData> players) {
        return players.stream().map(LineUpData::getId).collect(Collectors.toList());
    }
}
