package es.com.kete1987.sportmonks.library.core.model.timezone;

import java.util.List;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class TimezonesResponse {
    private List<String> data;

    public TimezonesResponse() {
    }

    public List<String> getData() {
        return ModelCollections.unmodifiable(data);
    }
}
