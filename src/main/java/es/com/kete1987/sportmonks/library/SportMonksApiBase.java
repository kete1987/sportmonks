package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.SubscriptionMeta;
import es.com.kete1987.sportmonks.library.common.model.subscription.SubscriptionMetaDeserializer;
import es.com.kete1987.sportmonks.library.common.util.EmptyStringToNumberTypeAdapter;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

abstract class SportMonksApiBase {

    /** Largest {@code per_page} the API accepts. */
    static final int MAX_PER_PAGE = 50;

    /** {@code per_page} the API applies when the request does not set one. */
    private static final int DEFAULT_PER_PAGE = 25;

    /**
     * Offset pagination is rejected past 20.000 rows (Sportmonks changelog, 2026-06-23): the
     * limit triggers at page 801 with {@code per_page=25} and at page 401 with {@code per_page=50}.
     * Cursor pagination has no such limit.
     */
    static final int MAX_OFFSET_ROWS = 20000;

    /** Status reported when a body read from a successful response cannot be mapped later on. */
    private static final int HTTP_OK = 200;

    final OkHttpClient httpClient;
    final String locale;
    final String timezone;
    final RateLimitTracker rateLimitTracker;

    SportMonksApiBase(OkHttpClient httpClient, String locale, String timezone, RateLimitTracker rateLimitTracker) {
        this.httpClient = httpClient;
        this.locale = locale;
        this.timezone = timezone;
        this.rateLimitTracker = rateLimitTracker;
    }

