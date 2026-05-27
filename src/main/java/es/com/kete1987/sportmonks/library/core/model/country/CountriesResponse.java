package es.com.kete1987.sportmonks.library.core.model.country;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;

import java.util.List;
import java.util.Collections;

public class CountriesResponse {
    private List<Country> data;
    private Pagination pagination;

    public CountriesResponse() {
    }

    public List<Country> getData() {
        return data == null ? null : Collections.unmodifiableList(data);
    }

    public Pagination getPagination() {
        return pagination;
    }
}
