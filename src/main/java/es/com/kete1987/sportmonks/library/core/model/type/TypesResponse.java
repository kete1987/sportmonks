package es.com.kete1987.sportmonks.library.core.model.type;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;

import java.util.List;
import java.util.Collections;

public class TypesResponse {
    private List<Type> data;
    private Pagination pagination;

    public TypesResponse() {
    }

    public List<Type> getData() {
        return data == null ? null : Collections.unmodifiableList(data);
    }

    public Pagination getPagination() {
        return pagination;
    }
}
