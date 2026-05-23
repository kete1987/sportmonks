package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PremiumOddsApiTest extends BaseApiTest {

    @Test
    void getPremiumPreMatchOddsByFixture_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumPreMatchOddsByFixture(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("pre-match/fixtures/1/premium"));
        assertFalse(request.getPath().contains("bookmakers"));
        assertFalse(request.getPath().contains("markets"));
    }

    @Test
    void getPremiumPreMatchOddsByFixture_returnsParsedList() throws IOException, SportMonksException {
        enqueue("odds.json");

        List<Odd> odds = api.getPremiumPreMatchOddsByFixture(1L);

        assertNotNull(odds);
    }

    @Test
    void getPremiumPreMatchOddsByFixtureAndBookmaker_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumPreMatchOddsByFixtureAndBookmaker(1L, 2L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("pre-match/fixtures/1/bookmakers/2/premium"));
    }

    @Test
    void getPremiumPreMatchOddsByFixtureAndMarket_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumPreMatchOddsByFixtureAndMarket(1L, 3L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("pre-match/fixtures/1/markets/3/premium"));
    }

    @Test
    void getLastUpdatedPremiumPreMatchOdds_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getLastUpdatedPremiumPreMatchOdds();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("pre-match/lastupdated/premium"));
    }

    @Test
    void getPremiumInplayOddsByFixture_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumInplayOddsByFixture(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("inplay/fixtures/1/premium"));
        assertFalse(request.getPath().contains("bookmakers"));
        assertFalse(request.getPath().contains("markets"));
    }

    @Test
    void getPremiumInplayOddsByFixtureAndBookmaker_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumInplayOddsByFixtureAndBookmaker(1L, 2L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("inplay/fixtures/1/bookmakers/2/premium"));
    }

    @Test
    void getPremiumInplayOddsByFixtureAndMarket_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getPremiumInplayOddsByFixtureAndMarket(1L, 3L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("inplay/fixtures/1/markets/3/premium"));
    }

    @Test
    void getLastUpdatedPremiumInplayOdds_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("odds.json");

        api.getLastUpdatedPremiumInplayOdds();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("inplay/lastupdated/premium"));
    }
}
