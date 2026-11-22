package es.com.kete1987.sportmonks.library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Issue #83: {@code Gson} is thread-safe and immutable, so the library shares one instance
 * instead of building a {@code GsonBuilder} on every request.
 */
class SharedGsonTest extends BaseApiTest {

    @Test
    void gson_isTheSameInstanceOnEveryCall() {
        FootballApi football = api.getFootball();

        assertSame(football.gson(), football.gson());
    }

    @Test
    void gson_isSharedAcrossSubApis() {
        assertSame(api.getFootball().gson(), api.getOdds().gson());
    }
}
