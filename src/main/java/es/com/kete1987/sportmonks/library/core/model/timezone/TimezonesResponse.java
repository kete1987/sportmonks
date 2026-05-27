package es.com.kete1987.sportmonks.library.core.model.timezone;

import java.util.List;
import java.util.Collections;

public class TimezonesResponse {
    private List<String> data;

    public TimezonesResponse() {
    }

    public List<String> getData() {
        return data == null ? null : Collections.unmodifiableList(data);
    }
}
