package es.com.kete1987.sportmonks.library.v2.model.team;

import es.com.kete1987.sportmonks.library.v2.model.Metadata;

import java.util.List;

public class TeamsResponse {
    private List<TeamDetail> data = null;
    private Metadata meta;

    public TeamsResponse(){}

    public List<TeamDetail> getListOfTeams()
    {
        return data;
    }

    public Metadata getMetadata() {
        return meta;
    }
}
