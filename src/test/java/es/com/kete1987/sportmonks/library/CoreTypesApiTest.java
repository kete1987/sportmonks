package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.my.MyApi;
import es.com.kete1987.sportmonks.library.core.model.my.MyLeague;
import es.com.kete1987.sportmonks.library.core.model.my.MyResource;
import es.com.kete1987.sportmonks.library.core.model.my.MyUsage;
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
    void getAllTypes_returnsParsedList() throws IOException, SportMonksException {
        enqueue("types.json");

        List<Type> types = api.getAllTypes();

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
    void getTypeById_returnsSingleParsed() throws IOException, SportMonksException {
        enqueue("type_detail.json");

        Type type = api.getTypeById(1L);

        assertNotNull(type);
        assertEquals(1L, type.getId());
        assertEquals("Goal", type.getName());
        assertEquals("goal", type.getCode());
    }

    @Test
    void getTypeById_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("type_detail.json");

        api.getTypeById(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("types/1"),
                "Expected path with type id, got: " + request.getPath());
    }

    @Test
    void getTypesByEntity_usesEntitiesPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("types_by_entity.json");

        api.getTypesByEntity("fixtures");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("types/entities/fixtures"),
                "Expected types/entities path, got: " + request.getPath());
    }

    @Test
    void getTypesByEntity_returnsParsedList() throws IOException, SportMonksException {
        enqueue("types_by_entity.json");

        List<Type> types = api.getTypesByEntity("fixtures");

        assertEquals(2, types.size());
        assertEquals("Goal", types.get(0).getName());
    }

    // --- Timezones ---

    @Test
    void getAllTimezones_returnsParsedList() throws IOException, SportMonksException {
        enqueue("timezones.json");

        List<String> timezones = api.getAllTimezones();

        assertEquals(4, timezones.size());
        assertEquals("Africa/Abidjan", timezones.get(0));
        assertEquals("Europe/Madrid", timezones.get(2));
    }

    @Test
    void getAllTimezones_urlContainsTimezones() throws IOException, SportMonksException, InterruptedException {
        enqueue("timezones.json");

        api.getAllTimezones();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("timezones"),
                "Expected timezones path, got: " + request.getPath());
    }

    // --- Filters ---

    @Test
    void getAllEntityFilters_returnsParsedMap() throws IOException, SportMonksException {
        enqueue("filters.json");

        Map<String, List<String>> filters = api.getAllEntityFilters();

        assertNotNull(filters);
        assertTrue(filters.containsKey("fixtures"));
        assertEquals(2, filters.get("fixtures").size());
        assertEquals("fixtureStatisticTypes", filters.get("fixtures").get(0));
        assertTrue(filters.containsKey("leagues"));
    }

    @Test
    void getAllEntityFilters_usesFiltersEntitiesPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("filters.json");

        api.getAllEntityFilters();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("filters/entities"),
                "Expected filters/entities path, got: " + request.getPath());
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
        assertTrue(request.getPath().contains("api"),
                "Expected api path, got: " + request.getPath());
        assertFalse(request.getPath().startsWith("/core/"),
                "Should not use core base, got: " + request.getPath());
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
        assertTrue(request.getPath().contains("leagues"),
                "Expected leagues path, got: " + request.getPath());
    }

    @Test
    void getMyEnrichments_usesEnrichmentsPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("my_enrichments.json");

        api.getMyEnrichments();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("enrichments"),
                "Expected enrichments path, got: " + request.getPath());
    }

    @Test
    void getMyEnrichments_returnsParsedList() throws IOException, SportMonksException {
        enqueue("my_enrichments.json");

        List<String> enrichments = api.getMyEnrichments();

        assertEquals(3, enrichments.size());
        assertEquals("myfixtures", enrichments.get(0));
    }

    @Test
    void getMyResources_usesResourcesPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("my_resources.json");

        api.getMyResources();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("resources"),
                "Expected resources path, got: " + request.getPath());
    }

    @Test
    void getMyResources_returnsParsedList() throws IOException, SportMonksException {
        enqueue("my_resources.json");

        List<MyResource> resources = api.getMyResources();

        assertEquals(2, resources.size());
        assertEquals("fixtures", resources.get(0).getName());
        assertEquals("/v3/football/fixtures", resources.get(0).getEndpoint());
    }

    @Test
    void getMyUsage_usesUsagePath() throws IOException, SportMonksException, InterruptedException {
        enqueue("my_usage.json");

        api.getMyUsage();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("usage"),
                "Expected usage path, got: " + request.getPath());
    }

    @Test
    void getMyUsage_returnsParsedList() throws IOException, SportMonksException {
        enqueue("my_usage.json");

        List<MyUsage> usages = api.getMyUsage();

        assertEquals(2, usages.size());
        assertEquals(1, usages.get(0).getSportId().intValue());
        assertEquals(145, usages.get(0).getRequests().intValue());
        assertEquals("2024-10-26", usages.get(0).getDate());
    }
}
