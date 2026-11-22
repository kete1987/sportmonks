package es.com.kete1987.sportmonks.library;

import okhttp3.HttpUrl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** The value object behind {@code withOptions(...)} (#84): building, immutability and URL rendering. */
class RequestOptionsTest {

    private static final HttpUrl BASE = HttpUrl.get("https://example.test/v3/football/leagues?include=country");
    private static final String NAME = "name";
    private static final String FILTERS = "filters";

    @Test
    void none_isEmptyAndLeavesUrlsUnchanged() {
        RequestOptions none = RequestOptions.none();

        assertTrue(none.isEmpty());
        assertTrue(none.getSelect().isEmpty());
        assertNull(none.getOrder());
        assertNull(none.getSortBy());
        assertNull(none.getFilters());
        assertSame(BASE, none.applyTo(BASE));
    }

    @Test
    void emptyBuilder_equalsNone() {
        assertEquals(RequestOptions.none(), RequestOptions.builder().build());
        assertEquals(RequestOptions.none().hashCode(), RequestOptions.builder().build().hashCode());
    }

    @Test
    void select_joinsFieldsSkippingBlanks() {
        RequestOptions options = RequestOptions.builder().select(" name ", "", null, "image_path").build();

        assertEquals(List.of(NAME, "image_path"), options.getSelect());
        assertEquals("name,image_path", options.applyTo(BASE).queryParameter("select"));
    }

    @Test
    void blankValues_meanAbsent() {
        RequestOptions options = RequestOptions.builder().sortBy("  ").filters("").select().build();

        assertTrue(options.isEmpty());
    }

    @Test
    void order_rendersLowerCase() {
        HttpUrl url = RequestOptions.builder().order(RequestOptions.Order.DESC).build().applyTo(BASE);

        assertEquals("desc", url.queryParameter("order"));
        assertEquals("country", url.queryParameter("include"));
    }

    @Test
    void filters_areAppendedToTheFiltersAlreadyInTheUrl() {
        HttpUrl withFilter = BASE.newBuilder().addQueryParameter(FILTERS, "leagueCountries:462").build();

        HttpUrl url = RequestOptions.builder().filters("leagueLeagues:8").build().applyTo(withFilter);

        assertEquals(List.of("leagueCountries:462;leagueLeagues:8"), url.queryParameterValues(FILTERS));
    }

    @Test
    void builder_doesNotAffectOptionsAlreadyBuilt() {
        RequestOptions.Builder builder = RequestOptions.builder().select(NAME);
        RequestOptions first = builder.build();

        builder.select("id").order(RequestOptions.Order.ASC);

        assertEquals(List.of(NAME), first.getSelect());
        assertNull(first.getOrder());
    }

    @Test
    void toBuilder_copiesEveryValue() {
        RequestOptions original = RequestOptions.builder().select(NAME).order(RequestOptions.Order.ASC)
                .sortBy(NAME).filters("leagueCountries:462").build();

        RequestOptions copy = original.toBuilder().build();
        RequestOptions changed = original.toBuilder().order(RequestOptions.Order.DESC).build();

        assertEquals(original, copy);
        assertNotEquals(original, changed);
        assertEquals(RequestOptions.Order.ASC, original.getOrder());
    }

    @Test
    void getSelect_isUnmodifiable() {
        List<String> select = RequestOptions.builder().select(NAME).build().getSelect();

        assertThrows(UnsupportedOperationException.class, select::clear);
    }

    @Test
    void toString_listsTheValues() {
        String text = RequestOptions.builder().select(NAME).sortBy("starting_at").build().toString();

        assertTrue(text.contains(NAME) && text.contains("starting_at"), text);
    }
}
