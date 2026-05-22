package es.com.kete1987.sportmonks.library.core.model.region;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;

import java.util.List;

public class RegionsResponse {
    private List<Region> data;
    private Pagination pagination;

    public RegionsResponse() {
    }

    public List<Region> getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
