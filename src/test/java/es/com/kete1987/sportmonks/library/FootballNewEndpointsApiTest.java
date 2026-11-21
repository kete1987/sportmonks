package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.transferrumourconfig.TransferRumourConfig;
import es.com.kete1987.sportmonks.library.football.model.transferrumourconfig.TransferRumourConfigEntry;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Endpoints added by issue #82: {@code GET /fixtures}, {@code transfer-rumours/config} and venue includes. */
class FootballNewEndpointsApiTest extends BaseApiTest {

    @Test
    void getAllFixtures_sendsLimitIncludesAndFilters() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        api.getAllFixtures(3, new String[]{"participants"}, "fixtureLeagues:564");

        HttpUrl url = server.takeRequest().getRequestUrl();
        assertEquals("/fixtures", url.encodedPath());
        assertEquals("3", url.queryParameter("per_page"));
        assertEquals("participants", url.queryParameter("include"));
        assertEquals("fixtureLeagues:564", url.queryParameter("filters"));
    }

    @Test
    void getAllFixtures_withoutFilters_omitsTheParameter() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixtures_single_page.json");

        List<MatchDetail> fixtures = api.getAllFixtures(1);

        assertEquals(1, fixtures.size());
        assertNull(server.takeRequest().getRequestUrl().queryParameter("filters"));
    }

    @Test
    void getTransferRumoursConfig_parsesTheCatalogue() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfer_rumours_config.json");

        TransferRumourConfig config = api.getTransferRumoursConfig();

        assertEquals("/transfer-rumours/config", server.takeRequest().getRequestUrl().encodedPath());
        assertEquals(1, config.getFee().size());
        assertEquals(1, config.getTransfers().size());
        assertEquals(2, config.getStatuses().size());
        TransferRumourConfigEntry confirmed = config.getStatuses().get(0);
        assertEquals(90319L, confirmed.getId());
        assertEquals("Confirmed Rumour", confirmed.getName());
        assertEquals("rumour-confirmed", confirmed.getCode());
        assertEquals("RUMOUR_CONFIRMED", confirmed.getDeveloperName());
        assertEquals("rumour-status", confirmed.getModelType());
        assertNull(confirmed.getStatGroup());
    }

    @Test
    void getTransferRumoursConfig_acceptsADataWrapper() throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setBody(
                "{\"data\":{\"statuses\":[{\"id\":90319,\"name\":\"Confirmed Rumour\"}]}}"));

        TransferRumourConfig config = api.getTransferRumoursConfig();

        assertEquals(90319L, config.getStatuses().get(0).getId());
        assertNull(config.getFee());
    }

    @Test
    void getTransferRumoursConfig_emptyBody_returnsNull() throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setBody(""));

        assertNull(api.getTransferRumoursConfig());
    }

    @Test
    void getVenues_bySeason_sendsIncludes() throws IOException, SportMonksException, InterruptedException {
        enqueue("venues.json");

        api.getVenues("19686", "city", "country");

        HttpUrl url = server.takeRequest().getRequestUrl();
        assertEquals("/venues/seasons/19686", url.encodedPath());
        assertEquals("city;country", url.queryParameter("include"));
    }

    @Test
    void getVenues_bySeason_withoutIncludes_omitsTheParameter() throws IOException, SportMonksException, InterruptedException {
        enqueue("venues.json");

        assertFalse(api.getVenues("19686").isEmpty());
        assertNull(server.takeRequest().getRequestUrl().queryParameter("include"));
    }
}
