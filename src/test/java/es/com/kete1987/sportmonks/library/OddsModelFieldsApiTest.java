package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.BookmakerMapping;
import es.com.kete1987.sportmonks.library.odds.model.Market;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

/** Values the odds models expose once parsed from real JSON (#78). */
class OddsModelFieldsApiTest extends BaseApiTest {

    @Test
    void bookmakerMapping_exposesTheRealFields() throws IOException, SportMonksException {
        enqueue("bookmaker_mapping_real.json");

        List<BookmakerMapping> mappings = api.getBookmakerMappingsByFixture(18538184L);

        assertEquals(2, mappings.size());
        assertEquals(18538184L, mappings.get(0).getFixtureId());
        assertEquals(2L, mappings.get(0).getBookmakerId());
        assertEquals("bet365", mappings.get(0).getBookmakerName());
        assertEquals("130037617", mappings.get(0).getBookmakerEventId());
        assertNull(mappings.get(0).getBookmakerEventUrl());
        assertEquals("22Bet", mappings.get(1).getBookmakerName());
        assertEquals("https://22bet.com/en/live/Football/12821-France-Ligue-1/424202506-AC-Ajaccio-Stade-de-Reims",
                mappings.get(1).getBookmakerEventUrl());
    }

    @Test
    void market_exposesLegacyId() throws IOException, SportMonksException {
        enqueue("market_real.json");

        Market market = api.getMarketById(2L);

        assertEquals(63L, market.getLegacyId());
        assertFalse(market.getHasWinningCalculations());
    }

    @Test
    void odd_exposesIncludedMarketBookmakerAndFixture() throws IOException, SportMonksException {
        enqueue("odds_included.json");

        Odd odd = api.getMatchOdds("19722082", "market", "bookmaker", "fixture").get(0);

        assertEquals(254835558627L, odd.getId());
        assertEquals("FULLTIME_RESULT", odd.getMarket().getDeveloperName());
        assertEquals(1L, odd.getMarket().getLegacyId());
        assertEquals("bet365", odd.getBookmaker().getName());
        assertEquals("Aston Villa vs Everton", odd.getFixture().getName());
        assertEquals(19722082L, odd.getFixture().getId());
    }
}
