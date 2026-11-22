package es.com.kete1987.sportmonks.library;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Opt-in retries (see {@link ClientOptions.Builder#maxRetries(int)}). A 429 is retried after the
 * {@code rate_limit.resets_in_seconds} of its body, but only when that wait fits in the cap: a
 * quota that resets in an hour is reported at once instead of blocking the thread. A 5xx is
 * retried with exponential backoff ({@code backoff}, {@code 2 * backoff}, ...), also capped. Any
 * other status is returned as is.
 */
final class RetryInterceptor implements Interceptor {

    private static final int TOO_MANY_REQUESTS = 429;
    private static final long MAX_ERROR_BODY_BYTES = 64 * 1024L;
    private static final int MAX_BACKOFF_SHIFT = 20;
    private static final long GIVE_UP = -1;

    private final int maxRetries;
    private final long maxWaitMillis;
    private final long backoffMillis;

    RetryInterceptor(int maxRetries, Duration maxWait, Duration backoff) {
        this.maxRetries = maxRetries;
        this.maxWaitMillis = maxWait.toMillis();
        this.backoffMillis = backoff.toMillis();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        for (int attempt = 0; attempt < maxRetries; attempt++) {
            long wait = waitBeforeRetry(response, attempt);
            if (wait == GIVE_UP) {
                return response;
            }
            response.close();
            pause(wait);
            response = chain.proceed(request);
        }
        return response;
    }

    /** Milliseconds to wait before retrying {@code response}, or {@link #GIVE_UP}. */
    private long waitBeforeRetry(Response response, int attempt) throws IOException {
        int code = response.code();
        if (code == TOO_MANY_REQUESTS) {
            Long resetsIn = resetsInSeconds(response);
            if (resetsIn == null) {
                return backoff(attempt);
            }
            long wait = TimeUnit.SECONDS.toMillis(Math.max(0, resetsIn));
            return wait <= maxWaitMillis ? wait : GIVE_UP;
        }
        return code >= 500 && code < 600 ? backoff(attempt) : GIVE_UP;
    }

    private long backoff(int attempt) {
        long wait = backoffMillis << Math.min(attempt, MAX_BACKOFF_SHIFT);
        return Math.min(wait, maxWaitMillis);
    }

    /** {@code rate_limit.resets_in_seconds} of the error body, or {@code null} when absent. */
    private static Long resetsInSeconds(Response response) throws IOException {
        String body = response.peekBody(MAX_ERROR_BODY_BYTES).string();
        try {
            JsonElement root = JsonParser.parseString(body);
            JsonElement rateLimit = root.isJsonObject() ? root.getAsJsonObject().get("rate_limit") : null;
            JsonElement resets = rateLimit != null && rateLimit.isJsonObject()
                    ? rateLimit.getAsJsonObject().get("resets_in_seconds") : null;
            return resets != null && resets.isJsonPrimitive() && resets.getAsJsonPrimitive().isNumber()
                    ? resets.getAsLong() : null;
        } catch (JsonParseException e) {
            return null;
        }
    }

    private static void pause(long millis) throws InterruptedIOException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("interrupted while waiting to retry");
        }
    }
}
