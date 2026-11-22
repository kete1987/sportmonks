package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Routes and null-safety of {@link PredictionsApi} (#79), with no network. */
class PredictionsRoutesApiTest extends BaseApiTest {

    private static final String EMPTY_BODY = "";
    private static final String BODY_WITHOUT_DATA = "{\"rate_limit\":{\"remaining\":1}}";

    @FunctionalInterface
    interface ListCall {
        List<?> on(PredictionsApi api) throws IOException, SportMonksException;
    }

    @Test
    void predictabilityByLeague_usesThePredictabilityRoute() throws IOException, SportMonksException, InterruptedException {
        enqueue("predictability.json");

        api.getPredictabilityByLeague(271L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("/predictions/predictability/leagues/271"), request.getPath());
    }

    @Test
    void expectedGoalsByFixture_usesTheFixturesRoute() throws IOException, SportMonksException, InterruptedException {
        enqueue("expected_goals.json");

        api.getExpectedGoalsByFixture();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("/expected/fixtures"), request.getPath());
    }

    @Test
    void expectedGoalsByLineup_usesTheLineupsRoute() throws IOException, SportMonksException, InterruptedException {
        enqueue("expected_goals.json");

        api.getExpectedGoalsByLineup();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("/expected/lineups"), request.getPath());
    }

    static Stream<Arguments> listMethods() {
        return Stream.of(
                call("getAllProbabilities", a -> a.getAllProbabilities()),
                call("getPredictabilityByLeague", a -> a.getPredictabilityByLeague(1L)),
                call("getProbabilitiesByFixture", a -> a.getProbabilitiesByFixture(1L)),
                call("getAllValueBets", a -> a.getAllValueBets()),
                call("getValueBetsByFixture", a -> a.getValueBetsByFixture(1L)),
                call("getExpectedGoalsByFixture", a -> a.getExpectedGoalsByFixture()),
                call("getExpectedGoalsByLineup", a -> a.getExpectedGoalsByLineup()),
                call("getAllPreMatchNews", a -> a.getAllPreMatchNews()),
                call("getPreMatchNewsBySeason", a -> a.getPreMatchNewsBySeason(1L)),
                call("getPreMatchNewsForUpcoming", a -> a.getPreMatchNewsForUpcoming()),
                call("getAllPostMatchNews", a -> a.getAllPostMatchNews()),
                call("getPostMatchNewsBySeason", a -> a.getPostMatchNewsBySeason(1L)));
    }

    private static Arguments call(String name, ListCall call) {
        return Arguments.of(name, call);
    }

    @ParameterizedTest(name = "{0}: empty body gives an empty list")
    @MethodSource("listMethods")
    void emptyBody_givesEmptyList(String name, ListCall call) throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(EMPTY_BODY));

        List<?> result = call.on(api.getPredictions());

        assertNotNull(result, name);
        assertTrue(result.isEmpty(), name);
    }

    @ParameterizedTest(name = "{0}: body without data gives an empty list")
    @MethodSource("listMethods")
    void bodyWithoutData_givesEmptyList(String name, ListCall call) throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(BODY_WITHOUT_DATA));

        List<?> result = call.on(api.getPredictions());

        assertNotNull(result, name);
        assertEquals(0, result.size(), name);
    }
}
