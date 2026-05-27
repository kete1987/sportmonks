package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.coach.Coach;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoachApiTest extends BaseApiTest {

    @Test
    void getAllCoaches_usesCoachesPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("coaches.json");

        api.getAllCoaches();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("coaches"));
        assertFalse(request.getPath().contains("coaches/"));
    }

    @Test
    void getAllCoaches_returnsParsedList() throws IOException, SportMonksException {
        enqueue("coaches.json");

        List<Coach> coaches = api.getAllCoaches();

        assertEquals(2, coaches.size());
        Coach first = coaches.get(0);
        assertEquals(1L, first.getId());
        assertEquals("Erik ten Hag", first.getName());
        assertEquals("Erik", first.getFirstname());
        assertEquals("ten Hag", first.getLastname());
        assertEquals("E. ten Hag", first.getDisplayName());
        assertEquals("male", first.getGender());
        assertEquals(1L, first.getSportId());
        assertEquals(462L, first.getCountryId());
        assertEquals(462L, first.getNationalityId());
        assertEquals("1970-02-02", first.getDateOfBirth());
    }

    @Test
    void getCoachById_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("coach_detail.json");

        api.getCoachById(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("coaches/1"));
    }

    @Test
    void getCoachById_returnsParsed() throws IOException, SportMonksException {
        enqueue("coach_detail.json");

        Coach coach = api.getCoachById(1L);

        assertNotNull(coach);
        assertEquals(1L, coach.getId());
        assertEquals("Erik ten Hag", coach.getName());
    }

    @Test
    void getCoachesByCountry_usesCountryPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("coaches.json");

        api.getCoachesByCountry(462L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("coaches/countries/462"));
    }

    @Test
    void getCoachesByCountry_returnsParsedList() throws IOException, SportMonksException {
        enqueue("coaches.json");

        List<Coach> coaches = api.getCoachesByCountry(462L);

        assertEquals(2, coaches.size());
    }

    @Test
    void searchCoaches_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("coaches.json");

        api.searchCoaches("Guardiola");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("coaches/search/Guardiola"));
    }

    @Test
    void searchCoaches_returnsParsedList() throws IOException, SportMonksException {
        enqueue("coaches.json");

        List<Coach> coaches = api.searchCoaches("Guardiola");

        assertEquals(2, coaches.size());
    }

    @Test
    void getLatestUpdatedCoaches_usesLatestPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("coaches.json");

        api.getLatestUpdatedCoaches();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("coaches/latest"));
    }

    @Test
    void getLatestUpdatedCoaches_returnsParsedList() throws IOException, SportMonksException {
        enqueue("coaches.json");

        List<Coach> coaches = api.getLatestUpdatedCoaches();

        assertEquals(2, coaches.size());
    }
}
