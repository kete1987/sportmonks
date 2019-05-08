package com.kete.sportmonks.library.model.league;

import com.kete.sportmonks.library.model.Metadata;

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
