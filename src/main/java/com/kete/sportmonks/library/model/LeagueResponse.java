package com.kete.sportmonks.library.model;

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
