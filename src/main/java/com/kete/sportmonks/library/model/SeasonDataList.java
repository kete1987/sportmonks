package com.kete.sportmonks.library.model;

import java.util.List;

public class SeasonDataList {
    private List<SeasonData> data = null;
    private Metadata meta;

    public SeasonDataList(){}

    public List<SeasonData> getListOfSeasons()
    {
        return data;
    }

    public Metadata getMetadata() {
        return meta;
    }
}
