package es.com.kete1987.sportmonks.library;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import es.com.kete1987.sportmonks.library.odds.model.BookmakerMapping;
import es.com.kete1987.sportmonks.library.odds.model.Market;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import es.com.kete1987.sportmonks.library.odds.model.OddsResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The odds models declare every key of the real JSON (fixtures copied from live responses, #78).
 * Gson drops unknown keys silently, so each test lists the keys the model lacks.
 */
class OddsModelsApiTest {

    private static JsonElement body(String fixture) throws IOException {
        try (InputStream in = OddsModelsApiTest.class.getClassLoader().getResourceAsStream("fixtures/" + fixture)) {
            if (in == null) {
                throw new IOException("Test fixture not found: " + fixture);
            }
            return JsonParser.parseString(new String(in.readAllBytes(), StandardCharsets.UTF_8));
        }
    }

    private static JsonElement data(String fixture) throws IOException {
        return body(fixture).getAsJsonObject().get("data");
    }

    @Test
    void bookmakerMapping_declaresEveryRealKey() throws IOException {
        List<String> missing = JsonCoverage.missing(data("bookmaker_mapping_real.json"), BookmakerMapping.class);

        assertEquals(Collections.emptyList(), missing);
    }

    @Test
    void market_declaresLegacyId() throws IOException {
        List<String> missing = JsonCoverage.missing(data("market_real.json"), Market.class);

        assertEquals(Collections.emptyList(), missing);
    }

    @Test
    void odd_declaresIncludedMarketAndFixture() throws IOException {
        List<String> missing = JsonCoverage.missing(data("odds_included.json"), Odd.class);

        assertEquals(Collections.emptyList(), missing);
    }

    @Test
    void oddsResponse_declaresTimestamp() throws IOException {
        List<String> missing = JsonCoverage.missing(body("odds_included.json"), OddsResponse.class);

        assertEquals(Collections.emptyList(), missing);
    }
}
