package es.com.kete1987.sportmonks.library.model.season;

import es.com.kete1987.sportmonks.library.model.Metadata;

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