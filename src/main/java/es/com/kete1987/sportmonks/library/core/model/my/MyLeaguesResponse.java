package es.com.kete1987.sportmonks.library.core.model.my;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;

import java.util.List;
import java.util.Collections;

public class MyLeaguesResponse {
    private List<MyLeague> data;
    private Pagination pagination;

    public MyLeaguesResponse() {
    }

    public List<MyLeague> getData() {
        return data == null ? null : Collections.unmodifiableList(data);
    }

    public Pagination getPagination() {
        return pagination;
    }
}
