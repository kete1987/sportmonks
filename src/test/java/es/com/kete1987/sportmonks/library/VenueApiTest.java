package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VenueApiTest extends BaseApiTest {

    @Test
    void getAllVenues_usesVenuesPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("venues.json");

        api.getAllVenues();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("venues"));
        assertFalse(request.getPath().contains("venues/"));
        assertFalse(request.getPath().contains("seasons"));
    }

    @Test
    void getAllVenues_returnsParsedList() throws IOException, SportMonksException {
        enqueue("venues.json");

        List<Venue> venues = api.getAllVenues();

        assertEquals(2, venues.size());
        assertEquals(6001L, venues.get(0).getId());
        assertEquals("Old Trafford", venues.get(0).getName());
        assertEquals(76000L, venues.get(0).getCapacity());
    }

    @Test
    void getVenueById_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("venue_detail.json");

        api.getVenueById(6001L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("venues/6001"));
    }

    @Test
    void getVenueById_returnsParsed() throws IOException, SportMonksException {
        enqueue("venue_detail.json");

        Venue venue = api.getVenueById(6001L);

        assertNotNull(venue);
        assertEquals(6001L, venue.getId());
        assertEquals("Old Trafford", venue.getName());
    }

    @Test
    void searchVenues_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("venues.json");

        api.searchVenues("Trafford");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("venues/search/Trafford"));
    }

    @Test
    void searchVenues_returnsParsedList() throws IOException, SportMonksException {
        enqueue("venues.json");

        List<Venue> venues = api.searchVenues("Trafford");

        assertEquals(2, venues.size());
    }
}
