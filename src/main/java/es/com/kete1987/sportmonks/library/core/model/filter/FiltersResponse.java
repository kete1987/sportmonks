package es.com.kete1987.sportmonks.library.core.model.filter;

import java.util.List;
import java.util.Map;

public class FiltersResponse {
    private Map<String, List<String>> data;

    public FiltersResponse() {
    }

    public Map<String, List<String>> getData() {
        return data;
    }
}
