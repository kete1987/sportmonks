package es.com.kete1987.sportmonks.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import es.com.kete1987.sportmonks.library.football.model.standings.Standings;
import org.junit.jupiter.api.Test;

class StandingsDetailLookupTest {

    private static Standings parse(String json) {
        return new FootballApi("test-token").gson().fromJson(json, Standings.class);
    }

    @Test
    void aDetailWithoutTypeIdIsSkippedInsteadOfThrowing() {
        Standings standings = parse("{\"details\":[{\"value\":1},{\"type_id\":129,\"value\":7}]}");

        assertEquals(7, standings.getDetails(129).getValue().intValue());
    }

    @Test
    void anUnknownTypeReturnsNull() {
        Standings standings = parse("{\"details\":[{\"type_id\":129,\"value\":7}]}");

        assertNull(standings.getDetails(130));
    }
}
