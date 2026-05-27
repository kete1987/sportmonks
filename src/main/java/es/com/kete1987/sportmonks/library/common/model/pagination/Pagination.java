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

    public boolean hasMore() {
        return Boolean.TRUE.equals(hasMore);
    }
}
