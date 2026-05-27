package es.com.kete1987.sportmonks.library.core.model.region;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;

import java.util.List;
import java.util.Collections;

public class RegionsResponse {
    private List<Region> data;
    private Pagination pagination;

    public RegionsResponse() {
    }

    public List<Region> getData() {
        return data == null ? null : Collections.unmodifiableList(data);
    }

    public Pagination getPagination() {
        return pagination;
    }
}
