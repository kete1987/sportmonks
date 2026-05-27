package es.com.kete1987.sportmonks.library.core.model.type;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;

import java.util.List;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class TypesResponse {
    private List<Type> data;
    private Pagination pagination;

    public TypesResponse() {
    }

    public List<Type> getData() {
        return ModelCollections.unmodifiable(data);
    }

    public Pagination getPagination() {
        return pagination;
    }
}
