package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.my.MyApi;
import es.com.kete1987.sportmonks.library.core.model.my.MyLeague;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CoreTypesApiTest extends BaseApiTest {

    // --- Types ---

    @Test
    void getTypes_returnsParsedList() throws IOException, SportMonksException {
        enqueue("types.json");

        List<Type> types = api.getTypes();

        assertEquals(2, types.size());
        assertEquals(1L, types.get(0).getId());
        assertEquals("Goal", types.get(0).getName());
        assertEquals("goal", types.get(0).getCode());
        assertEquals("GOAL", types.get(0).getDeveloperName());
        assertEquals("sport", types.get(0).getModelType());
        assertEquals(2L, types.get(1).getId());
        assertEquals("Yellow Card", types.get(1).getName());
    }

    @Test
    void getType_returnsSingleParsed() throws IOException, SportMonksException {
        enqueue("type_detail.json");

        Type type = api.getType("1");

        assertNotNull(type);
        assertEquals(1L, type.getId());
        assertEquals("Goal", type.getName());
        assertEquals("goal", type.getCode());
    }

    @Test
    void getType_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("type_detail.json");

        api.getType("1");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("types/1"),
                "Expected path with type id, got: " + request.getPath());
    }

    // --- Timezones ---

    @Test
    void getTimezones_returnsParsedList() throws IOException, SportMonksException {
        enqueue("timezones.json");

        List<String> timezones = api.getTimezones();

        assertEquals(4, timezones.size());
        assertEquals("Africa/Abidjan", timezones.get(0));
        assertEquals("Europe/Madrid", timezones.get(2));
    }

    @Test
    void getTimezones_urlContainsTimezones() throws IOException, SportMonksException, InterruptedException {
        enqueue("timezones.json");

        api.getTimezones();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("timezones"),
                "Expected timezones path, got: " + request.getPath());
    }

    // --- Filters ---

    @Test
    void getFilters_returnsParsedMap() throws IOException, SportMonksException {
        enqueue("filters.json");

        Map<String, List<String>> filters = api.getFilters();

        assertNotNull(filters);
        assertTrue(filters.containsKey("fixtures"));
        assertEquals(2, filters.get("fixtures").size());
        assertEquals("fixtureStatisticTypes", filters.get("fixtures").get(0));
        assertTrue(filters.containsKey("leagues"));
    }

    // --- MySportmonks ---

    @Test
    void getMyApi_returnsParsed() throws IOException, SportMonksException {
        enqueue("my_api.json");

        MyApi myApi = api.getMyApi();

        assertNotNull(myApi);
        assertEquals(1, myApi.getSportId());
        assertEquals("UTC", myApi.getTimezone());
    }

    @Test
    void getMyApi_urlContainsMyApi() throws IOException, SportMonksException, InterruptedException {
        enqueue("my_api.json");

        api.getMyApi();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("my/api"),
                "Expected my/api path, got: " + request.getPath());
    }

    @Test
    void getMyLeagues_returnsParsedList() throws IOException, SportMonksException {
        enqueue("my_leagues.json");

        List<MyLeague> leagues = api.getMyLeagues();

        assertEquals(2, leagues.size());
        assertEquals(271L, leagues.get(0).getLeagueId());
        assertEquals("Superliga", leagues.get(0).getName());
        assertEquals("domestic", leagues.get(0).getType());
        assertEquals(8L, leagues.get(1).getLeagueId());
        assertEquals("Premier League", leagues.get(1).getName());
    }

    @Test
    void getMyLeagues_urlContainsMyLeagues() throws IOException, SportMonksException, InterruptedException {
        enqueue("my_leagues.json");

        api.getMyLeagues();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("my/leagues"),
                "Expected my/leagues path, got: " + request.getPath());
    }
}
