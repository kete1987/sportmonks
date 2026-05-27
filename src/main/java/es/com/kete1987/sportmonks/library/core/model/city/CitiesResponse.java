package es.com.kete1987.sportmonks.library.core.model.city;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;

import java.util.List;
import java.util.Collections;

public class CitiesResponse {
    private List<City> data;
    private Pagination pagination;

    public CitiesResponse() {
    }

    public List<City> getData() {
        return data == null ? null : Collections.unmodifiableList(data);
    }

    public Pagination getPagination() {
        return pagination;
    }
}
