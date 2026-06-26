package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Verifies that the optional client-wide {@code timezone} is forwarded as the {@code timezone}
 * query parameter on requests across the different sub-APIs.
 */
class TimezoneParamTest extends BaseApiTest {

    private SportMonksAPI apiWithTimezone(String timezone) {
        String base = server.url("/").toString();
        return new SportMonksAPI(new OkHttpClient(), base, base, base, base, null, timezone);
    }

    @Test
    void timezone_isAppended_whenConfigured() throws IOException, SportMonksException, InterruptedException {
        enqueue("coaches.json");

        apiWithTimezone("Europe/Amsterdam").getFootball().getAllCoaches();

        RecordedRequest request = server.takeRequest();
        assertEquals("Europe/Amsterdam", request.getRequestUrl().queryParameter("timezone"));
    }

    @Test
    void timezone_isNotAppended_whenNull() throws IOException, SportMonksException, InterruptedException {
        enqueue("coaches.json");

        // Default api from BaseApiTest is built without a timezone.
        api.getFootball().getAllCoaches();

        RecordedRequest request = server.takeRequest();
        assertNull(request.getRequestUrl().queryParameter("timezone"));
    }

    @Test
    void timezone_isAppended_onCoreEndpoints() throws IOException, SportMonksException, InterruptedException {
        enqueue("timezones.json");

        apiWithTimezone("America/New_York").getCore().getAllTimezones();

        RecordedRequest request = server.takeRequest();
        assertEquals("America/New_York", request.getRequestUrl().queryParameter("timezone"));
    }
}
