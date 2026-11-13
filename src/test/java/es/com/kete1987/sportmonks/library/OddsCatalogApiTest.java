package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.Constants;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OddsCatalogApiTest extends BaseApiTest {

    @ParameterizedTest
    @ValueSource(strings = {"markets", "bookmakers"})
    void standaloneCatalog_keepsRoutingAuthAndOptionsAcrossPages(String catalog)
            throws IOException, SportMonksException, InterruptedException {
        OddsApi odds = new OddsApi(SportMonksApiBase.buildHttpClient("test-token"),
                server.url(HttpUrl.parse(Constants.BASE_URL_ODDS).encodedPath()).toString(),
                server.url(HttpUrl.parse(Constants.BASE_URL_ODDS_CATALOG).encodedPath()).toString(),
                "es", "Europe/Madrid", new RateLimitTracker());
        enqueue("cursor_page1.json");
        enqueue(catalog + ".json");

        assertEquals(4, "markets".equals(catalog) ? odds.getAllMarkets().size() : odds.getAllBookmakers().size());

        for (int page = 0; page < 2; page++) {
            RecordedRequest request = server.takeRequest();
            HttpUrl url = request.getRequestUrl();
            assertEquals("/v3/odds/" + catalog, url.encodedPath());
            assertEquals("test-token", request.getHeader("Authorization"));
            assertNull(url.queryParameter("api_token"));
            assertEquals("es", url.queryParameter("locale"));
            assertEquals("Europe/Madrid", url.queryParameter("timezone"));
            assertEquals(page == 0 ? null : "Y1VSU09SLVBBR0UtMg", url.queryParameter("cursor"));
        }
    }
}
