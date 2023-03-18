package es.com.kete1987.sportmonks.library.v2.model.group;

import es.com.kete1987.sportmonks.library.v2.model.match.MatchDataList;
import es.com.kete1987.sportmonks.library.v2.model.standings.Standings;

public class Group {
    private final String name = null;
    private final int league_id = -1;
    private final int season_id = -1;
    private final int stage_id = -1;
    private final String stage_name = null;
    private final MatchDataList fixtures = null;
    private final Standings standings = null;

    public Group() {
    }

    public String getName() {
        return name;
    }

    public int getLeagueId() {
        return league_id;
    }

    public int getSeasonId() {
        return season_id;
    }

    public int getStageId() {
        return stage_id;
    }

    public String getStageName() {
        return stage_name;
    }

    public MatchDataList getFixtures() {
        return fixtures;
    }

    public Standings getStandings() {
        return standings;
    }


}
