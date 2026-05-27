package es.com.kete1987.sportmonks.library.core.model.my;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;

import java.util.List;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class MyLeaguesResponse {
    private List<MyLeague> data;
    private Pagination pagination;

    public MyLeaguesResponse() {
    }

    public List<MyLeague> getData() {
        return ModelCollections.unmodifiable(data);
    }

    public Pagination getPagination() {
        return pagination;
    }
}
