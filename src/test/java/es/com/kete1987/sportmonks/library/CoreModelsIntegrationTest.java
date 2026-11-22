package es.com.kete1987.sportmonks.library;

import com.google.gson.JsonElement;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.city.City;
import es.com.kete1987.sportmonks.library.core.model.continent.Continent;
import es.com.kete1987.sportmonks.library.core.model.country.Country;
import es.com.kete1987.sportmonks.library.core.model.my.MyLeague;
import es.com.kete1987.sportmonks.library.core.model.my.MyResource;
import es.com.kete1987.sportmonks.library.core.model.my.MyUsage;
import es.com.kete1987.sportmonks.library.core.model.region.Region;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Audit of the core and MySportmonks endpoints against the real API (issue #77): routes that answer 404
 * and models that do not declare every key of the real JSON (Gson drops unknown fields without a trace).
 *
 * Skipped unless {@code SPORTMONKS_API_KEY} is set; run with {@code mvn test -Pintegration}.
 */
class CoreModelsIntegrationTest {
    private static final String COUNTRY_WITH_INCLUDES = "core/countries/462?include=continent;regions";
    private static final String MY_USAGE = "my/usage?per_page=5";

    private static String key;
    private static SportMonksAPI api;

    @BeforeAll
    static void setUp() {
        key = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(key != null && !key.isEmpty(), "SPORTMONKS_API_KEY not set");
        api = new SportMonksAPI(key);
    }

    private static void assertCovered(String path, Class<?> model) throws IOException, InterruptedException {
        JsonElement data = JsonCoverage.fetchData(key, path);
        List<String> missing = JsonCoverage.missing(data, model);
        assertTrue(missing.isEmpty(), model.getSimpleName() + " does not model: " + missing);
    }

    // ---- routes ----

    @Test
    void typesOfAnEntityComeFromTheRealRoute() throws IOException, SportMonksException {
        List<Type> types = api.getTypesByEntity("Event");

        assertFalse(types.isEmpty(), "core/types/entities lists types for Event");
    }

    // ---- public methods that threw or returned nulls ----

    @Test
    void myEnrichmentsDeserialize() throws IOException, SportMonksException {
        var enrichments = api.getMyEnrichments();

        assertFalse(enrichments.isEmpty());
    }

    @Test
    void myLeaguesCarryTheirId() throws IOException, SportMonksException {
        List<MyLeague> leagues = api.getMyLeagues();

        assertFalse(leagues.isEmpty());
        assertTrue(leagues.stream().allMatch(l -> l.getLeagueId() != null), "every league has an id");
    }

    @Test
    void myResourcesCarryTheirData() throws IOException, SportMonksException {
        List<MyResource> resources = api.getMyResources();

        assertFalse(resources.isEmpty());
        assertTrue(resources.stream().anyMatch(r -> r.getName() != null), "some resource has a description");
    }

    // ---- raw JSON vs POJO ----

    @Test
    void myLeagueModelsEveryField() throws IOException, InterruptedException {
        assertCovered("my/leagues?per_page=5", MyLeague.class);
    }

    @Test
    void myResourceModelsEveryField() throws IOException, InterruptedException {
        assertCovered("my/resources", MyResource.class);
    }

    @Test
    void myUsageModelsEveryField() throws IOException, InterruptedException {
        assertCovered(MY_USAGE, MyUsage.class);
    }

    @Test
    void subscriptionModelsEveryField() throws IOException, InterruptedException {
        JsonElement subscription = JsonCoverage.fetchMember(key, "core/types/1", "subscription");
        List<String> missing = JsonCoverage.missing(subscription, Subscription.class);

        assertTrue(missing.isEmpty(), "Subscription does not model: " + missing);
    }

    @Test
    void countryModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered(COUNTRY_WITH_INCLUDES, Country.class);
    }

    @Test
    void cityModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("core/cities/1?include=region", City.class);
    }

    @Test
    void regionModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("core/regions/1?include=cities;country", Region.class);
    }

    @Test
    void continentModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("core/continents/1?include=countries", Continent.class);
    }

    @Test
    void typeModelsEveryField() throws IOException, InterruptedException {
        assertCovered("core/types/1", Type.class);
    }
}
