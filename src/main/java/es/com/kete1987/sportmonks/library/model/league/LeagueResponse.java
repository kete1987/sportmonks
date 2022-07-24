package es.com.kete1987.sportmonks.library.model.league;

import es.com.kete1987.sportmonks.library.model.Metadata;

import java.util.List;

public class LeagueResponse {
    private List<League> data = null;
    private Metadata meta;

    public LeagueResponse(){}

    public List<League> getListOfLeagues()
    {
        return data;
    }

    public Metadata getMetadata() {
        return meta;
    }
}
