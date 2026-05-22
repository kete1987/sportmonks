package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OddsApiTest extends BaseApiTest {

    @Test
    void getMatchOdds_returnsParsedList() throws IOException, SportMonksException {
        enqueue("odds.json");

        List<Odd> odds = api.getMatchOdds("1");

        assertEquals(2, odds.size());
        assertEquals(101, odds.get(0).getId().intValue());
        assertEquals("1", odds.get(0).getLabel());
        assertEquals("2.50", odds.get(0).getValue());
        assertEquals(102, odds.get(1).getId().intValue());
        assertEquals("X", odds.get(1).getLabel());
    }

    @Test
    void getMatchOdds_urlUsesOddsBaseNotFootball() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getMatchOdds("1");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("pre-match/fixtures/1"),
                "Expected odds URL path, got: " + request.getPath());
    }

    @Test
    void getMatchOddsByBookmaker_buildsCorrectUrl() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getMatchOddsByBookmaker("1", "5");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("fixtures/1/bookmakers/5"),
                "Expected bookmaker URL path, got: " + request.getPath());
    }

    @Test
    void getMatchOddsByMarket_buildsCorrectUrl() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getMatchOddsByMarket("1", "1");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("fixtures/1/markets/1"),
                "Expected market URL path, got: " + request.getPath());
    }
}
