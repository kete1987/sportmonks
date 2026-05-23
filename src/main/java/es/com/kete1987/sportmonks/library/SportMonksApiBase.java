package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.com.kete1987.sportmonks.library.common.model.subscription.SubscriptionMeta;
import es.com.kete1987.sportmonks.library.common.model.subscription.SubscriptionMetaDeserializer;
import es.com.kete1987.sportmonks.library.common.util.EmptyStringToNumberTypeAdapter;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

abstract class SportMonksApiBase {

    final OkHttpClient httpClient;
    final String locale;
    final RateLimitTracker rateLimitTracker;

    SportMonksApiBase(OkHttpClient httpClient, String locale, RateLimitTracker rateLimitTracker) {
        this.httpClient = httpClient;
        this.locale = locale;
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
            return body;
        }
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

    public String getRemainingRequests() {
        return rateLimitTracker.remaining;
    }

    public String getMaximumRequests() {
        return rateLimitTracker.total;
    }
}
