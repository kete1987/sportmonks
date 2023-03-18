package es.com.kete1987.sportmonks.library.v2.model.stage;

import es.com.kete1987.sportmonks.library.v2.model.match.MatchDataList;
import es.com.kete1987.sportmonks.library.v2.model.match.MatchDetail;

import java.util.List;

public class Stage {
    private final int id = -1;
    private final String name = null;
    private final String type = null;
    private final int league_id = -1;
    private final int season_id = -1;
    private final MatchDataList fixtures = null;

    public Stage() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLeagueId() {
        return league_id;
    }

    public int getSeasonId() {
        return season_id;
    }

    public List<MatchDetail> getFixtures() {
        if (fixtures != null)
            return fixtures.getMatchDetailList();
        else return null;
    }
}
