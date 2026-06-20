package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Shared, mutable holder for the rate-limit state observed across requests.
 *
 * <p>Two independent signals are tracked. {@link #total}/{@link #remaining} come from the
 * {@code X-RateLimit-*} HTTP headers and are a single global counter (last response wins).
 * {@link #byEntity}/{@link #last} come from the {@code rate_limit} object in the response body,
 * which Sportmonks scopes per {@code requested_entity} (e.g. {@code "league"}, {@code "team"}),
 * so they are accumulated keyed by entity.
 */
class RateLimitTracker {
    volatile String total;
    volatile String remaining;

    private final Map<String, RateLimit> byEntity = new ConcurrentHashMap<>();
    private volatile RateLimit last;

    void record(RateLimit rateLimit) {
        if (rateLimit == null) return;
        last = rateLimit;
        String entity = rateLimit.getRequestedEntity();
        if (entity != null) {
            byEntity.put(entity, rateLimit);
        }
    }

    RateLimit last() {
        return last;
    }

    Map<String, RateLimit> byEntity() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(byEntity));
    }
}
