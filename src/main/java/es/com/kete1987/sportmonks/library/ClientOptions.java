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
 *
 * <p>Retries are off by default. {@link Builder#maxRetries(int)} turns them on for 429 (waiting
 * for the quota to reset, when that fits in {@link Builder#maxRetryWait(Duration)}) and for 5xx
 * (exponential backoff):
 *
 * <pre>{@code
 * ClientOptions options = ClientOptions.builder()
 *         .maxRetries(3)
 *         .maxRetryWait(Duration.ofSeconds(30))
 *         .build();
 * }</pre>
 */
public final class ClientOptions {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    private static final Duration DEFAULT_MAX_RETRY_WAIT = Duration.ofSeconds(60);
    private static final Duration DEFAULT_RETRY_BACKOFF = Duration.ofSeconds(1);
    private static final ClientOptions DEFAULTS = builder().build();

    private final OkHttpClient httpClient;
    private final Duration connectTimeout;
    private final Duration readTimeout;
    private final Duration callTimeout;
    private final int maxRetries;
    private final Duration maxRetryWait;
    private final Duration retryBackoff;

    private ClientOptions(Builder builder) {
        this.httpClient = builder.httpClient;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.callTimeout = builder.callTimeout;
        this.maxRetries = builder.maxRetries;
        this.maxRetryWait = builder.maxRetryWait;
        this.retryBackoff = builder.retryBackoff;
    }

    /** Options with every setting at its default: what the constructors without options use. */
    public static ClientOptions defaults() {
        return DEFAULTS;
    }

    public static Builder builder() {
        return new Builder();
    }

    /** Builder of the client the API uses: the base client, the timeouts and the opt-in retries. */
    OkHttpClient.Builder newClientBuilder() {
        OkHttpClient.Builder builder;
        if (httpClient != null) {
            builder = httpClient.newBuilder();
        } else {
            builder = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT)
                    .readTimeout(DEFAULT_TIMEOUT);
        }
        if (connectTimeout != null) {
            builder.connectTimeout(connectTimeout);
        }
        if (readTimeout != null) {
            builder.readTimeout(readTimeout);
        }
        if (callTimeout != null) {
            builder.callTimeout(callTimeout);
        }
        if (maxRetries > 0) {
            builder.addInterceptor(new RetryInterceptor(maxRetries, maxRetryWait, retryBackoff));
        }
        return builder;
    }

    /** Mutable builder of {@link ClientOptions}; every setting is optional. */
    public static final class Builder {

        private OkHttpClient httpClient;
        private Duration connectTimeout;
        private Duration readTimeout;
        private Duration callTimeout;
        private int maxRetries;
        private Duration maxRetryWait = DEFAULT_MAX_RETRY_WAIT;
        private Duration retryBackoff = DEFAULT_RETRY_BACKOFF;

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
         * page. With retries enabled, the waits and the retried requests count against it.
         */
        public Builder callTimeout(Duration callTimeout) {
            this.callTimeout = callTimeout;
            return this;
        }

        /**
         * Retries per request after a 429 or a 5xx (default 0: retries are off and a failed
         * response throws at once). A 429 is retried after the {@code rate_limit.resets_in_seconds}
         * of its body, only if that wait is within {@link #maxRetryWait(Duration)}; otherwise it
         * throws straight away. A 5xx is retried after {@link #retryBackoff(Duration)}, doubling on
         * each attempt up to the same cap. Other statuses (400, 401, 403, 404, 422) are never
         * retried. The calling thread sleeps while it waits.
         *
         * @throws IllegalArgumentException if {@code maxRetries} is negative
         */
        public Builder maxRetries(int maxRetries) {
            if (maxRetries < 0) {
                throw new IllegalArgumentException("maxRetries must not be negative: " + maxRetries);
            }
            this.maxRetries = maxRetries;
            return this;
        }

        /** Longest single wait before a retry (default 60 s). */
        public Builder maxRetryWait(Duration maxRetryWait) {
            this.maxRetryWait = maxRetryWait;
            return this;
        }

        /** First wait before retrying a 5xx, doubled on each further attempt (default 1 s). */
        public Builder retryBackoff(Duration retryBackoff) {
            this.retryBackoff = retryBackoff;
            return this;
        }

        public ClientOptions build() {
            return new ClientOptions(this);
        }
    }
}
