package es.com.kete1987.sportmonks.library.core.model.my;

import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

import java.util.List;

public class MyEnrichmentsResponse {
    private List<MyEnrichment> data;

    public List<MyEnrichment> getData() {
        return ModelCollections.unmodifiable(data);
    }
}