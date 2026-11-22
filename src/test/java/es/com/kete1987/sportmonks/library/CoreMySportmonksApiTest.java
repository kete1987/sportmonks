package es.com.kete1987.sportmonks.library;

import com.google.gson.JsonParser;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.my.MyUsage;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Core and MySportmonks fixes of issue #77, checked with fixtures shaped like the real responses. */
class CoreMySportmonksApiTest extends BaseApiTest {

    private static final String SUBSCRIPTION = "{\"meta\":{\"trial_ends_at\":\"2017-05-23 17:15:56\","
            + "\"ends_at\":\"2026-04-03 10:29:01\",\"current_timestamp\":1795358826},"
            + "\"plans\":[{\"plan\":\"Custom Plan\",\"sport\":\"Football\",\"category\":\"Custom\"}],"
            + "\"add_ons\":[],\"widgets\":[]}";

    @Test
    void getTypesByEntity_usesTheEntitiesRouteWithoutSegment() throws IOException, SportMonksException,
            InterruptedException {
        enqueue("types_entities.json");

        List<Type> types = api.getTypesByEntity("Event");

        assertEquals("/types/entities", server.takeRequest().getRequestUrl().encodedPath());
        assertEquals(2, types.size());
    }

    @Test
    void getMyEnrichments_readsIdAndNameObjects() throws IOException, SportMonksException {
        enqueue("my_enrichments_objects.json");

        var enrichments = api.getMyEnrichments();

        assertEquals(2, enrichments.size());
    }

    @Test
    void getMyUsage_walksEveryPage() throws IOException, SportMonksException {
        enqueue("my_usage_page1.json");
        enqueue("my_usage_page2.json");

        List<MyUsage> usage = api.getMyUsage();

        assertEquals(3, usage.size());
    }

    @Test
    void getMyApi_wasRemovedBecauseTheRouteDoesNotExist() {
        assertFalse(hasMethod(CoreApi.class, "getMyApi"), "CoreApi.getMyApi");
        assertFalse(hasMethod(SportMonksAPI.class, "getMyApi"), "SportMonksAPI.getMyApi");
    }

    @Test
    void subscription_modelsEveryKeyOfTheRealResponse() {
        List<String> missing = JsonCoverage.missing(JsonParser.parseString(SUBSCRIPTION), Subscription.class);

        assertTrue(missing.isEmpty(), "Subscription does not model: " + missing);
    }

    private static boolean hasMethod(Class<?> type, String name) {
        return Arrays.stream(type.getMethods()).anyMatch(m -> m.getName().equals(name));
    }
}
