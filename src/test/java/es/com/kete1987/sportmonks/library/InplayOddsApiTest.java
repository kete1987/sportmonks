package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InplayOddsApiTest extends BaseApiTest {

    @Test
    void getInplayOddsByFixture_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getInplayOddsByFixture(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("inplay/fixtures/1"));
        assertFalse(request.getPath().contains("bookmakers"));
        assertFalse(request.getPath().contains("markets"));
    }

    @Test
    void getInplayOddsByFixture_returnsParsedList() throws IOException, SportMonksException {
        enqueue("odds.json");

        List<Odd> odds = api.getInplayOddsByFixture(1L);

        assertNotNull(odds);
    }

    @Test
    void getInplayOddsByFixtureAndBookmaker_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getInplayOddsByFixtureAndBookmaker(1L, 2L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("inplay/fixtures/1/bookmakers/2"));
    }

    @Test
    void getInplayOddsByFixtureAndMarket_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getInplayOddsByFixtureAndMarket(1L, 3L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("inplay/fixtures/1/markets/3"));
    }

    @Test
    void getLastUpdatedInplayOdds_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getLastUpdatedInplayOdds();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("inplay/lastupdated"));
    }
}
