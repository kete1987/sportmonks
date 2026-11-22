package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Checks of issue #79 against the real API. The test subscription does not cover most of
 * predictions/expected, so a 403 means "the route exists" and is accepted; a 404 (missing
 * route) or 422 fails. Models cannot be validated here for the same reason.
 *
 * <p>Skipped unless {@code SPORTMONKS_API_KEY} is set; run with {@code mvn test -Pintegration}.
 */
class PredictionsRoutesIntegrationTest {

    private static PredictionsApi api;

    @FunctionalInterface
    interface RouteCall {
        void on(PredictionsApi api) throws IOException, SportMonksException;
    }

    @BeforeAll
    static void setUp() {
        String key = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(key != null && !key.isEmpty(), "SPORTMONKS_API_KEY not set");
        api = new PredictionsApi(key);
    }

    static Stream<Arguments> routes() {
        return Stream.of(
                route("getAllProbabilities", a -> a.getAllProbabilities()),
                route("getPredictabilityByLeague", a -> a.getPredictabilityByLeague(271L)),
                route("getProbabilitiesByFixture", a -> a.getProbabilitiesByFixture(19146700L)),
                route("getAllValueBets", a -> a.getAllValueBets()),
                route("getValueBetsByFixture", a -> a.getValueBetsByFixture(19146700L)),
                route("getExpectedGoalsByTeam", a -> a.getExpectedGoalsByTeam()),
                route("getExpectedGoalsByPlayer", a -> a.getExpectedGoalsByPlayer()),
                route("getAllPreMatchNews", a -> a.getAllPreMatchNews()),
                route("getPreMatchNewsBySeason", a -> a.getPreMatchNewsBySeason(23614L)),
                route("getPreMatchNewsForUpcoming", a -> a.getPreMatchNewsForUpcoming()),
                route("getAllPostMatchNews", a -> a.getAllPostMatchNews()),
                route("getPostMatchNewsBySeason", a -> a.getPostMatchNewsBySeason(23614L)));
    }

    private static Arguments route(String name, RouteCall call) {
        return Arguments.of(name, call);
    }

    /** 404 means the route does not exist; 403 means it exists but the subscription does not cover it. */
    @ParameterizedTest(name = "{0}")
    @MethodSource("routes")
    void routeExists(String name, RouteCall call) throws IOException {
        int status = 200;
        try {
            call.on(api);
        } catch (SportMonksException e) {
            status = e.getStatusCode();
        }
        if (status != 200) {
            assertEquals(403, status, name + " answered " + status + " (404 = route does not exist)");
        }
    }
}
