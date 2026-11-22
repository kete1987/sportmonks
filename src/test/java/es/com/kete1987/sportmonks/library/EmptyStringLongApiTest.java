package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Issue #83: the {@code ""}-as-null tolerance applied to {@code int}/{@code Integer} only, but
 * almost every id in the models is a {@code Long}, so an empty string there broke the response.
 */
class EmptyStringLongApiTest extends BaseApiTest {

    private void enqueueBody(String body) {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(body)
                .addHeader("Content-Type", "application/json"));
    }

    @Test
    void emptyStringInALongField_isReadAsNull() throws IOException, SportMonksException {
        enqueueBody("{\"data\":{\"id\":1,\"country_id\":\"\",\"venue_id\":\"7\"}}");

        Team team = api.getTeamById(1L);

        assertNull(team.getCountryId());
        assertEquals(Long.valueOf(7), team.getVenueId());
    }

    @Test
    void largeLongValues_areNotTruncatedToInt() throws IOException, SportMonksException {
        enqueueBody("{\"data\":{\"id\":9000000000}}");

        assertEquals(Long.valueOf(9_000_000_000L), api.getTeamById(1L).getId());
    }
}
