package es.com.kete1987.sportmonks.library.v3.model.pagination;

public class Pagination {
    private Long count;
    private Long per_page;
    private Long current_page;
    private String next_page;
    private Boolean has_more;

    public Pagination() {

    }

    public Long getCount() {
        return count;
    }

    public Long getPerPage() {
        return per_page;
    }

    public Long getCurrentPage() {
        return current_page;
    }

    public String getNextPage() {
        return next_page;
    }

    public Boolean hasMore() {
        return has_more;
    }
}
