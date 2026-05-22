package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.Market;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketApiTest extends BaseApiTest {

    @Test
    void getAllMarkets_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("markets.json");

        api.getAllMarkets();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("markets"));
    }

    @Test
    void getAllMarkets_returnsParsedList() throws IOException, SportMonksException {
        enqueue("markets.json");

        List<Market> markets = api.getAllMarkets();

        assertEquals(2, markets.size());
        assertEquals(1L, markets.get(0).getId());
        assertEquals("3Way Result", markets.get(0).getName());
        assertEquals("1X2", markets.get(0).getDisplayName());
        assertTrue(markets.get(0).getHasWinningCalculations());
        assertEquals("3way_result", markets.get(0).getDeveloperName());
    }

    @Test
    void getMarketById_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("market_detail.json");

        api.getMarketById(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("markets/1"));
    }

    @Test
    void getMarketById_returnsParsedMarket() throws IOException, SportMonksException {
        enqueue("market_detail.json");

        Market market = api.getMarketById(1L);

        assertNotNull(market);
        assertEquals(1L, market.getId());
        assertEquals("3Way Result", market.getName());
    }

    @Test
    void searchMarkets_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("markets.json");

        api.searchMarkets("Result");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("markets/search/Result"));
    }

    @Test
    void searchMarkets_returnsParsedList() throws IOException, SportMonksException {
        enqueue("markets.json");

        List<Market> markets = api.searchMarkets("Result");

        assertFalse(markets.isEmpty());
    }
}
