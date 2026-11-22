package es.com.kete1987.sportmonks.library;

import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Request parameters Sportmonks accepts on (almost) every endpoint: {@code select},
 * {@code order}, {@code sortBy} and {@code filters}. Immutable; build it with {@link #builder()}
 * and apply it with {@code withOptions(...)} on {@link SportMonksAPI} or on any sub-API, which
 * returns a view that sends these parameters on every request, every page of a paginated walk
 * included:
 *
 * <pre>{@code
 * RequestOptions latestFirst = RequestOptions.builder()
 *         .sortBy("starting_at")
 *         .order(RequestOptions.Order.DESC)
 *         .build();
 * List<MatchDetail> fixtures = api.withOptions(latestFirst).getAllFixtures(10);
 * }</pre>
 *
 * <p>Behaviour checked against the real API (November 2026):
 * <ul>
 *   <li><b>{@code select}</b> trims the fields of the main entity: {@code teams/{id}?select=name,image_path}
 *       returns {@code name} and {@code image_path} plus {@code id} and the relation keys
 *       ({@code country_id}, {@code sport_id}, {@code venue_id}), which the API always adds. Every
 *       other model field is then {@code null}. It works on football, core and odds entities; an
 *       unknown field is rejected with 400, and the MySportmonks endpoints ({@code my/...}) ignore it.
 *       It does not reach includes, which keep all their fields.</li>
 *   <li><b>{@code order}</b> ({@code asc} by default) sorts by id, or by {@code sortBy} when set;
 *       checked on leagues, fixtures, countries, states, markets and bookmakers. An invalid value
 *       is ignored by the API, so it is an enum here.</li>
 *   <li><b>{@code sortBy}</b> accepts only the fields each entity supports for custom sorting:
 *       {@code starting_at} on fixtures, {@code name} on leagues. Any other field answers 400
 *       listing the supported ones, and entities with no custom sort (standings) answer 400 to
 *       any value.</li>
 *   <li><b>{@code filters}</b> works on endpoints that had no {@code filters} parameter in the
 *       library, e.g. {@code players/{id}?include=statistics&filters=playerstatisticSeasons:1586}
 *       keeps only that season's statistics, {@code leagues?filters=leagueCountries:462} only
 *       English leagues. An unknown filter answers 400; {@code CoreApi#getAllEntityFilters()}
 *       lists the valid ones per entity. Several filters are separated with {@code ;}.</li>
 * </ul>
 *
 * <p><b>Combining filters.</b> When the method already sends {@code filters} (the
 * {@code (includes, filters)} overloads of fixtures, livescores, stages, rounds or odds, or the
 * fixed {@code seasontopscorerTypes} of {@code getTopScoresFiltered}), the method's filter goes
 * first and the one of the options is appended with {@code ;}, so neither is lost:
 * {@code lineupdetailTypes:40} plus {@code eventTypes:14} is sent as
 * {@code lineupdetailTypes:40;eventTypes:14}. The API applies both (they narrow the result).
 *
 * <p><b>Pagination.</b> The options travel on every page. With cursor pagination they are not
 * optional: the API rejects a cursor whose {@code order}, {@code sortBy} or {@code select} differ
 * from the request that created it ("The cursor is not valid for this request"). {@code per_page}
 * is not an option: the library derives it from the {@code limit} of each method and never sends
 * it alongside a cursor.
 */
public final class RequestOptions {

    /** Sort direction of the {@code order} parameter. */
    public enum Order {
        ASC, DESC;

        String value() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    static final String SELECT = "select";
    static final String ORDER = "order";
    static final String SORT_BY = "sortBy";
    static final String FILTERS = "filters";

    /** Separator Sportmonks uses between several filters. */
    static final String FILTER_SEPARATOR = ";";

    private static final RequestOptions NONE = new Builder().build();

    private final List<String> select;
    private final Order order;
    private final String sortBy;
    private final String filters;

    private RequestOptions(Builder builder) {
        this.select = Collections.unmodifiableList(new ArrayList<>(builder.select));
        this.order = builder.order;
        this.sortBy = builder.sortBy;
        this.filters = builder.filters;
    }

    /** Options that add nothing: a view built with them sends exactly the requests of the original. */
    public static RequestOptions none() {
        return NONE;
    }

    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with these values, to derive new options from them. */
    public Builder toBuilder() {
        Builder builder = new Builder().order(order).sortBy(sortBy).filters(filters);
        builder.select.addAll(select);
        return builder;
    }

    /** Fields of {@code select}, in order; empty when not set. Unmodifiable. */
    public List<String> getSelect() {
        return select;
    }

    /** Value of {@code order}, or {@code null} when not set (the API default is ascending). */
    public Order getOrder() {
        return order;
    }

    /** Value of {@code sortBy}, or {@code null} when not set. */
    public String getSortBy() {
        return sortBy;
    }

    /** Value of {@code filters}, or {@code null} when not set. */
    public String getFilters() {
        return filters;
    }

    /** True when no option is set. */
    public boolean isEmpty() {
        return select.isEmpty() && order == null && sortBy == null && filters == null;
    }

    /**
     * Adds these options to {@code url} as query parameters. {@code select}, {@code order} and
     * {@code sortBy} replace any value already there; {@code filters} is appended to the
     * {@code filters} already in the URL with {@code ;}. Without options the same URL is returned.
     */
    HttpUrl applyTo(HttpUrl url) {
        if (isEmpty()) return url;
        HttpUrl.Builder builder = url.newBuilder();
        if (!select.isEmpty()) builder.setQueryParameter(SELECT, String.join(",", select));
        if (order != null) builder.setQueryParameter(ORDER, order.value());
        if (sortBy != null) builder.setQueryParameter(SORT_BY, sortBy);
        if (filters != null) builder.setQueryParameter(FILTERS, combineFilters(url.queryParameter(FILTERS)));
        return builder.build();
    }

    private String combineFilters(String existing) {
        return existing == null || existing.isBlank() ? filters : existing + FILTER_SEPARATOR + filters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestOptions)) return false;
        RequestOptions other = (RequestOptions) o;
        return select.equals(other.select) && order == other.order
                && Objects.equals(sortBy, other.sortBy) && Objects.equals(filters, other.filters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(select, order, sortBy, filters);
    }

    @Override
    public String toString() {
        return "RequestOptions{select=" + select + ", order=" + order + ", sortBy=" + sortBy
                + ", filters=" + filters + '}';
    }

    /** Mutable builder of {@link RequestOptions}; each {@link #build()} returns an independent copy. */
    public static final class Builder {

        private final List<String> select = new ArrayList<>();
        private Order order;
        private String sortBy;
        private String filters;

        private Builder() {
            // instances come from RequestOptions.builder() and toBuilder()
        }

        /**
         * Fields to return ({@code select=a,b}); replaces any previous selection. Blank or
         * {@code null} entries are skipped, and no field at all clears the option.
         */
        public Builder select(String... fields) {
            select.clear();
            if (fields != null) {
                for (String field : fields) {
                    String value = blankToNull(field);
                    if (value != null) select.add(value);
                }
            }
            return this;
        }

        /** Sort direction; {@code null} clears it. */
        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        /** Field to sort by (e.g. {@code starting_at} on fixtures); {@code null} or blank clears it. */
        public Builder sortBy(String field) {
            this.sortBy = blankToNull(field);
            return this;
        }

        /**
         * Unencoded filter expression (e.g. {@code playerstatisticSeasons:1586}; several separated
         * with {@code ;}); {@code null} or blank clears it.
         */
        public Builder filters(String filters) {
            this.filters = blankToNull(filters);
            return this;
        }

        public RequestOptions build() {
            return new RequestOptions(this);
        }

        private static String blankToNull(String value) {
            return value == null || value.isBlank() ? null : value.trim();
        }
    }
}
