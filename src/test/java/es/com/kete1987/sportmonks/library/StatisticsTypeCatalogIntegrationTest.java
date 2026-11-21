package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import es.com.kete1987.sportmonks.library.football.util.StatisticsType;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Compara {@link StatisticsType} con el catálogo completo {@code GET /core/types} para que las
 * constantes no se desfasen de la API sin que nadie se entere.
 *
 * <ul>
 *   <li>FALLA si una constante no existe en el catálogo o su nombre no coincide con el
 *       {@code developer_name} (ignorando {@code _} y mayúsculas), salvo las excepciones de
 *       {@link #NAME_EXCEPTIONS}.</li>
 *   <li>NO falla por tipos de estadística del catálogo sin constante: solo los imprime, para que
 *       Sportmonks añadiendo tipos no rompa la build.</li>
 * </ul>
 *
 * Se salta solo si falta {@code SPORTMONKS_API_KEY}; corre con {@code mvn test -Pintegration}.
 */
class StatisticsTypeCatalogIntegrationTest {

    /**
     * Constantes públicas cuyo nombre difiere legítimamente del {@code developer_name} de la API,
     * mapeadas al nombre real. No se renombran para no romper la API pública.
     */
    private static final Map<String, String> NAME_EXCEPTIONS = new HashMap<>();

    static {
        NAME_EXCEPTIONS.put("DRIBBLE_ATTEMPTS", "DRIBBLED_ATTEMPTS");
    }

    private static List<Type> catalog;

    @BeforeAll
    static void setUp() throws IOException, SportMonksException {
        String key = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(key != null && !key.isEmpty(), "SPORTMONKS_API_KEY not set");
        catalog = new SportMonksAPI(key).getAllTypes();
    }

    private static String normalize(String name) {
        return name.replace("_", "").toUpperCase(Locale.ROOT);
    }

    private static List<Field> constants() {
        List<Field> fields = new ArrayList<>();
        for (Field f : StatisticsType.class.getFields()) {
            if (Modifier.isStatic(f.getModifiers()) && f.getType() == int.class) {
                fields.add(f);
            }
        }
        return fields;
    }

    @Test
    void everyConstant_existsInCatalogWithSameDeveloperName() throws IllegalAccessException {
        Map<Long, Type> byId = new HashMap<>();
        for (Type type : catalog) {
            byId.put(type.getId(), type);
        }

        List<String> problems = new ArrayList<>();
        for (Field f : constants()) {
            int id = f.getInt(null);
            Type type = byId.get((long) id);
            if (type == null) {
                problems.add(f.getName() + " (" + id + "): no existe en core/types");
                continue;
            }
            String expected = NAME_EXCEPTIONS.getOrDefault(f.getName(), f.getName());
            if (!normalize(expected).equals(normalize(String.valueOf(type.getDeveloperName())))) {
                problems.add(f.getName() + " (" + id + "): developer_name en la API = " + type.getDeveloperName());
            }
        }

        assertTrue(problems.isEmpty(), "StatisticsType desfasado de core/types:\n" + String.join("\n", problems));
    }

    @Test
    void statisticTypesWithoutConstant_areOnlyReported() throws IllegalAccessException {
        Set<Long> known = new HashSet<>();
        for (Field f : constants()) {
            known.add((long) f.getInt(null));
        }

        List<String> missing = new ArrayList<>();
        int statistics = 0;
        for (Type type : catalog) {
            if ("statistic".equals(type.getModelType())) {
                statistics++;
                if (!known.contains(type.getId())) {
                    missing.add(type.getId() + " " + type.getDeveloperName());
                }
            }
        }

        // Informativo: no hace fallar el build, para que Sportmonks pueda añadir tipos.
        System.out.println(statistics + " tipos de estadistica en el catalogo; sin constante en StatisticsType ("
                + missing.size() + "): " + missing);
    }
}
