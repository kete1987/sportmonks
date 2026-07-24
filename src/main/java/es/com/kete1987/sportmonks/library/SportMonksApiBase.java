package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new EmptyStringToNumberTypeAdapter())
                .registerTypeAdapter(Integer.class, new EmptyStringToNumberTypeAdapter())
                .registerTypeAdapter(SubscriptionMeta.class, new SubscriptionMetaDeserializer())
                .create();
    }

    String execute(HttpUrl url) throws IOException, SportMonksException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = httpClient.newCall(request).execute()) {
            rateLimitTracker.total = response.header("X-RateLimit-Limit");
            rateLimitTracker.remaining = response.header("X-RateLimit-Remaining");
            String body = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                throw new SportMonksException(response.code() + " - " + body);
            }
            recordRateLimit(body);
            return body;
        }
    }

    private void recordRateLimit(String body) {
        if (body.isEmpty()) return;
        try {
            RateLimitEnvelope envelope = gson().fromJson(body, RateLimitEnvelope.class);
            if (envelope != null) {
                rateLimitTracker.track(envelope.rateLimit);
            }
        } catch (RuntimeException ignored) {
            // rate_limit is best-effort metadata; never fail a request over a parse hiccup.
        }
    }

    private static final class RateLimitEnvelope {
        @SerializedName("rate_limit")
        RateLimit rateLimit;
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
     * <p>A response that cannot be parsed, or one carrying no {@code data} at all, ends the walk
     * and returns whatever was gathered so far.
     */
    <T, R> List<T> fetchPaged(HttpUrl base, Class<R> type,
                              Function<R, List<T>> dataFn, Function<R, Pagination> pagFn,
                              int limit) throws IOException, SportMonksException {
        Gson g = gson();
        HttpUrl first = limit > 0
                ? base.newBuilder().setQueryParameter("per_page", String.valueOf(Math.min(limit, MAX_PER_PAGE))).build()
                : base;
        PageWalk walk = new PageWalk();
        List<T> all = new ArrayList<>();
        R resp = g.fromJson(execute(first), type);
        while (resp != null) {
            List<T> data = dataFn.apply(resp);
            if (data == null) break;
            all.addAll(data);
            HttpUrl next = walk.next(first, pagFn.apply(resp), limitReached(all, limit));
            resp = next != null ? g.fromJson(execute(next), type) : null;
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
    }

    /**
     * Extracts the opaque {@code cursor} value from a {@code next_cursor} URL. The API returns it
     * as a full URL but strips the API token from it, and it also drops any {@code include} or
     * filter of the original request — so only the cursor is reused, on top of the URL we built.
     */
    private static String cursorOf(String nextCursor) {
        if (nextCursor == null || nextCursor.isEmpty()) return null;
        HttpUrl url = HttpUrl.parse(nextCursor);
        String cursor = url != null ? url.queryParameter("cursor") : null;
        return cursor != null && !cursor.isEmpty() ? cursor : null;
    }

    /** Page to request next: the one the API reports as current plus one, or the local count. */
    private static int nextPage(Pagination pg, int lastPage) {
        Long current = pg.getCurrentPage();
        return (current != null && current > 0 ? current.intValue() : lastPage) + 1;
    }

    /** True when requesting {@code page} by offset would cross the API's 20.000-row depth limit. */
    private static boolean offsetLimitReached(int page, Pagination pg) {
        Long perPage = pg.getPerPage();
        long size = perPage != null && perPage > 0 ? perPage : DEFAULT_PER_PAGE;
        return page * size > MAX_OFFSET_ROWS;
    }

    HttpUrl.Builder withIncludes(HttpUrl.Builder builder, String... includes) {
        if (includes != null && includes.length > 0) {
            builder.addQueryParameter("include", String.join(";", includes));
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
