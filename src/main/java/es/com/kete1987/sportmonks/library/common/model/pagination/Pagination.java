package es.com.kete1987.sportmonks.library.common.model.pagination;

import com.google.gson.annotations.SerializedName;

public class Pagination {
    private Long count;
    @SerializedName("per_page")
    private Long perPage;
    @SerializedName("current_page")
    private Long currentPage;
    @SerializedName("next_page")
    private String nextPage;
    @SerializedName("next_cursor")
    private String nextCursor;
    @SerializedName("has_more")
    private Boolean hasMore;

    public Pagination() {

    }

    public Long getCount() {
        return count;
    }

    public Long getPerPage() {
        return perPage;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    /**
     * Cursor pointing at the next page, as a full URL carrying an opaque {@code cursor} query
     * parameter. Unlike {@link #getNextPage()} it has no depth limit, and the API returns it
     * without an API token — follow it by reusing only the {@code cursor} value on top of the
     * original request URL. Null on endpoints that do not offer cursor pagination.
     */
    public String getNextCursor() {
        return nextCursor;
    }

    public boolean hasMore() {
        return Boolean.TRUE.equals(hasMore);
    }
}
