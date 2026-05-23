package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.referee.Referee;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RefereeApiTest extends BaseApiTest {

    @Test
    void getAllReferees_usesRefereesPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("referees.json");

        api.getAllReferees();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("referees"));
        assertFalse(request.getPath().contains("referees/"));
    }

    @Test
    void getAllReferees_returnsParsedList() throws IOException, SportMonksException {
        enqueue("referees.json");

        List<Referee> referees = api.getAllReferees();

        assertEquals(2, referees.size());
        assertEquals(1L, referees.get(0).getId());
        assertEquals("Michael Oliver", referees.get(0).getName());
        assertEquals("M. Oliver", referees.get(0).getDisplayName());
        assertEquals("male", referees.get(0).getGender());
        assertEquals("1985-03-25", referees.get(0).getDateOfBirth());
    }

    @Test
    void getRefereeById_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("referee_detail.json");

        api.getRefereeById(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("referees/1"));
    }

    @Test
    void getRefereeById_returnsParsed() throws IOException, SportMonksException {
        enqueue("referee_detail.json");

        Referee referee = api.getRefereeById(1L);

        assertNotNull(referee);
        assertEquals(1L, referee.getId());
        assertEquals("Michael Oliver", referee.getName());
    }

    @Test
    void getRefereesByCountry_usesCountryPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("referees.json");

        api.getRefereesByCountry(462L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("referees/countries/462"));
    }

    @Test
    void getRefereesByCountry_returnsParsedList() throws IOException, SportMonksException {
        enqueue("referees.json");

        List<Referee> referees = api.getRefereesByCountry(462L);

        assertEquals(2, referees.size());
    }

    @Test
    void getRefereesBySeason_usesSeasonPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("referees.json");

        api.getRefereesBySeason(23614L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("referees/seasons/23614"));
    }

    @Test
    void getRefereesBySeason_returnsParsedList() throws IOException, SportMonksException {
        enqueue("referees.json");

        List<Referee> referees = api.getRefereesBySeason(23614L);

        assertEquals(2, referees.size());
    }

    @Test
    void searchReferees_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("referees.json");

        api.searchReferees("Oliver");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("referees/search/Oliver"));
    }

    @Test
    void searchReferees_returnsParsedList() throws IOException, SportMonksException {
        enqueue("referees.json");

        List<Referee> referees = api.searchReferees("Oliver");

        assertEquals(2, referees.size());
    }
}
