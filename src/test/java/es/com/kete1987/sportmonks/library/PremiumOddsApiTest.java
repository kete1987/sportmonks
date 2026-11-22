package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Premium odds parsing; the premium routes are asserted in {@link OddsRoutesApiTest} and {@link OddsEndpointsApiTest}. */
class PremiumOddsApiTest extends BaseApiTest {

    @Test
    void getPremiumPreMatchOddsByFixture_returnsParsedList() throws IOException, SportMonksException {
        enqueue("odds.json");

        List<Odd> odds = api.getPremiumPreMatchOddsByFixture(1L);

        assertEquals(2, odds.size());
        assertEquals("2.50", odds.get(0).getValue());
    }
}