package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.my.MyEnrichment;
import es.com.kete1987.sportmonks.library.core.model.my.MyLeague;
import es.com.kete1987.sportmonks.library.core.model.my.MyResource;
import es.com.kete1987.sportmonks.library.core.model.my.MyUsage;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import es.com.kete1987.sportmonks.library.core.model.type.TypeEntity;
import okhttp3.mockwebserver.MockResponse;
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
    void getTypesByEntity_matchesTheEntityIgnoringCase() throws IOException, SportMonksException {
        enqueue("types_entities.json");

        List<Type> types = api.getTypesByEntity("event");

        assertEquals(2, types.size());
        assertEquals("1st Half", types.get(0).getName());
    }

    @Test
    void getTypesByEntity_unknownEntityGivesEmptyList() throws IOException, SportMonksException {
        enqueue("types_entities.json");

        assertTrue(api.getTypesByEntity("nothing").isEmpty());
    }

    @Test
    void getAllTypeEntities_returnsTheMapKeyedByEntity() throws IOException, SportMonksException {
        enqueue("types_entities.json");

        Map<String, TypeEntity> entities = api.getAllTypeEntities();

        assertEquals(2, entities.size());
        assertEquals("2026-11-20T20:01:25.000000Z", entities.get("Event").getUpdatedAt());
        assertEquals("SUBSTITUTIONS", entities.get("CoachStatisticDetail").getTypes().get(0).getDeveloperName());
    }

    @Test
    void getAllTypeEntities_emptyBody_returnsEmptyMap() throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(""));

        assertTrue(api.getAllTypeEntities().isEmpty());
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
        assertTrue(filters.containsKey("fixture"));
        assertEquals(2, filters.get("fixture").size());
        assertEquals("fixtureLeagues", filters.get("fixture").get(0));
        assertTrue(filters.containsKey("league"));
    }

    @Test
    void getAllEntityFilters_usesMyFiltersEntityPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("filters.json");

        api.getAllEntityFilters();

        RecordedRequest request = server.takeRequest();
        assertEquals("/filters/entity", request.getRequestUrl().encodedPath());
    }

    // --- MySportmonks ---

    @Test
    void getMyLeagues_returnsParsedList() throws IOException, SportMonksException {
        enqueue("my_leagues.json");

        List<MyLeague> leagues = api.getMyLeagues();

        assertEquals(2, leagues.size());
        assertEquals(2L, leagues.get(0).getId());
        assertEquals("Champions League", leagues.get(0).getName());
        assertEquals("cup_international", leagues.get(0).getSubType());
        assertEquals(8L, leagues.get(1).getId());
        assertEquals("Premier League", leagues.get(1).getName());
    }

    @Test
    void getMyLeagues_readsCountryActiveShortCodeCategoryJerseysAndSport() throws IOException, SportMonksException {
        enqueue("my_leagues.json");

        MyLeague league = api.getMyLeagues().get(1);

        assertEquals(462L, league.getCountryId());
        assertEquals(Boolean.TRUE, league.getActive());
        assertEquals("UK PL", league.getShortCode());
        assertEquals(1, league.getCategory());
        assertEquals(Boolean.FALSE, league.getHasJerseys());
        assertEquals("Football", league.getSport().getName());
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

        List<MyEnrichment> enrichments = api.getMyEnrichments();

        assertEquals(2, enrichments.size());
        assertEquals(90L, enrichments.get(0).getId());
        assertEquals("Access odds", enrichments.get(0).getName());
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
        assertEquals(244L, resources.get(0).getId());
        assertEquals("Types by Entity", resources.get(0).getDescription());
    }

    @Test
    void getMyUsage_usesUsagePath() throws IOException, SportMonksException, InterruptedException {
        enqueue("my_usage_page2.json");

        api.getMyUsage();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("usage"),
                "Expected usage path, got: " + request.getPath());
    }

    @Test
    void getMyUsage_returnsParsedRows() throws IOException, SportMonksException {
        enqueue("my_usage_page1.json");
        enqueue("my_usage_page2.json");

        List<MyUsage> usages = api.getMyUsage();

        assertEquals(3, usages.size());
        assertEquals(2819442181L, usages.get(0).getId());
        assertEquals("api.sports.standings.season", usages.get(0).getEndpoint());
        assertEquals(5, usages.get(0).getCount());
        assertEquals("standing", usages.get(0).getEntity());
        assertEquals(2992, usages.get(0).getRemainingRequests());
        assertEquals(1795358400L, usages.get(0).getPeriodStart());
        assertEquals(1795358700L, usages.get(0).getPeriodEnd());
    }

    @Test
    void getMyUsage_withLimit_stopsEarlyAndSetsPerPage() throws IOException, SportMonksException, InterruptedException {
        // Only the first page is enqueued: if the limit didn't stop the walk the mock would block.
        enqueue("my_usage_page1.json");

        List<MyUsage> usages = api.getMyUsage(2);

        assertEquals(2, usages.size());
        assertEquals("2", server.takeRequest().getRequestUrl().queryParameter("per_page"));
    }
}
