package es.com.kete1987.sportmonks.library;

import okhttp3.OkHttpClient;

import java.time.Duration;

/**
 * HTTP settings for the API clients, passed to the constructors that take a
 * {@code ClientOptions} (e.g. {@link SportMonksAPI#SportMonksAPI(String, String, String, ClientOptions)}).
 *
 * <pre>{@code
 * ClientOptions options = ClientOptions.builder()
 *         .connectTimeout(Duration.ofSeconds(10))
 *         .readTimeout(Duration.ofSeconds(20))
 *         .callTimeout(Duration.ofMinutes(2))
 *         .build();
 * SportMonksAPI api = new SportMonksAPI("YOUR_API_KEY", null, null, options);
 * }</pre>
 *
 * <p>Without options the library keeps its historical behaviour: 30-second connect and read
 * timeouts and no call timeout. With {@link Builder#httpClient(OkHttpClient)} the library starts
 * from that client (its pool, dispatcher, interceptors and timeouts) and only adds the
 * {@code Authorization} header; the timeouts set here, if any, override the client's.
 */
public final class ClientOptions {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    private static final ClientOptions DEFAULTS = builder().build();

    private final OkHttpClient httpClient;
    private final Duration connectTimeout;
    private final Duration readTimeout;
    private final Duration callTimeout;

    private ClientOptions(Builder builder) {
        this.httpClient = builder.httpClient;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.callTimeout = builder.callTimeout;
    }

    /** Options with every setting at its default: what the constructors without options use. */
    public static ClientOptions defaults() {
        return DEFAULTS;
    }

    public static Builder builder() {
        return new Builder();
    }

    /** Builds the client the API uses: the base client, the timeouts and the token header. */
    OkHttpClient.Builder newClientBuilder() {
        OkHttpClient.Builder builder;
        if (httpClient != null) {
            builder = httpClient.newBuilder();
        } else {
            builder = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT)
                    .readTimeout(DEFAULT_TIMEOUT);
        }
        if (connectTimeout != null) builder.connectTimeout(connectTimeout);
        if (readTimeout != null) builder.readTimeout(readTimeout);
        if (callTimeout != null) builder.callTimeout(callTimeout);
        return builder;
    }

    /** Mutable builder of {@link ClientOptions}; every setting is optional. */
    public static final class Builder {

        private OkHttpClient httpClient;
        private Duration connectTimeout;
        private Duration readTimeout;
        private Duration callTimeout;

        /**
         * Client to start from, e.g. one shared with the rest of the application. Its timeouts
         * apply unless overridden here; the library never modifies it, it derives its own.
         */
        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /** Timeout to open a connection (default 30 s). */
        public Builder connectTimeout(Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /** Timeout between two reads of a response (default 30 s). */
        public Builder readTimeout(Duration readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * Limit for a whole HTTP call, from connecting to reading the full body (default: none).
         * It applies to each request: a list method that walks several pages fires one call per
         * page.
         */
        public Builder callTimeout(Duration callTimeout) {
            this.callTimeout = callTimeout;
            return this;
        }

        public ClientOptions build() {
            return new ClientOptions(this);
        }
    }
}
