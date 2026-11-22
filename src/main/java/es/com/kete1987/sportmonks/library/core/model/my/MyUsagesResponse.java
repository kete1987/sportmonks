package es.com.kete1987.sportmonks.library.core.model.my;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

import java.util.List;

public class MyUsagesResponse {
    private List<MyUsage> data;
    private Pagination pagination;

    public List<MyUsage> getData() {
        return ModelCollections.unmodifiable(data);
    }

    public Pagination getPagination() {
        return pagination;
    }
}