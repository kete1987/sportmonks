package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.util.LineupDetailType;
import es.com.kete1987.sportmonks.library.odds.model.Market;
import es.com.kete1987.sportmonks.library.odds.util.MarketIds;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Compares {@link LineupDetailType} with {@code GET /core/types} and {@link MarketIds} with
 * {@code GET /odds/markets}, so the constants cannot drift from the API unnoticed (#86).
 *
 * <ul>
 *   <li>Fails when a constant is missing from the catalogue or its name differs from the
 *       {@code developer_name} (ignoring {@code _} and case), except the entries of
 *       {@link #NAME_EXCEPTIONS}.</li>
 *   <li>Fails when a detail type that a real lineup carries has no {@link LineupDetailType}
 *       constant.</li>
 *   <li>Does not fail for catalogue entries without a constant: it only prints them, so Sportmonks
 *       adding types or markets cannot break the build.</li>
 * </ul>
 *
 * Needs {@code SPORTMONKS_API_KEY}; runs with {@code mvn test -Pintegration}.
 */
class ConstantsCatalogIntegrationTest {

    /** A finished fixture whose lineups carry detail rows of most types. */
    private static final String FIXTURE = "18545372";

    /** Constants whose Java name differs on purpose from the {@code developer_name} (it starts with a digit). */
    private static final Map<String, String> NAME_EXCEPTIONS = new HashMap<>();

    static {
        NAME_EXCEPTIONS.put("FIRST_HALF_GOALS", "1ST_HALF_GOALS");
    }

    private static SportMonksAPI api;
    private static List<Type> types;
    private static List<Market> markets;

    @BeforeAll
    static void setUp() throws IOException, SportMonksException {
        String key = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(key != null && !key.isEmpty(), "SPORTMONKS_API_KEY not set");
        api = new SportMonksAPI(key);
        types = api.getAllTypes();
        markets = api.getAllMarkets();
    }

    private static String normalize(String name) {
        return name.replace("_", "").toUpperCase(Locale.ROOT);
    }

    private static List<Field> constants(Class<?> holder) {
        List<Field> fields = new ArrayList<>();
        for (Field f : holder.getFields()) {
            if (Modifier.isStatic(f.getModifiers()) && f.getType().isPrimitive()) {
                fields.add(f);
            }
        }
        return fields;
    }

    private static List<String> problems(Class<?> holder, Map<Long, String> developerNames) throws IllegalAccessException {
        List<String> problems = new ArrayList<>();
        for (Field f : constants(holder)) {
            long id = ((Number) f.get(null)).longValue();
            String actual = developerNames.get(id);
            String expected = NAME_EXCEPTIONS.getOrDefault(f.getName(), f.getName());
            if (actual == null) {
                problems.add(f.getName() + " (" + id + "): does not exist in the catalogue");
            } else if (!normalize(expected).equals(normalize(actual))) {
                problems.add(f.getName() + " (" + id + "): developer_name in the API = " + actual);
            }
        }
        return problems;
    }

    @Test
    void everyLineupDetailTypeConstant_existsInCoreTypesWithSameDeveloperName() throws IllegalAccessException {
        Map<Long, String> byId = new HashMap<>();
        types.forEach(t -> byId.put(t.getId(), String.valueOf(t.getDeveloperName())));

        List<String> problems = problems(LineupDetailType.class, byId);

        assertTrue(problems.isEmpty(), "LineupDetailType drifted from core/types:\n" + String.join("\n", problems));
    }

    @Test
    void everyMarketIdsConstant_existsInTheMarketCatalogueWithSameDeveloperName() throws IllegalAccessException {
        Map<Long, String> byId = new HashMap<>();
        markets.forEach(m -> byId.put(m.getId(), String.valueOf(m.getDeveloperName())));

        List<String> problems = problems(MarketIds.class, byId);

        assertTrue(problems.isEmpty(), "MarketIds drifted from odds/markets:\n" + String.join("\n", problems));
    }

    @Test
    void everyDetailTypeOfARealLineup_hasAConstant() throws IOException, SportMonksException, IllegalAccessException {
        MatchDetail match = api.getMatchDetail(FIXTURE, new String[]{"lineups.details"});
        Set<Long> known = new HashSet<>();
        for (Field f : constants(LineupDetailType.class)) {
            known.add(((Number) f.get(null)).longValue());
        }

        Set<Long> unknown = new HashSet<>();
        match.getLineups().stream().flatMap(p -> p.getDetails().stream())
                .filter(d -> d.getTypeId() != null && !known.contains(d.getTypeId()))
                .forEach(d -> unknown.add(d.getTypeId()));

        assertFalse(match.getLineups().isEmpty(), "The fixture must carry lineups");
        assertTrue(unknown.isEmpty(),
                "Detail types of the fixture without a LineupDetailType constant: " + unknown);
    }

    @Test
    void catalogueEntriesWithoutConstant_areOnlyReported() throws IllegalAccessException {
        Set<Long> knownMarkets = new HashSet<>();
        for (Field f : constants(MarketIds.class)) {
            knownMarkets.add(((Number) f.get(null)).longValue());
        }

        List<String> withoutConstant = new ArrayList<>();
        markets.stream().filter(m -> !knownMarkets.contains(m.getId()))
                .forEach(m -> withoutConstant.add(m.getId() + " " + m.getDeveloperName()));

        // Informative: Sportmonks adding markets must not break the build.
        System.out.println(markets.size() + " markets in the catalogue; without a MarketIds constant ("
                + withoutConstant.size() + "): " + withoutConstant);
        assertTrue(markets.size() > knownMarkets.size(), "The catalogue must hold more markets than the constants");
    }
}
