package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
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
import java.util.Map;
import java.util.concurrent.TimeUnit;

abstract class SportMonksApiBase {

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
