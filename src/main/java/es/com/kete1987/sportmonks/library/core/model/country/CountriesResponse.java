package es.com.kete1987.sportmonks.library.core.model.country;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;

import java.util.List;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class CountriesResponse {
    private List<Country> data;
    private Pagination pagination;

    public CountriesResponse() {
    }

    public List<Country> getData() {
        return ModelCollections.unmodifiable(data);
    }

    public Pagination getPagination() {
        return pagination;
    }
}
