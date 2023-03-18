package es.com.kete1987.sportmonks.library.v2.model;

public class Pagination {
    private final int total = -1;
    private final int count = -1;
    private final int per_page = -1;
    private final int current_page = -1;
    private final int total_pages = -1;

    public Pagination() {
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public int getPerPage() {
        return per_page;
    }

    public int getCurrentPage() {
        return current_page;
    }

    public int getTotalPages() {
        return total_pages;
    }
}
