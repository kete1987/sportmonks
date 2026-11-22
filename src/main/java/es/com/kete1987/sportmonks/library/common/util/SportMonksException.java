package es.com.kete1987.sportmonks.library.common.util;

import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;

/**
 * Error returned by the library for any response it cannot turn into data: a non-2xx HTTP
 * status or a body that cannot be parsed.
 *
 * <p>{@link #getMessage()} keeps the historical {@code "code - body"} format. The same data is
 * available typed: {@link #getStatusCode()} (e.g. 404, 403, 429), {@link #getErrorCode()} (the
 * Sportmonks {@code code} of the error body, e.g. 5007 for an endpoint outside the
 * subscription) and {@link #getRateLimit()} (the {@code rate_limit} object of the body, when the
 * API sends one).
 */
public class SportMonksException extends Exception {
    private static final long serialVersionUID = -4146966752341203498L;

    private final int statusCode;
    private final Integer errorCode;
    private final transient RateLimit rateLimit;

    /** Error without HTTP details; {@link #getStatusCode()} returns 0. */
    public SportMonksException(String s) {
        this(s, 0, null, null, null);
    }

    /**
     * Error built from an HTTP response.
     *
     * @param statusCode HTTP status of the response
     * @param body       response body, kept in the message as {@code "statusCode - body"}
     * @param errorCode  Sportmonks {@code code} of the error body, or {@code null}
     * @param rateLimit  {@code rate_limit} of the error body, or {@code null}
     */
    public SportMonksException(int statusCode, String body, Integer errorCode, RateLimit rateLimit) {
        this(statusCode + " - " + body, statusCode, errorCode, rateLimit, null);
    }

    private SportMonksException(String message, int statusCode, Integer errorCode, RateLimit rateLimit, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.rateLimit = rateLimit;
    }

    /** HTTP status of the response (e.g. 404, 403, 429), or 0 when the error has no HTTP details. */
    public int getStatusCode() {
        return statusCode;
    }

    /** Sportmonks {@code code} of the error body (e.g. 5007), or {@code null} when it has none. */
    public Integer getErrorCode() {
        return errorCode;
    }

    /** {@code rate_limit} of the error body, or {@code null} when it has none. */
    public RateLimit getRateLimit() {
        return rateLimit;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