    static OkHttpClient buildHttpClient(String apiToken) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> chain.proceed(
                        chain.request().newBuilder()
                                .header("Authorization", apiToken)
                                .build()))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * The one {@code Gson} of the library. {@code Gson} is immutable and thread-safe, so every
     * sub-API and every request shares it instead of building a {@code GsonBuilder} per call.
     */
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(int.class, new EmptyStringToNumberTypeAdapter())
            .registerTypeAdapter(Integer.class, new EmptyStringToNumberTypeAdapter())
            .registerTypeAdapter(SubscriptionMeta.class, new SubscriptionMetaDeserializer())
            .create();

    Gson gson() {
        return GSON;
    }

    /**
     * Requests {@code url} and parses its body as {@code type}. Every failure is a
     * {@link SportMonksException}: a non-2xx status, and also a 2xx body that cannot be parsed
     * (malformed JSON or a value of the wrong type), which carries the status code, the URL
     * (it never holds the API token, which travels in a header) and the Gson error as cause.
     * An empty body yields {@code null}.
     *
     * <p>The body text is parsed once, into a tree: the {@code rate_limit} object is read from
     * that tree and the same tree is then mapped to {@code type}, so a multi-megabyte odds
     * response is not tokenised twice.
     */
    <R> R get(HttpUrl url, Class<R> type) throws IOException, SportMonksException {
        HttpBody body = execute(url);
        JsonElement tree;
        try {
            tree = JsonParser.parseString(body.text);
        } catch (JsonParseException e) {
            throw unparseable(body.status, url, e);
        }
        recordRateLimit(tree);
        return convert(body.status, url, tree, type);
    }

    /**
     * Maps part of a body already fetched from {@code url} (e.g. a nested object picked by hand)
     * to {@code type}, with the same error handling as {@link #get}.
     */
    <R> R convert(HttpUrl url, JsonElement json, Class<R> type) throws SportMonksException {
        return convert(HTTP_OK, url, json, type);
    }

    private static <R> R convert(int status, HttpUrl url, JsonElement json, Class<R> type) throws SportMonksException {
        try {
            return GSON.fromJson(json, type);
        } catch (JsonParseException e) {
            throw unparseable(status, url, e);
        }
    }

    private static SportMonksException unparseable(int status, HttpUrl url, JsonParseException e) {
        return new SportMonksException(status, "unparseable response from " + url + ": " + e.getMessage(), e);
    }

    private HttpBody execute(HttpUrl url) throws IOException, SportMonksException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = httpClient.newCall(request).execute()) {
            rateLimitTracker.total = response.header("X-RateLimit-Limit");
            rateLimitTracker.remaining = response.header("X-RateLimit-Remaining");
            String body = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                throw httpError(response.code(), body);
            }
            return new HttpBody(response.code(), body);
        }
    }

    private static final class HttpBody {
        final int status;
        final String text;

        HttpBody(int status, String text) {
            this.status = status;
            this.text = text;
        }
    }

    /** Builds the exception of a non-2xx response, reading {@code code} and {@code rate_limit} when the body is JSON. */
    private static SportMonksException httpError(int status, String body) {
        ErrorEnvelope envelope = null;
        try {
            envelope = GSON.fromJson(body, ErrorEnvelope.class);
        } catch (RuntimeException ignored) {
            // an error body that is not JSON (e.g. a proxy's HTML page) only loses the extra fields
        }
        return envelope != null
                ? new SportMonksException(status, body, envelope.code, envelope.rateLimit)
                : new SportMonksException(status, body, null, null);
    }

    private static final class ErrorEnvelope {
        Integer code;
        @SerializedName("rate_limit")
        RateLimit rateLimit;
    }

    /** Tracks the {@code rate_limit} object of an already parsed body, if it has one. */
    private void recordRateLimit(JsonElement tree) {
        if (!tree.isJsonObject()) return;
        JsonElement rateLimit = tree.getAsJsonObject().get("rate_limit");
        if (rateLimit == null || !rateLimit.isJsonObject()) return;
        try {
            rateLimitTracker.track(GSON.fromJson(rateLimit, RateLimit.class));
        } catch (JsonParseException ignored) {
            // rate_limit is best-effort metadata; never fail a request over a parse hiccup.
        }
    }

    /**
     * Generic paginator shared by every list endpoint: walks all pages and returns the complete
     * list, so a single library call may fire several HTTP requests.
     *
     * <p>It advances with the {@code next_cursor} the API reports, falling back to {@code &page=N}
     * on endpoints that do not return one. Cursor pagination has no depth limit; when falling back
     * to offset the walk stops at {@link #MAX_OFFSET_ROWS} rows instead of letting the API reject
     * the request, so a huge dataset returns a truncated list rather than throwing.
     *
     * <p>When {@code limit > 0} it shrinks {@code per_page} (capped at {@link #MAX_PER_PAGE}) so
     * the first page already carries enough rows, stops as soon as {@code limit} is reached, and
     * trims any overflow — avoiding crawling every page just to keep the first N. A non-positive
     * {@code limit} means "no limit". The {@code dataFn}/{@code pagFn} extract the {@code data}
     * and {@code pagination} of each response so the same loop serves every {@code *Response} type.
     *
     * <p>An empty body, or one carrying no {@code data} at all, ends the walk and returns whatever
     * was gathered so far. A body that cannot be parsed is not swallowed: like any other request
     * it throws a {@link SportMonksException} (see {@link #get}), discarding the pages already read,
     * because returning them would pass a silently truncated list off as complete.
     */
    <T, R> List<T> fetchPaged(HttpUrl base, Class<R> type,
                              Function<R, List<T>> dataFn, Function<R, Pagination> pagFn,
                              int limit) throws IOException, SportMonksException {
        HttpUrl first = limit > 0
                ? base.newBuilder().setQueryParameter("per_page", String.valueOf(Math.min(limit, MAX_PER_PAGE))).build()
                : base;
        PageWalk walk = new PageWalk();
        List<T> all = new ArrayList<>();
        R resp = get(first, type);
        while (resp != null) {
            List<T> data = dataFn.apply(resp);
            if (data == null) break;
            all.addAll(data);
            HttpUrl next = walk.next(first, pagFn.apply(resp), limitReached(all, limit));
            resp = next != null ? get(next, type) : null;
        }
        return limit > 0 && all.size() > limit ? new ArrayList<>(all.subList(0, limit)) : all;
    }

    private static boolean limitReached(List<?> gathered, int limit) {
        return limit > 0 && gathered.size() >= limit;
    }

    /**
     * Decides where the next page comes from, keeping the state the decision needs: the last
     * offset page requested and the last cursor followed. {@link #next} returns {@code null} when
     * the walk is over — no more pages, the limit is covered, or the next step would be unsafe.
     */
    private static final class PageWalk {

        private int page = 1;
        private String lastCursor;

        HttpUrl next(HttpUrl first, Pagination pg, boolean limitReached) {
            if (limitReached || pg == null || !pg.hasMore()) return null;
            String cursor = cursorOf(pg.getNextCursor());
            return cursor != null ? byCursor(first, cursor) : byOffset(first, pg);
        }

        private HttpUrl byCursor(HttpUrl first, String cursor) {
            if (cursor.equals(lastCursor)) return null; // a repeated cursor would loop forever
            lastCursor = cursor;
            // per_page is rejected alongside a cursor ("start a new request without a cursor to
            // change the page size"); the size the first request asked for travels encoded inside
            // the cursor itself, so dropping it here preserves it.
            return first.newBuilder()
                    .removeAllQueryParameters("page")
                    .removeAllQueryParameters("per_page")
                    .setQueryParameter("cursor", cursor)
                    .build();
        }

        private HttpUrl byOffset(HttpUrl first, Pagination pg) {
            page = nextPage(pg, page);
            if (offsetLimitReached(page, pg)) return null;
            return first.newBuilder().setQueryParameter("page", String.valueOf(page)).build();
        }

        /**
         * Extracts the opaque {@code cursor} value from {@code next_cursor}. The API returns it
         * today as a full URL, stripped of the API token and of any {@code include} or filter of
         * the original request, so only its {@code cursor} parameter is reused, on top of our URL.
         * The changelog documents it as a bare token instead; a value that is not a URL is taken
         * as that token, so a change of format keeps the walk on the cursor.
         */
        private static String cursorOf(String nextCursor) {
            if (nextCursor == null || nextCursor.isBlank()) return null;
            HttpUrl url = HttpUrl.parse(nextCursor);
            String cursor = url != null ? url.queryParameter("cursor") : nextCursor.trim();
            return cursor != null && !cursor.isEmpty() ? cursor : null;
        }

        /** Page to request next: the one the API reports as current plus one, or the local count. */
        private static int nextPage(Pagination pg, int lastPage) {
            Long current = pg.getCurrentPage();
            return (current != null && current > 0 ? current.intValue() : lastPage) + 1;
        }

        /** True when requesting {@code page} by offset would cross the 20.000-row depth limit. */
        private static boolean offsetLimitReached(int page, Pagination pg) {
            Long perPage = pg.getPerPage();
            long size = perPage != null && perPage > 0 ? perPage : DEFAULT_PER_PAGE;
            return page * size > MAX_OFFSET_ROWS;
        }
    }

    /**
     * Builds {@code base + path + segments}. {@code path} is a fixed route written by the library
     * (its slashes separate segments, e.g. {@code "teams/search"}); every value in
     * {@code segments} — user text or an id — becomes exactly one percent-encoded segment, so a
     * {@code /}, {@code ?} or {@code #} in it can neither change the route nor turn into the query
     * or the fragment. Locale and timezone are added as query parameters.
     */
    HttpUrl.Builder apiUrl(String base, String path, Object... segments) {
        return timezoneUrl(localeUrl(path(base, path, segments)));
    }

    static HttpUrl.Builder path(String base, String path, Object... segments) {
        HttpUrl.Builder builder = HttpUrl.get(base).newBuilder().addPathSegments(path);
        for (Object segment : segments) {
            builder.addPathSegment(String.valueOf(segment));
        }
        return builder;
    }

    HttpUrl.Builder withIncludes(HttpUrl.Builder builder, String... includes) {
        if (includes != null && includes.length > 0) {
            builder.addQueryParameter("include", String.join(";", includes));
        }
        return builder;
    }

    HttpUrl.Builder withFilters(HttpUrl.Builder builder, String filters) {
        if (filters != null && !filters.isBlank()) {
            builder.addQueryParameter("filters", filters);
        }
        return builder;
    }

    HttpUrl.Builder localeUrl(HttpUrl.Builder builder) {
        if (locale != null && !locale.isEmpty()) {
            builder.addQueryParameter("locale", locale);
        }
        return builder;
    }

    HttpUrl.Builder timezoneUrl(HttpUrl.Builder builder) {
        if (timezone != null && !timezone.isEmpty()) {
            builder.addQueryParameter("timezone", timezone);
        }
        return builder;
    }

    public String getRemainingRequests() {
        return rateLimitTracker.remaining;
    }

    public String getMaximumRequests() {
        return rateLimitTracker.total;
    }

    /**
     * Returns the {@code rate_limit} object from the body of the most recent response, or
     * {@code null} if no request has been made yet. Unlike {@link #getRemainingRequests()} (a
     * global header counter), this is scoped to the entity that response requested.
     */
    public RateLimit getLastRateLimit() {
        return rateLimitTracker.last();
    }

    /**
     * Returns an immutable snapshot of the latest {@code rate_limit} seen for each
     * {@code requested_entity} (e.g. {@code "league"}, {@code "team"}), accumulated across every
     * request made through this client. Empty until the first request.
     */
    public Map<String, RateLimit> getRateLimitsByEntity() {
        return rateLimitTracker.byEntity();
    }
}
