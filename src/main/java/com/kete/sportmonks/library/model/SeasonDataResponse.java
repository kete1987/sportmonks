package com.kete.sportmonks.library.model;

import java.util.List;

public class SeasonDataResponse {
    private List<SeasonData> data = null;
    private Metadata meta;

    public SeasonDataResponse() {
    }

    public List<SeasonData> getData() {
        return data;
    }

    public Metadata getMetadata() {
        return meta;
    }
}
