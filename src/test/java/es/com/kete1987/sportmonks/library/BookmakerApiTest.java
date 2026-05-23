package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.Bookmaker;
import es.com.kete1987.sportmonks.library.odds.model.BookmakerMapping;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookmakerApiTest extends BaseApiTest {

    @Test
    void getAllBookmakers_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("bookmakers.json");

        api.getAllBookmakers();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("bookmakers"));
    }

    @Test
    void getAllBookmakers_returnsParsedList() throws IOException, SportMonksException {
        enqueue("bookmakers.json");

        List<Bookmaker> bookmakers = api.getAllBookmakers();

        assertEquals(2, bookmakers.size());
        assertEquals(1L, bookmakers.get(0).getId());
        assertEquals(10L, bookmakers.get(0).getLegacyId());
        assertEquals("Bet365", bookmakers.get(0).getName());
        assertEquals("Bet 365", bookmakers.get(0).getDisplayName());
        assertTrue(bookmakers.get(0).getHasStreaming());
        assertFalse(bookmakers.get(0).getIsPremium());
    }

    @Test
    void getBookmakerById_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("bookmaker_detail.json");

        api.getBookmakerById(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("bookmakers/1"));
    }

    @Test
    void getBookmakerById_returnsParsedBookmaker() throws IOException, SportMonksException {
        enqueue("bookmaker_detail.json");

        Bookmaker bookmaker = api.getBookmakerById(1L);

        assertNotNull(bookmaker);
        assertEquals(1L, bookmaker.getId());
        assertEquals("Bet365", bookmaker.getName());
    }

    @Test
    void searchBookmakers_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("bookmakers.json");

        api.searchBookmakers("Bet");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("bookmakers/search/Bet"));
    }

    @Test
    void searchBookmakers_returnsParsedList() throws IOException, SportMonksException {
        enqueue("bookmakers.json");

        List<Bookmaker> bookmakers = api.searchBookmakers("Bet");

        assertFalse(bookmakers.isEmpty());
    }

    @Test
    void getBookmakerMappingsByFixture_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("bookmaker_mapping.json");

        api.getBookmakerMappingsByFixture(100L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("bookmakers/mapping/fixtures/100"));
    }

    @Test
    void getBookmakerMappingsByFixture_returnsParsedList() throws IOException, SportMonksException {
        enqueue("bookmaker_mapping.json");

        List<BookmakerMapping> mappings = api.getBookmakerMappingsByFixture(100L);

        assertEquals(2, mappings.size());
        assertEquals(100L, mappings.get(0).getFixtureId());
        assertEquals(1L, mappings.get(0).getBookmakerId());
        assertEquals("BET365_FIXTURE_100", mappings.get(0).getFixtureIdentifier());
    }
}
