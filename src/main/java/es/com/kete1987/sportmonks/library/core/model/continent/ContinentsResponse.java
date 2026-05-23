package es.com.kete1987.sportmonks.library.core.model.continent;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;

import java.util.List;

public class ContinentsResponse {
    private List<Continent> data;
    private Pagination pagination;

    public ContinentsResponse() {
    }

    public List<Continent> getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
